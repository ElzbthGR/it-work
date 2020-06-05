package ru.kpfu.itis.services;

import ru.kpfu.itis.settings.FlywaySettings;
import ru.kpfu.itis.settings.SchemaGenerationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlywayService implements InitializingBean {

    private static final String AUTO_MIGRATION_NAME = "auto-migration";
    private static final String MANUAL_DDL_PATH = "src/main/resources/db/ddl/manual";
    private static final String MANUAL_INITIAL_DDL_PATH = MANUAL_DDL_PATH + "/initial";
    private static final String MANUAL_FINAL_DDL_PATH = MANUAL_DDL_PATH + "/final";

    private final Flyway flyway;
    private final SchemaGenerationService schemaGenerationService;
    private final FlywaySettings flywaySettings;
    private final SchemaGenerationSettings schemaGenerationSettings;

    @Override
    public void afterPropertiesSet() {
        try {
            if (flywaySettings.isGenerationEnabled()) {
                createMigrationFilesFromManualDdl(MANUAL_INITIAL_DDL_PATH);
                migrate();
                schemaGenerationService.generate();
                File ddlFile = new File(schemaGenerationSettings.getDdlLocation());
                if (ddlFile.exists() && ddlFile.length() != 0) {
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
                    String fileName = String.format("V%s__%s.sql", timestamp, AUTO_MIGRATION_NAME);
                    Path migrationPath = Paths.get(flywaySettings.getLocation(), fileName);
                    Path ddlPath = ddlFile.toPath();
                    log.info("Moving file {} to {}", ddlPath.toString(), migrationPath.toString());
                    Files.move(ddlPath, migrationPath);
                    log.info("Successfully moved");
                } else {
                    log.info("DDL file is empty");
                }
                createMigrationFilesFromManualDdl(MANUAL_FINAL_DDL_PATH);
            } else {
                log.info("DDL generation disabled");
            }
            migrate();
            schemaGenerationService.validate();
        } catch (IOException e) {
            log.error("Migration error. Cause: ", e);
        }
    }

    private void migrate() {
        log.info("Starting flyway migration");
        flyway.migrate();
        log.info("Migration completed successfully");
    }

    private void createMigrationFilesFromManualDdl(String manualDdlDirectoryPath) throws IOException {
        final Path directoryPath = Paths.get(manualDdlDirectoryPath);
        Files.createDirectories(directoryPath);
        Files.walk(directoryPath)
                .filter(Files::isRegularFile)
                .forEach(this::createMigrationFileFromManualDdl);
    }

    private void createMigrationFileFromManualDdl(Path ddlFilePath) {
        try {
            String fileExtension = FilenameUtils.getExtension(ddlFilePath.getFileName().toString());
            if (!fileExtension.equalsIgnoreCase("sql")) {
                log.warn("File {} is not .sql, skipped", ddlFilePath);
                return;
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            String fileName = String.format("V%s__%s", timestamp, ddlFilePath.getFileName());
            Path migrationPath = Paths.get(flywaySettings.getLocation(), fileName);
            Files.move(ddlFilePath, migrationPath);
        } catch (IOException e) {
            log.error("Migration error. Cause: ", e);
        }
    }
}

package ru.kpfu.itis.services;

import ru.kpfu.itis.settings.SchemaGenerationSettings;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.Metadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.SchemaValidator;
import org.hibernate.tool.schema.TargetType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.MessageFormat;
import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class SchemaGenerationService {

    private final Metadata metadata;
    private final ServiceRegistry serviceRegistry;
    private final SchemaGenerationSettings schemaGenerationSettings;

    public void generate() {
        File ddlFile = new File(schemaGenerationSettings.getDdlLocation());
        deleteFileIfExists(ddlFile);
        new SchemaUpdate()
                .setDelimiter(";")
                .setFormat(true)
                .setOutputFile(ddlFile.getAbsolutePath())
                .execute(EnumSet.of(TargetType.SCRIPT), metadata, serviceRegistry);
    }

    public void validate() {
        new SchemaValidator()
                .validate(metadata, serviceRegistry);
    }

    private void deleteFileIfExists(File ddlFile) {
        if (ddlFile.exists()) {
            if (!ddlFile.isFile()) {
                String msg = MessageFormat.format("File is not a normal file {0}", ddlFile);
                throw new IllegalStateException(msg);
            }
            if (!ddlFile.delete()) {
                String msg = MessageFormat.format("Unable to delete file {0}", ddlFile);
                throw new IllegalStateException(msg);
            }
        }
    }
}

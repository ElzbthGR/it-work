package ru.kpfu.itis.services;


import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.models.FileData;
import ru.kpfu.itis.models.enums.FileExtension;
import ru.kpfu.itis.repositories.FileDataRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileDataService {
    private static final String FILE_UPLOAD_FOLDER = "uploads";
    private static final List<FileExtension> imagesExtension =
            Lists.newArrayList(FileExtension.PNG, FileExtension.JPG, FileExtension.JPEG);
    public static final List<FileExtension> videosExtension =
            Lists.newArrayList(FileExtension.AVI, FileExtension.MOV, FileExtension.MP4);
    public static final List<FileExtension> documentsExtension =
            Lists.newArrayList(FileExtension.DOCX, FileExtension.TXT, FileExtension.XLS, FileExtension.XML);

    private final FileDataRepository fileDataRepository;

    public FileData upload(final MultipartFile multipartFile, final Long userId) {
        log.info("Upload file");
        if (multipartFile.isEmpty()) {
            return null;
        }
        final String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
        final String uuid = UUID.randomUUID().toString();
        final String path = String.format(FILE_UPLOAD_FOLDER + "/%s.%s", uuid, extension);
        FileData.ContentType contentType;

        boolean isImage = imagesExtension.stream()
                .map(FileExtension::getExtension)
                .collect(Collectors.toList())
                .contains(extension);
        boolean isVideo = videosExtension.stream()
                .map(FileExtension::getExtension)
                .collect(Collectors.toList())
                .contains(extension);
        boolean isDocument = documentsExtension.stream()
                .map(FileExtension::getExtension)
                .collect(Collectors.toList())
                .contains(extension);
        if (isImage) {
            contentType = FileData.ContentType.IMAGE;
        } else if (isVideo) {
            contentType = FileData.ContentType.VIDEO;
        } else if (isDocument) {
            contentType = FileData.ContentType.DOCUMENT;
        } else {
            throw new NotFoundException("Not supported file extension");
        }
        byte[] bytes = new byte[0];
        try {
            FileOutputStream fout = new FileOutputStream(
                    new File(String.format(FILE_UPLOAD_FOLDER + "/%s.%s", uuid, extension))
            );
            bytes = multipartFile.getBytes();
            fout.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileDataRepository.save(FileData.builder()
                .originalName(multipartFile.getOriginalFilename())
                .uuid(uuid)
                .creatorId(userId)
                .path(path)
                .file(bytes)
                .contentType(contentType)
                .used(Boolean.FALSE)
                .extension(FileExtension.valueOf(extension.toUpperCase()))
                .build());
    }

    @Transactional
    public void markAsUsed(final Long fileId) {
        log.info("File with id {} used", fileId);
        fileDataRepository.markAsUsed(fileId);
    }

    @Transactional
    public void markAllAsUsed(final List<Long> fileIds) {
        log.info("Files with ids {} used", fileIds);
        fileDataRepository.markAllAsUsed(fileIds);
    }

    public List<FileData> getAllByIds(final List<Long> fileIds) {
        log.info("Getting all files with ids {}", fileIds);
        return fileDataRepository.findAllByIdAndDeletedFalse(fileIds);
    }

    public FileData getById(final Long fileId) {
        log.info("Getting file with id {}", fileId);
        return fileDataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(String.format("File by id %s not found", fileId)));
    }

    public FileData getByUuid(final String uuid) {
        log.info("Getting file with uuid {}", uuid);
        return fileDataRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("File by uuid %s not found", uuid)));
    }
}

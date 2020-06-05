package ru.kpfu.itis.models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileExtension {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    AVI("avi"),
    MP4("mp4"),
    MOV("mov"),
    DOCX("docx"),
    TXT("txt"),
    XML("xml"),
    XLS("xls");

    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

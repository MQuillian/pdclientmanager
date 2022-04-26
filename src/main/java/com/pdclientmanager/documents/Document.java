package com.pdclientmanager.documents;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class Document {
    
    @NotEmpty
    private String fileName;
    
    @NotNull
    private MultipartFile file;

    public Document() {
        
    }
    
    public Document(String fileName, MultipartFile file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

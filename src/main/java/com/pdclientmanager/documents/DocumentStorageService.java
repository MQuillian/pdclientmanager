package com.pdclientmanager.documents;

import java.net.URL;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentStorageService {

    void uploadFile(String caseNumber, String fileName, MultipartFile file)
            throws Exception;

    URL getDownloadUrl(String caseNumber, String fileName) throws Exception;

    List<String> listFiles(String caseNumber) throws Exception;

    void deleteFile(String caseNumber, String fileName) throws Exception;

}
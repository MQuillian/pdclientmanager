package com.pdclientmanager.documents;

import java.net.URL;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentStorageService {

    void uploadFile(String caseId, String fileName, MultipartFile file)
            throws Exception;

    URL getDownloadUrl(String caseId, String fileName) throws Exception;

    List<String> listFiles(String caseId) throws Exception;

    void deleteFile(String caseId, String fileName) throws Exception;

}
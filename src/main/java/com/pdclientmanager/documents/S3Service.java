package com.pdclientmanager.documents;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class S3Service implements DocumentStorageService {
    
    private Region clientRegion = Region.US_WEST_2;
    private String bucketName = "mq-pdcm1";
    
    private S3Client s3Client;
    private S3Presigner presigner;

    public S3Service() {
        this.s3Client = S3Client.builder()
            .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
            .region(clientRegion)
            .build();
        
        this.presigner = S3Presigner.builder()
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .region(clientRegion)
                .build();
    }
    
    @Override
    public void uploadFile(String caseNumber, String fileName, MultipartFile file)
        throws AwsServiceException, SdkClientException, S3Exception, IOException {
        //NOTE: for cost-saving/demo purposes, objects expire 1 hr after upload and delete 24 hours after that
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType("application/pdf")
                .key(generateObjectKey(caseNumber, fileName))
                .expires(Instant.now().plusSeconds(3600L))
                .build();
        
        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }
    
    @Override
    public URL getDownloadUrl(String caseNumber, String fileName) throws AwsServiceException, SdkClientException, S3Exception {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(generateObjectKey(caseNumber, fileName))
                .responseContentType("application/pdf")
                .build();
        
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(request)
                .build();
        
        PresignedGetObjectRequest presignedGetObjectRequest =
                presigner.presignGetObject(getObjectPresignRequest);
        
        return presignedGetObjectRequest.url();
    }
    
    @Override
    public List<String> listFiles(String caseNumber) throws AwsServiceException, SdkClientException, S3Exception {
        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket(bucketName)
                .prefix(generateListPrefix(caseNumber))
                .build();
        
        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<String> fileNames = new ArrayList<>();
        for(S3Object obj : res.contents()) {
            fileNames.add(obj.key().replaceAll("\\w+/\\w+/", ""));
        }
        
        return fileNames;
    }
    
    @Override
    public void deleteFile(String caseNumber, String fileName) throws AwsServiceException, SdkClientException, S3Exception {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(generateObjectKey(caseNumber, fileName))
                .build();
        
        s3Client.deleteObject(deleteObjectRequest);
    }
    
    private String generateObjectKey(String caseNumber, String fileName) {
        return "Documents/" + caseNumber + "/" + fileName;
    }
    
    private String generateListPrefix(String caseNumber) {
        return "Documents/" + caseNumber + "/";
    }
}

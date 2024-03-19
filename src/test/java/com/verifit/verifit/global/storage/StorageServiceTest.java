package com.verifit.verifit.global.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StorageServiceImplTest {
//
//    @Mock
//    private AmazonS3 amazonS3Client;
//
//    private StorageServiceImpl storageService;
//
//    @BeforeEach
//    void setUp() throws MalformedURLException {
//        MockitoAnnotations.openMocks(this);
//        storageService = new StorageServiceImpl(amazonS3Client);
//        when(amazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(new PutObjectResult());
//        when(amazonS3Client.getUrl(anyString(), anyString())).thenReturn(new java.net.URL("http://example.com"));
//
//    }
//
//    @Test
//    void testUpload() throws IOException {
//        // Given
//        String originalFileName = "test.txt";
//        byte[] content = "Hello, World!".getBytes();
//        MockMultipartFile multipartFile = new MockMultipartFile("file", originalFileName, "text/plain", content);
//        String dirName = "profile-pictures";
//
//        // When
//        String resultUrl = storageService.upload(multipartFile, dirName);
//
//        // Then
//        assertNotNull(resultUrl);
//        assertTrue(resultUrl.contains(originalFileName));
//        verify(amazonS3Client, times(1)).putObject(any(PutObjectRequest.class));
//    }
}

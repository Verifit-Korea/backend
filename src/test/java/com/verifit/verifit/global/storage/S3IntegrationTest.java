package com.verifit.verifit.global.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

// S3 연동 통합 테스트
// S3에 파일을 업로드하고, 업로드된 파일의 URL을 검증하는 테스트
@SpringBootTest
public class S3IntegrationTest {

    @Autowired
    private StorageService storageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String uploadedFileName;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        // 테스트에 사용된 S3 객체를 정리
//        if (uploadedFileName != null && !uploadedFileName.isEmpty()) {
//            storageService.delete(uploadedFileName, "profile-picture");
//        }
    }

    @Test
    void testUploadImageToS3() throws IOException {
        // 테스트 이미지 파일 생성
        byte[] content = "test image content".getBytes();
        MultipartFile file = new MockMultipartFile("test-image.jpg", "test-image.jpg", "image/jpeg", content);

        // 파일 업로드 실행
        String imageUrl = storageService.upload(file, "profile-picture");

        // 업로드된 파일의 URL 검증
        assertThat(imageUrl).isNotNull();
        assertThat(imageUrl).contains("test-image.jpg");

        // S3에서 파일을 정상적으로 가져올 수 있는지 검증 (선택적)
        // 여기에 S3에서 파일을 가져오는 로직을 추가할 수 있습니다.

        uploadedFileName = imageUrl; // tearDown에서 사용하기 위해 업로드된 파일 이름 저장
    }
}

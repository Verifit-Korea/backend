package com.verifit.verifit.global.storage;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final S3Template s3Template;

    @Override
    public String uploadProfileImage(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try{
            //    @Value("${cloud.aws.s3.bucket}")
            // 이상하게 application.yml에 있는 값을 가져오지 못함
            String bucketName = "verifit";
            s3Template.upload(bucketName, fileName, file.getInputStream());

            return String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucketName, fileName);
        } catch (IOException e){
            throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage());
        }
    }
}

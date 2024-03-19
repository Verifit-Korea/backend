package com.verifit.verifit.global.storage;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    /**
     * MultipartFile을 전달받아 S3에 업로드하고, 업로드된 파일의 URL을 반환합니다.
     *
     * @param multipartFile 업로드할 파일
     * @param dirName 파일이 저장될 S3 내의 디렉토리 이름
     * @return 업로드된 파일의 S3 URL
     * @throws IOException 파일 변환 중 예외 발생 시
     */
    String upload(MultipartFile multipartFile, String dirName) throws IOException;
}

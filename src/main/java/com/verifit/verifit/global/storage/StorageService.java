package com.verifit.verifit.global.storage;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String uploadProfileImage(MultipartFile file);

}

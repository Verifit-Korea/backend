package com.verifit.verifit.mypage.controller;

import com.verifit.verifit.global.storage.StorageService;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
import com.verifit.verifit.mypage.dto.MemberInfoDTO;
import com.verifit.verifit.mypage.dto.PasswordChangeRequest;
import com.verifit.verifit.mypage.service.MypageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;
    private final MemberService memberService;
    private final StorageService storageService;


    // 마이페이지의 내정보 들어가면 필요한 정보 가져오기
    @GetMapping("/my-info")
    public ResponseEntity<MemberInfoDTO> getMyInfo(@AuthenticationPrincipal Member member) {
        MemberInfoDTO memberInfoDTO = mypageService.getMyInfoDTO(member);
        return ResponseEntity.ok(memberInfoDTO);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal Member member,
                                                 @Valid @RequestBody PasswordChangeRequest request) {
        memberService.changePassword(member.getId(), request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @PatchMapping("/change-nickname")
    public ResponseEntity<String> changeNickname(@AuthenticationPrincipal Member member,
                                                 @RequestParam("nickname") String nickname) {
        memberService.updateNickname(member.getId(), nickname);
        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }

    // 프로필 사진 업로드 및 업데이트
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@AuthenticationPrincipal Member member,
                                                       @RequestParam("file") MultipartFile file) {

        // 파일이 null이거나 비어있는지 확인
        if (file == null || file.isEmpty()) {
            return new ResponseEntity<>("파일이 제공되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = storageService.upload(file, "profile-picture"); // S3에 프로필 사진 업로드
            System.out.println("fileName = " + fileName);
            memberService.updateProfileUrl(member.getId(), fileName); // 프로필 URL 변경
            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            return new ResponseEntity<>("프로필 사진 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

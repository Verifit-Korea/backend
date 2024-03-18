package com.verifit.verifit.member.controller;

import com.verifit.verifit.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/email/existence")
    public ResponseEntity checkEmailExistence(@RequestParam("email") String email) {
        boolean isEmailExists = memberService.isEmailAlreadyExists(email);
        return new ResponseEntity(isEmailExists, HttpStatus.OK);
    }

    @GetMapping("/nickname/existence")
    public ResponseEntity checkNicknameExistence(@RequestParam("nickname") String nickname) {
        boolean isNicknameExists = memberService.isNicknameAlreadyExists(nickname);
        return new ResponseEntity(isNicknameExists, HttpStatus.OK);
    }
}

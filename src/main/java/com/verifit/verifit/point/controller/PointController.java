package com.verifit.verifit.point.controller;

import com.verifit.verifit.global.jwt.TokenInfo;
import com.verifit.verifit.member.entity.Member;
import com.verifit.verifit.member.service.MemberService;
import com.verifit.verifit.point.dto.PointDTO;
import com.verifit.verifit.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {
    private final PointService pointService;
    private final MemberService memberService;

    @GetMapping("/my-point")
    public ResponseEntity<PointDTO> getMyPoint(@AuthenticationPrincipal TokenInfo userDetails) {
        Member member = memberService.findMemberByEmail(userDetails.getUsername());
        PointDTO pointDTO = pointService.getMemberPoint(member);
        return ResponseEntity.ok(pointDTO);
    }

    // 테스트용 API. 개발 이후 삭제 ㄱㄱ
    @PostMapping("/add-point")
    public ResponseEntity<String> addPoint(@AuthenticationPrincipal TokenInfo userDetails) {
        Member member = memberService.findMemberByEmail(userDetails.getUsername());
        pointService.addPoint(member, 100);

        return ResponseEntity.ok("포인트 적립 완료");
    }
}

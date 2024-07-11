package com.verifit.verifit.point.dto;

import com.verifit.verifit.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRankingDTO {
    private Long memberId;
    private String nickname;
    private Long totalPoints;
    private int rank;

    public MemberRankingDTO(Long memberId, String nickname, Long totalPoints, int rank) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.totalPoints = totalPoints;
        this.rank = rank;
    }
}

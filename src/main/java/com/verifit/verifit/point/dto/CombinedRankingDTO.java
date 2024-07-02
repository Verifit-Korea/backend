package com.verifit.verifit.point.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class CombinedRankingDTO {
    private List<MemberRankingDTO> dailyRanking;
    private List<MemberRankingDTO> weeklyRanking;
    private List<MemberRankingDTO> monthlyRanking;
}

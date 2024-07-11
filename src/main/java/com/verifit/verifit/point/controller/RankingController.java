package com.verifit.verifit.point.controller;

import com.verifit.verifit.global.exception.ApiException;
import com.verifit.verifit.global.exception.ExceptionCode;
import com.verifit.verifit.point.dto.CombinedRankingDTO;
import com.verifit.verifit.point.dto.MemberRankingDTO;
import com.verifit.verifit.point.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/all")
    public ResponseEntity<CombinedRankingDTO> getCombinedRanking() {
        try{
            CombinedRankingDTO ranking =  rankingService.getCombinedRanking();
            return ResponseEntity.ok(ranking);
        } catch (Exception e){
            throw new ApiException(ExceptionCode.RANKING_NOT_AVAILABLE);
        }
    }

    @GetMapping("/{period}")
    public  ResponseEntity<List<MemberRankingDTO>> getRanking(@PathVariable String period) {
        try {
            List<MemberRankingDTO> ranking = switch (period.toLowerCase()) { // enhanced switch로 쌈@뽕하게 처리함
                case "daily" -> rankingService.getDailyRanking();
                case "weekly" -> rankingService.getWeeklyRanking();
                case "monthly" -> rankingService.getMonthlyRanking();
                default -> throw new ApiException(ExceptionCode.INVALID_RANKING_PERIOD);
            };
            return ResponseEntity.ok(ranking);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ExceptionCode.RANKING_NOT_AVAILABLE);
        }
    }
}

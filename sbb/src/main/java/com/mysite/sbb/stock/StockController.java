package com.mysite.sbb.stock;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StockController {

    private final StockService stockService;

    
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/loadStockInfo")
    public String loadStockInfo(Model model) {
        List<String> codes = Arrays.asList(
            // 여러 기업의 주식 코드 목록
            "005930", // 삼성 0
            "000660", // SK하이닉스 1
            "046890", // 서울반도체 2
            "000990", // DB하이텍 3
            "322000", // HD현대에너지솔루션4
            "078350", // 한양디지텍 5
            "092190", // 서울바이오시스 6
            "033170", // 시그네틱스 7
            "200710", // 에이디테크놀로지 8
            "045100", // 한양이엔지 9
            "095340", // ISC 10
            "140070", // 서플러스글로벌 11
            "036930", // 주성엔지니어링 12
            "053610", // 프로텍 13
            "214430", // 아이쓰리시스템 14
            "038060", // 루멘스 15
            "097800", // 윈팩 16
            "054450", // 텔레칩스 17
            "153490"  // 우리이엔엘
            
            
        );

        // 각 기업의 주식 정보를 가져온 다음 모델에 추가
        Map<String, String> stocks = stockService.getStockPrices(codes);
        model.addAttribute("stocks", stocks);

        return "stockInfo"; // stockInfo.html로 이동
    }
}

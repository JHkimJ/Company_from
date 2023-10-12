package com.mysite.sbb.stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.question.Question;

@Controller
public class StockController {

    private final StockService stockService;

    
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/loadStockInfo")
    public String loadStockInfo(Model model) {
        Map<String,String> codes = new HashMap<>();
        codes.put("005930", "00126380");
// 				여러 기업의 주식 코드 목록
//            "005930/00126380", // 삼성 0
//            "000660/00126380", // SK하이닉스 1
//            "046890/00126380", // 서울반도체 2
//            "000990/00126380", // DB하이텍 3
//            "322000/00126380", // HD현대에너지솔루션4
//            "078350/00126380", // 한양디지텍 5
//            "092190/00126380", // 서울바이오시스 6
//            "033170/00126380", // 시그네틱스 7
//            "200710/00126380", // 에이디테크놀로지 8
//            "045100/00126380", // 한양이엔지 9
//            "095340/00126380", // ISC 10
//            "140070/00126380", // 서플러스글로벌 11
//            "036930/00126380", // 주성엔지니어링 12
//            "053610/00126380", // 프로텍 13
//            "214430/00126380", // 아이쓰리시스템 14
//            "038060/00126380", // 루멘스 15
//            "097800/00126380", // 윈팩 16
//            "054450/00126380", // 텔레칩스 17
//            "153490/00126380"  // 우리이엔엘
            
     
//        );
        // 각 기업의 주식 정보를 가져온 다음 모델에 추가
        Map<String, String> stocks = stockService.getStockPrices(codes);
        model.addAttribute("stocks", stocks);

        return "stockInfo"; // stockInfo.html로 이동
    }
    
    @GetMapping("/loadStockInfo/view")
    public String loadInfoView(Model model, @RequestParam(value = "code", defaultValue = "") String code){
        
    	/*Map<String, String> code = stockService.getStockInfo(code);
    	model.addAttribute("code" ,code);*/
    	System.out.println("corp_code = "+ code);
    	Map<String, String> corpInfo = stockService.getCompnayView(code);
        model.addAttribute("corpInfo", corpInfo);
    	return "viewinfo"; // viewInfo.html 로 이동
    }
}

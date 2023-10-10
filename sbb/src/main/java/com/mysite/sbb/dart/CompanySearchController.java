package com.mysite.sbb.dart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompanySearchController {

    private final DartApiService dartApiService;

    @Autowired
    public CompanySearchController(DartApiService dartApiService) {
        this.dartApiService = dartApiService;
    }

    @GetMapping("/search")
    public String searchCompanies(@RequestParam(name = "query") String query, Model model) {
        // 검색어(query)를 사용하여 기업 정보를 조회
        List<DartCompanyInfo> companies = dartApiService.searchCompanies(query);
        model.addAttribute("companies", companies);
        return "dart"; // "dart.html" 템플릿으로 연결
    }
}

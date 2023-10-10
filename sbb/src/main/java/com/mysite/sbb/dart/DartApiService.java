package com.mysite.sbb.dart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DartApiService {

    @Value("${dart.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public DartApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DartCompanyInfo getCompanyInfoByCorpCode(String corpCode) {
        String apiUrl = "https://opendart.fss.or.kr/api/company.json?crtfc_key=" + apiKey + "&corp_code=" + corpCode;

        DartCompanyInfo companyInfo = restTemplate.getForObject(apiUrl, DartCompanyInfo.class);

        return companyInfo;
    }

    // Add the searchCompanies method
    public List<DartCompanyInfo> searchCompanies(String query) {
        String apiUrl = "https://opendart.fss.or.kr/api/company.json?crtfc_key=" + apiKey + "&corp_name=" + query;

        DartCompanyInfo[] companyInfoArray = restTemplate.getForObject(apiUrl, DartCompanyInfo[].class);

        if (companyInfoArray != null) {
            return List.of(companyInfoArray);
        } else {
            return List.of();
        }
    }
}

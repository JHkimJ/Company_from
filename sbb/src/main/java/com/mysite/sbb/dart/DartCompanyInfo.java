package com.mysite.sbb.dart;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DartCompanyInfo {

    @JsonProperty("corp_code")
    private String corpCode;

    @JsonProperty("corp_name")
    private String corpName;

    @JsonProperty("stock_code")
    private String stockCode;

    @JsonProperty("modify_date")
    private String modifyDate;


}
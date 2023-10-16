package com.mysite.sbb.stock;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document; // 변경된 임포트
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Service
public class StockService {

	private String crtfcKey = "bfea3d8660917e68e9ca617e3c3dad789aa61a6e";
	private String bsns_year = "2022";		// 연도
	private String reprt_code = "11011";	// 11011 : 사업보고서,
											// 11012 : 반기보고서,
											// 11013 : 1분기보고서,
											// 11014 : 3분기보고서
	private String fs_div = "OFS";			// OFS : 재무제표 , CFS : 연결재무제표
	
	public Map<String, String> getStockPrices(Map<String, String> codes) {
		Map<String, String> stocks = new HashMap<>();

		for (String key : codes.keySet()) {
			String url = "https://finance.naver.com/item/main.naver?code=" + key;

			try {
				Document doc = Jsoup.connect(url).get();

				String nameSelector = "dd:contains(종목명)";
				String priceSelector = "dd:contains(현재가)";
				String name = doc.select(nameSelector).first().text();
				name = name.replace("종목명 ", "");
				name = "<a href='loadStockInfo/view?code=" + codes.get(key) + "'>" + name + "</a>";
				String price = doc.select(priceSelector).first().text();

				stocks.put(name, price);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return stocks;
	}

	public Map<String, String> getCompnayView(String code) throws Exception {
		Map<String, String> info = new HashMap<>();
		// 기업정보
		URI uri1 = UriComponentsBuilder.fromHttpUrl("https://opendart.fss.or.kr/api/company.json")
				.queryParam("crtfc_key", crtfcKey).queryParam("corp_code", code).build().encode().toUri();
		// 재무제표
		URI uri2 = UriComponentsBuilder.fromHttpUrl("https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json")
				.queryParam("crtfc_key", crtfcKey).queryParam("corp_code", code).queryParam("bsns_year", bsns_year)
				.queryParam("reprt_code", reprt_code).queryParam("fs_div", fs_div).build().encode().toUri();
		// 최대주주 현황
		URI uri3 = UriComponentsBuilder.fromHttpUrl("https://opendart.fss.or.kr/api/hyslrSttus.json")
				.queryParam("crtfc_key", crtfcKey).queryParam("corp_code", code).queryParam("bsns_year", bsns_year)
				.queryParam("reprt_code", reprt_code).build().encode().toUri();

		// 기업정보
		HttpHeaders headers1 = new HttpHeaders();
		RestTemplate restTemplate1 = new RestTemplate();
		HttpEntity<String> requestEntity1 = new HttpEntity<>(headers1);
		ResponseEntity<String> responseEntity1 = restTemplate1.exchange(uri1, HttpMethod.GET, requestEntity1,
				String.class);
		JSONObject jsonObject1;
		jsonObject1 = new JSONObject(responseEntity1.getBody());
		System.out.println(jsonObject1.toString());
		info.put("corp_name", jsonObject1.get("corp_name").toString());
		info.put("ceo_nm", jsonObject1.get("ceo_nm").toString());
		info.put("corp_code", jsonObject1.get("corp_code").toString());
		info.put("stock_code", jsonObject1.get("stock_code").toString());
		info.put("adres", jsonObject1.get("adres").toString());

		StringBuffer sb = new StringBuffer();
		sb.append(jsonObject1.get("est_dt").toString());
		sb.insert(4, "년");
		sb.insert(7, "월");
		sb.insert(10, "일");
		info.put("est_dt", sb.toString());
		// 재무제표
		HttpHeaders headers2 = new HttpHeaders();
		RestTemplate restTemplate2 = new RestTemplate();
		HttpEntity<String> requestEntity2 = new HttpEntity<>(headers2);
		ResponseEntity<String> responseEntity2 = restTemplate2.exchange(uri2, HttpMethod.GET, requestEntity2,
				String.class);
		JSONObject jsonObject2;
		jsonObject2 = new JSONObject(responseEntity2.getBody());
		System.out.println(jsonObject2.toString());

		// "list" [{ "data" }] 가져옴
		JSONObject list1 = jsonObject2.getJSONArray("list").getJSONObject(0);
		info.put("reprt_code", list1.get("reprt_code").toString());
		info.put("bsns_year", list1.get("bsns_year").toString());
		info.put("sj_nm", list1.get("sj_nm").toString());
		info.put("thstrm_nm", list1.get("thstrm_nm").toString());
		info.put("thstrm_amount", list1.get("thstrm_amount").toString());

		String thstrm_amount = list1.get("thstrm_amount").toString();

		// thstrm_amount long 으로 자료형 변환
		long amount = Long.parseLong(thstrm_amount);

		// 10진수 형 변환
		DecimalFormat formatter = new DecimalFormat("#,###");

		// ₩ 단위 추가
		String formattedAmount = formatter.format(amount);
		formattedAmount = "₩" + formattedAmount;
		info.put("thstrm_amount", formattedAmount);

		// 최대주주 현황
		HttpHeaders headers3 = new HttpHeaders();
		RestTemplate restTemplate3 = new RestTemplate();
		HttpEntity<String> requestEntity3 = new HttpEntity<>(headers3);
		ResponseEntity<String> responseEntity3 = restTemplate3.exchange(uri3, HttpMethod.GET, requestEntity3,
				String.class);
		JSONObject jsonObject3;
		jsonObject3 = new JSONObject(responseEntity3.getBody());
		System.out.println(jsonObject3.toString());

		JSONObject list2 = jsonObject3.getJSONArray("list").getJSONObject(0);
		info.put("nm", list2.get("nm").toString());
		info.put("relate", list2.get("relate").toString());
		info.put("stock_knd", list2.get("stock_knd").toString());
		info.put("bsis_posesn_stock_co", list2.get("bsis_posesn_stock_co").toString());
		info.put("bsis_posesn_stock_qota_rt", list2.get("bsis_posesn_stock_qota_rt").toString());
		info.put("trmend_posesn_stock_co", list2.get("trmend_posesn_stock_co").toString());
		info.put("trmend_posesn_stock_qota_rt", list2.get("trmend_posesn_stock_qota_rt").toString());

		return info;
	}
}

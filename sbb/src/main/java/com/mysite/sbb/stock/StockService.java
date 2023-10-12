package com.mysite.sbb.stock;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document; // 변경된 임포트
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Service
public class StockService {

	private String crtfcKey = "bfea3d8660917e68e9ca617e3c3dad789aa61a6e";

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
		/*
		 * info.put("corp_code", "00126380"); info.put("corp_name", "삼성전자(주)");
		 * info.put("stock_code", "005930"); info.put("adres", "경기도 수원시 영통구  삼성로 129");
		 * info.put("est_dt", "1997년06월11일"); info.put("", ""); info.put("", "");
		 * info.put("", "");
		 */
		URI uri = UriComponentsBuilder.fromHttpUrl("https://opendart.fss.or.kr/api/company.json")
				.queryParam("crtfc_key", crtfcKey).queryParam("corp_code", code).build().encode().toUri();

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		JSONObject jsonObject;
		jsonObject = new JSONObject(responseEntity.getBody());
		System.out.println(jsonObject.toString());
		info.put("corp_name", jsonObject.get("corp_name").toString());
		info.put("ceo_nm", jsonObject.get("ceo_nm").toString());
		info.put("corp_code", jsonObject.get("corp_code").toString());
		info.put("stock_code", jsonObject.get("stock_code").toString());
		info.put("adres", jsonObject.get("adres").toString());

		StringBuffer sb = new StringBuffer();
		sb.append(jsonObject.get("est_dt").toString());
		sb.insert(4, "년");
		sb.insert(7, "월");
		sb.insert(10, "일");
		
		info.put("est_dt", sb.toString());

		return info;

	}
}

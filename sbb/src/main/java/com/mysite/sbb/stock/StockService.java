package com.mysite.sbb.stock;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document; // 변경된 임포트
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class StockService {

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
                name = "<a href='loadStockInfo/view?code="+codes.get(key)+"'>"+name+"</a>";
                String price = doc.select(priceSelector).first().text();

                stocks.put(name, price);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return stocks;
    }
    public Map<String, String> getCompnayView(String code) {
    	Map<String, String> info = new HashMap<>();
    	info.put("corpCode", "00126380");
    	info.put("corpName", "삼성전자(주)");
       	info.put("stock_code", "005930");
       	info.put("adres", "경기도 수원시 영통구  삼성로 129 (매탄동)");
    	return info;
    
    }
}

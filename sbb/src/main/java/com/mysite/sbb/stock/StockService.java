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

    public Map<String, String> getStockPrices(List<String> codes) {
        Map<String, String> stocks = new HashMap<>();

        for (String code : codes) {
            String url = "https://finance.naver.com/item/main.naver?code=" + code;

            try {
                Document doc = Jsoup.connect(url).get();

                String nameSelector = "dd:contains(종목명)";
                String priceSelector = "dd:contains(현재가)";

                String name = doc.select(nameSelector).first().text();
                String price = doc.select(priceSelector).first().text();

                stocks.put(name, price);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return stocks;
    }
}

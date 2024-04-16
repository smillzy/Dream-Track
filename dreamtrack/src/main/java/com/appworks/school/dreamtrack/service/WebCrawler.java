package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.WebCrawlerRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.appworks.school.dreamtrack"})
public class WebCrawler implements CommandLineRunner {

    private final WebCrawlerRepository webCrawlerRepository;

    public WebCrawler(WebCrawlerRepository webCrawlerRepository) {
        this.webCrawlerRepository = webCrawlerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebCrawler.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        getStockInfo();
        getExchange();
    }

    public void getStockInfo() {
        String stockApiUrl = "https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL";
        ResponseEntity<String> response = new RestTemplate().getForEntity(stockApiUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String stockInfo = response.getBody();
            JSONArray stockInfoArray = new JSONArray(stockInfo);

            List<Map<String, Object>> responseRepo = new ArrayList<>();

            for (int i = 0; i < stockInfoArray.length(); i++) {
                JSONObject stockItem = stockInfoArray.getJSONObject(i);
                Map<String, Object> stockDetails = new HashMap<>();

                String code = stockItem.getString("Code");
                String name = stockItem.getString("Name");

                String closingPriceStr = stockItem.optString("ClosingPrice", "0").trim();
                BigDecimal closePrice;
                if (closingPriceStr.isEmpty() || closingPriceStr.equals(" ")) {
                    closePrice = BigDecimal.ZERO;
                } else {
                    closePrice = new BigDecimal(closingPriceStr);
                }

                stockDetails.put("code", code);
                stockDetails.put("name", name);
                stockDetails.put("closePrice", closePrice);

                responseRepo.add(stockDetails);
            }
            webCrawlerRepository.updateStockData(responseRepo);
            log.info("Update Stock Data:" + responseRepo);
        }
    }

    public void getExchange() {
        List<Map<String, String>> exchangeRates = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://rate.bot.com.tw/xrt?Lang=zh-TW").get();

            Elements rows = doc.select("table.table tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");
                String currencyName = row.select("div.visible-phone").text().split(" ")[0];
                String buyingRate = cells.get(3).text();

                Map<String, String> rateData = new HashMap<>();
                rateData.put("currency", currencyName);
                rateData.put("buying_rate", buyingRate);

                exchangeRates.add(rateData);
            }
            webCrawlerRepository.updateRates(exchangeRates);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("exchangeRates: " + exchangeRates);
    }
}

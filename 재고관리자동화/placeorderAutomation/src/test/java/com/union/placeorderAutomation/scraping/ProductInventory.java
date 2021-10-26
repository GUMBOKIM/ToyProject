package com.union.placeorderAutomation.scraping;

import com.union.placeorderAutomation.dto.ProductInventoryDto;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductInventory {

    @Test
    public void RestTemplateTest() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "GET");
        httpHeaders.set("path", "https://es-qms.borgwarner.com/qms/kqis9220.query_data?p_companycd=00&p_workcenter=CS&p_x=1&p_plant=&p_stor_loc=&p_part_no=");
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=106076; CCODE=106076");
        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/kqis9220.query_data?p_companycd=00&p_workcenter=CS&p_x=1&p_plant=&p_stor_loc=&p_part_no="); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("user-agent", "XMLHttpRequest");
        httpHeaders.set(HttpHeaders.ACCEPT_ENCODING, "gzip");

        HttpEntity request = new HttpEntity(httpHeaders);

        String url = "https://es-qms.borgwarner.com/qms/kqis9220.query_data?p_companycd=00&p_workcenter=CS&p_x=1&p_plant=&p_stor_loc=&p_part_no=";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );
        String result = response.getBody();
        Pattern pattern = Pattern.compile("(\\{value:\")(.*)(\"\\})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(result);

        int seq = 0;
        ProductInventoryDto inventory = new ProductInventoryDto();
        List<ProductInventoryDto> inventoryList = new ArrayList<>();
        while (matcher.find()) {
            seq = (seq == 10) ? seq - 9 : seq + 1;
            if (matcher.group(2) == null) break;
            String temp = matcher.group(2).replace(".","");
            switch (seq){
                case 1:
                    inventory.setNo(Integer.parseInt(temp));
                    break;
                case 2:
                    inventory.setPlant(temp);
                    break;
                case 3 :
                    inventory.setWhNo(temp);
                    break;
                case 4:
                    inventory.setStorLoc(temp);
                    break;
                case 5:
                    inventory.setPartNo(temp);
                    break;
                case 6:
                    inventory.setStockQTY(Integer.parseInt(temp));
                    break;
                case 7:
                    inventory.setUom(temp);
                    break;
                case 8:
                    inventory.setRop(temp);
                    break;
                case 9:
                    inventory.setLotMin(Integer.parseInt(temp));
                    break;
                case 10:
                    inventory.setLotMax(Integer.parseInt(temp));
                        inventoryList.add(inventory);
                    inventory = new ProductInventoryDto();
                    break;
            }
        }
        System.out.println("inventoryList = " + inventoryList);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }
}

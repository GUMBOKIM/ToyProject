package com.union.placeorderAutomation.scraping;

import com.union.placeorderAutomation.dto.ProductPlanDto;
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

public class ProductPlaning {

    @Test
    public void RestTemplateTest() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "GET");
        httpHeaders.set("path", "/qms/kqis9210__tt.query_data?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no=");
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=106076; CCODE=106076");
        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/kqis9210__tt.query?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no="); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("user-agent", "XMLHttpRequest");
        httpHeaders.set(HttpHeaders.ACCEPT_ENCODING, "gzip");

        HttpEntity request = new HttpEntity(httpHeaders);

        String url = "https://es-qms.borgwarner.com/qms/kqis9210__tt.query_data?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no=";
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
        ProductPlanDto plan = new ProductPlanDto();
        List<ProductPlanDto> planList = new ArrayList<>();
        while (matcher.find()) {
            seq = (seq == 12) ? seq - 11 : seq + 1;
            if (matcher.group(2) == null) break;
            String temp = matcher.group(2).replace(",","");
            switch (seq){
                case 1:
                    plan.setNo(Integer.parseInt(temp));
                    break;
                case 2:
                    plan.setPlant(temp);
                    break;
                case 4 :
                    plan.setWorkCenter(temp);
                    break;
                case 6:
                    plan.setPartNo(temp);
                    break;
                case 7:
                    plan.setTotalQTY(Integer.parseInt(temp));
                    break;
                case 8:
                    plan.setDeliveredQTY(Integer.parseInt(temp));
                    break;
                case 9:
                    plan.setRemainQTY(Integer.parseInt(temp));
                    break;
                case 10:
                    // 고객사 오류로 인해서 자료형 String으로 넣음
                    plan.setSeq(temp);
                    break;
                case 11:
                    plan.setRTime(Integer.parseInt(temp));
                    break;
                case 12:
                    plan.setTTime(Integer.parseInt(temp));
                    if(!plan.getSeq().equals("0")) {
                        planList.add(plan);
                    }
                    plan = new ProductPlanDto();
                    break;
            }
        }
        System.out.println("planList = " + planList);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }
}


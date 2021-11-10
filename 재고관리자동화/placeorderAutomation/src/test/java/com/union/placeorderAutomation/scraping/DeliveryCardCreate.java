package com.union.placeorderAutomation.scraping;

import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryCardCreate {

    @Test
    public void RestTemplateTest() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        String prefix = "https://es-qms.borgwarner.com";
        String url = "/qms/kqis91101.query_data?p_companycd=00&p_vendcd=" + "106076" + "&p_plant=&p_symd=" + "20211110" + "&p_eymd=" + "20211110" + "&p_delivery_no=";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "GET");
        httpHeaders.set("path", url);
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + "106076" + "; CCODE=" + "106076");
        httpHeaders.set("referer", prefix + url); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("user-agent", "XMLHttpRequest");
        httpHeaders.set(HttpHeaders.ACCEPT_ENCODING, "gzip");

        HttpEntity request = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                prefix + url,
                HttpMethod.GET,
                request,
                String.class
        );
        String result = response.getBody();

        Pattern pattern = Pattern.compile("(\\{value:\")(.*)(\"\\})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(result);

        String find = "";
        int seq = 0;
        while (matcher.find()) {
            seq += 1;
            if(seq==12){
             find = matcher.group(2);
            }
        }
        String[] splitList = find.replace("<img border='0' src='/image/button/print.gif'>^javascript:to_Print2(", "")
                .replace(")^_self", "").replace("\\\"", "").split(",");
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }
}

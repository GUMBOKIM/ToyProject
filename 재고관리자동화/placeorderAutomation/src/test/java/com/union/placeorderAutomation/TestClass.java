package com.union.placeorderAutomation;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.assertj.core.api.Assertions.assertThat;

public class TestClass {


    @Test
    public void RestTemplateTest() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("authority", "es-qms.borgwarner.com");
//        httpHeaders.set("method", "POST");
//        httpHeaders.set("path", "/qms/kqis91101.process");
//        httpHeaders.set("scheme", "https");
//        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//        httpHeaders.set("accept-encoding", "gzip, deflate, br");
//        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
//        httpHeaders.set("cache-control", "max-age=0");
//        httpHeaders.set("content-type", "multipart/form-data");
//        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + 106076 + "; CCODE=" + 106076);
//        httpHeaders.set("origin", "https://es-qms.borgwarner.com"); // url이랑 동일
//        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/kqis91101.crRec?p_companycd=00"); // url이랑 동일
//        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
//        httpHeaders.set("sec-ch-ua-mobile", "?0");
//        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
//        httpHeaders.set("sec-fetch-dest", "document");
//        httpHeaders.set("sec-fetch-mode", "navigate");
//        httpHeaders.set("sec-fetch-site", "same-origin");
//        httpHeaders.set("sec-fetch-user", "?1");
//        httpHeaders.set("upgrade-insecure-requests", "1");
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("p_companycd", "00");
//        body.add("p_time","");
//        body.add("p_dml_gubun", "1");
//        body.add("p_vendcd", "106076");
//        body.add("p_plant", "5300");
//        body.add("p_income_date", "20220313");
//        body.add("p_sno", "");
//        body.add("p_bw_lotno", "");
//        body.add("p_delivery_no", "");
//        body.add("p_descr", "테스트");
//
//        body.add("p_partno", "K171103A");
//        body.add("p_partnm", "SP");
//        body.add("p_order_no", "5500009744");
//        body.add("p_lotno", "AAA");
//        body.add("p_qty", "60");
//        body.add("p_qty_per_box", "60");
//        body.add("p_qty_box", "1");
//        body.add("p_remarks", "ROLI-1");
//
//
//        body.add("p_barcode", "");
//        body.add("p_pseqno", "");
//
//        System.out.println("httpHeaders = " + httpHeaders);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
//
//
//        String url = "https://es-qms.borgwarner.com/qms/kqis91101.process";
//        ResponseEntity<String> response = restTemplate.postForEntity(
//                url,
//                request,
//                String.class
//        );
//        String result = response.getBody();
//        System.out.println("result = " + result);
//        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }


}

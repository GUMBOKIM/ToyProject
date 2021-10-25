package com.union.placeorderAutomation.scraping;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.http.HttpClient;

import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryRegistration {

    @Test
    public void RestTemplateTest() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "POST");
        httpHeaders.set("path", "/qms/MIS1150_t_new.PROCESS");
        httpHeaders.set("scheme", "https");
        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cache-control", "max-age=0");
        httpHeaders.set("content-type", "application/x-www-form-urlencoded");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=106076; CCODE=106076");
        httpHeaders.set("origin", "https://es-qms.borgwarner.com"); // url이랑 동일
        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/mis1150_t_new.control"); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "iframe");
        httpHeaders.set("sec-fetch-mode", "navigate");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("sec-fetch-user", "?1");
        httpHeaders.set("upgrade-insecure-requests", "1");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("p_companycd","00");
        body.add("p_dml_gubun","1");
        body.add("p_ym","20211026");
        body.add("p_vendcd","106076");
        body.add("p_time","09:00");

        body.add("p_partno","K170377");
        body.add("p_menge","1");
        body.add("p_lgpbe","LO1");
        body.add("p_barco","N");
        body.add("p_seqno","");
        body.add("p_plant","01");
        body.add("p_po_no","1111");



        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body,httpHeaders);


        String url = "https://es-qms.borgwarner.com/qms/MIS1150_t_new.PROCESS";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
        String result = response.getBody();

        System.out.println("result = " + result);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }
}

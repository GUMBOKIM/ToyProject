package com.union.placeorderAutomation.scraping;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.union.placeorderAutomation.JsonDto;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RestTemplateTest {

    @Test
    public void RestTemplateTest() throws JsonProcessingException, JSONException {
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

        String result = response.getBody()
//                .replace("rows", "\"rows\"")
//                .replace("//","")
//                .replace("data=", "")
//                .replace("id", "\"id\"")
//                .replace("data", "\"data\"")
//                .replace("value:", "")
//                .replace("{\"", "\"")
//                .replace("\"}", "\"")
                ;
        System.out.println("jsonResult = " + result);

        assertThat(response.getStatusCode().equals(HttpStatus.OK));


    }
}


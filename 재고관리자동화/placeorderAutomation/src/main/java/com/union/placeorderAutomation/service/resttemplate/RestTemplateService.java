package com.union.placeorderAutomation.service.resttemplate;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RestTemplateService {


    public void createDeliveryCardManual(String companyCode, String plantCode, String date, String seqNo, List<CreateDeliveryDto> deliveryList) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        setHeaderDeliveryCard(companyCode, httpHeaders);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("p_companycd", "00");
        body.add("p_dml_gubun", "1");
        body.add("p_vendcd", companyCode);
        body.add("p_plant", plantCode);
        body.add("p_sno", seqNo);
        body.add("P_descr", deliveryList.size() + " 품목");
        body.add("p_income_date", date.replace("-", ""));//20210201 양식

        deliveryList.forEach(delivery -> {
            body.add("p_partno", delivery.getBwCode());
            body.add("p_partnm", delivery.getPartName());
            body.add("p_order_no", delivery.getPoCode());
            body.add("p_lotno", delivery.getLot());
            body.add("p_qty", Integer.toString(delivery.getLoadAmount() * delivery.getBoxQuantity()));
            body.add("p_qty_per_box", Integer.toString(delivery.getLoadAmount()));
            body.add("p_qty_box", Integer.toString(delivery.getBoxQuantity()));
            body.add("p_remarks", delivery.getLocation());
        });

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

        String url = "https://es-qms.borgwarner.com/qms/kqis91101.process";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public void createDeliveryCard(String companyCode, String plantCode, String date, List<CreateDeliveryDto> deliveryList) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        setHeaderDeliveryCard(companyCode, httpHeaders);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("p_companycd", "00");
        body.add("p_dml_gubun", "1");
        body.add("p_vendcd", companyCode);
        body.add("p_plant", plantCode);
        // 차수
        //        body.add("p_sno", "");
        body.add("P_descr", deliveryList.size() + " 품목");
        body.add("p_income_date", date.replace("-", ""));//20210201 양식

        deliveryList.forEach(delivery -> {
            body.add("p_partno", delivery.getBwCode());
            body.add("p_partnm", delivery.getPartName());
            body.add("p_order_no", delivery.getPoCode());
            body.add("p_lotno", delivery.getLot());
            body.add("p_qty", Integer.toString(delivery.getLoadAmount() * delivery.getBoxQuantity()));
            body.add("p_qty_per_box", Integer.toString(delivery.getLoadAmount()));
            body.add("p_qty_box", Integer.toString(delivery.getBoxQuantity()));
            body.add("p_remarks", delivery.getLocation());
        });

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

        String url = "https://es-qms.borgwarner.com/qms/kqis91101.process";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public void registryDelivery(String companyCode, String plantCode, String date, String time, List<CreateDeliveryDto> deliveryList) {
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
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + companyCode + "; CCODE=" + companyCode);
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
        body.add("p_companycd", "00");
        body.add("p_dml_gubun", "1");
        body.add("p_ym", date);
        body.add("p_vendcd", companyCode);
        body.add("p_time", time);

        deliveryList.forEach(delivery -> {

            body.add("p_partno", delivery.getBwCode());
            body.add("p_menge", Integer.toString(delivery.getLoadAmount() * delivery.getBoxQuantity()));
            body.add("p_lgpbe", delivery.getLot());
            body.add("p_barco", "Y");
            body.add("p_seqno", "");
            if (plantCode.equals("5300")) {
                body.add("p_plant", "01");
            } else if (plantCode.equals("5330")) {
                body.add("p_plant", "03");
            }
            body.add("p_po_no", delivery.getPoCode());
        });


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);

        String url = "https://es-qms.borgwarner.com/qms/MIS1150_t_new.PROCESS";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
        String result = response.getBody();

        System.out.println("result = " + result);
    }

    public List<ProductPlanDto> getProductPlanning(String companyCode, String plantCode) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        HttpHeaders httpHeaders = new HttpHeaders();
        setHeaderGetProductPlanning(companyCode, plantCode, httpHeaders);

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
            String temp = matcher.group(2).replace(",", "");
            switch (seq) {
                case 2:
                    plan.setPlant(temp);
                    break;
                case 4:
                    plan.setWorkCenter(temp);
                    break;
                case 6:
                    plan.setBomBwCode(temp);
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
                    if ((!plan.getSeq().equals("0")) && plan.getPlant().equals(plantCode)) {
                        planList.add(plan);
                    }
                    plan = new ProductPlanDto();
                    break;
            }
        }
        return planList;
    }

    public String[] findDeliveryCard(String companyCode, String date, String plantCode) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        String prefix = "https://es-qms.borgwarner.com";
        String url = "/qms/kqis91101.query_data?p_companycd=00&p_vendcd=" + companyCode + "&p_plant=" + plantCode + "&p_symd=" + date + "&p_eymd=" + date + "&p_delivery_no=";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "GET");
        httpHeaders.set("path", url);
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + companyCode + "; CCODE=" + companyCode);
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
            if (seq == 12) {
                find = matcher.group(2);
            }
        }
        String[] splitList = find.replace("<img border='0' src='/image/button/print.gif'>^javascript:to_Print2(", "")
                .replace(")^_self", "").replace("\\\"", "").split(",");
        return splitList;
    }

    public List<ProductInventoryDto> getPartInventory(String companyCode, String plantCode) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        HttpHeaders httpHeaders = new HttpHeaders();
        setHeaderGetProductInvetory(companyCode, plantCode, httpHeaders);

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
            String temp = matcher.group(2).replace(".", "");
            switch (seq) {
                case 1:
                    inventory.setNo(Integer.parseInt(temp));
                    break;
                case 2:
                    inventory.setPlant(temp);
                    break;
                case 3:
                    inventory.setWhNo(temp);
                    break;
                case 4:
                    inventory.setStoreLocation(temp);
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
                    if (inventory.getPlant().equals(plantCode)) {
                        inventoryList.add(inventory);
                    }
                    inventory = new ProductInventoryDto();
                    break;
            }
        }
        return inventoryList;
    }

    private void setHeaderGetProductInvetory(String companyCode, String plantCode, HttpHeaders httpHeaders) {
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "GET");
        httpHeaders.set("path", "https://es-qms.borgwarner.com/qms/kqis9220.query_data?p_companycd=00&p_workcenter=CS&p_x=1&p_plant=" + plantCode + "&p_stor_loc=&p_part_no=");
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + companyCode + "; CCODE=" + companyCode);
        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/kqis9220.query_data?p_companycd=00&p_workcenter=CS&p_x=1&p_plant=" + plantCode + "&p_stor_loc=&p_part_no="); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("user-agent", "XMLHttpRequest");
        httpHeaders.set(HttpHeaders.ACCEPT_ENCODING, "gzip");
    }


    private void setHeaderGetProductPlanning(String companyCode, String plantCode, HttpHeaders httpHeaders) {
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "GET");
        httpHeaders.set("path", "/qms/kqis9210__tt.query_data?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no=");
        httpHeaders.set("accept", "*/*");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + companyCode + "; CCODE=" + companyCode);
        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/kqis9210__tt.query?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no="); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("user-agent", "XMLHttpRequest");
        httpHeaders.set(HttpHeaders.ACCEPT_ENCODING, "gzip");
    }

    private void setHeaderDeliveryCard(String companyCode, HttpHeaders httpHeaders) {
        httpHeaders.set("authority", "es-qms.borgwarner.com");
        httpHeaders.set("method", "POST");
        httpHeaders.set("path", "/qms/kqis91101.process");
        httpHeaders.set("scheme", "https");
        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpHeaders.set("accept-encoding", "gzip, deflate, br");
        httpHeaders.set("accept-language", "en,ko;q=0.9,fr;q=0.8");
        httpHeaders.set("cache-control", "max-age=0");
        httpHeaders.set("cookie", "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=" + companyCode + "; CCODE=" + companyCode);
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryea1qGVBFGzKiRrEa");
        httpHeaders.set("referer", "https://es-qms.borgwarner.com/qms/kqis91101.crRec?p_companycd=00"); // url이랑 동일
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "navigate");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("sec-fetch-user", "?1");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
    }


}

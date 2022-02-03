package union.seosan.resttemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import union.seosan.dto.test.SearchDto;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


public class ResttemplateLoginTest {

    @Test
    void 로그인_뷰() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/loginForm.do";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Connection", "keep-alive");

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("response = " + response.getHeaders());

        Pattern pattern = Pattern.compile("(name=\"_csrf\" value=\")(.*)(\")", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(response.getBody());
        matcher.find();
        String CSRF = matcher.group(2);
        System.out.println("CSRF = " + CSRF);

        String SCOUTER = response.getHeaders().get("Set-Cookie").get(0).substring(8, 22);
        System.out.println("SCOUTER = " + SCOUTER);
        String JSESSIONID = response.getHeaders().get("Set-Cookie").get(1).substring(11, 43);
        System.out.println("JSESSIONID = " + JSESSIONID);

        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void 로그인_처리() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/loginProcess.do";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Connection", "keep-alive");
        headers.set("Cookie", "SCOUTER=z6q1o5cf52ml73; JSESSIONID=12C636F8D0A4FD57EAA9EC1EDADDAF48");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("_csrf", "18087613-3036-4483-aff9-1f6eec1f117b");
        map.add("userId", "T008002");
        map.add("dupCheck", "");
        map.add("password", "audCJS88!");
        map.add("password_change", "");
        map.add("password_confirm", "");
        map.add("sessionDisConnectFlag","N");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println("response = " + response);
        String JSESSIONID = response.getHeaders().get("Set-Cookie").get(0).substring(11, 43);
        System.out.println("JSESSIONID = " + JSESSIONID);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void 로그인_성공() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/loginSuccess.do";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "JSESSIONID=EFC573E078B4D353DBFB8E72D591A71A");
        headers.set("Connection", "keep-alive");
        headers.set("Cache-Control","max-age=0");
        headers.set("Upgrade-Insecure-Requests","1");
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        Pattern pattern = Pattern.compile("(name=\"_csrf\" th:content=\")(.*)(\")", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(response.getBody());
        matcher.find();
        String CSRF = matcher.group(2);
        System.out.println("CSRF = " + CSRF);

        String SCOUTER = response.getHeaders().get("Set-Cookie").get(0).substring(8, 22);
        System.out.println("SCOUTER = " + SCOUTER);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }
//
//
//    @Test
//    void 월간_생산계획() throws JsonProcessingException {
//        로그인_성공();
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://lo-tgps.hyundai-transys.com/center/lo/em/WLOEMPL001/selectWLOEMPL001List.do";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
//        headers.set("Accept-Encoding", "gzip, deflate, br");
//        headers.set("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
//        headers.set("AJAX", "true");
//        headers.set("Connection", "keep-alive");
//        headers.set("Content-Type", "application/json; charset=UTF-8");
//        headers.set("Cookie", "SCOUTER=x5pbo2e344vo9s; JSESSIONID=C398532801ADBF64C8D6BADA67B0376D");
//        headers.set("Host", "lo-tgps.hyundai-transys.com");
//        headers.set("Origin", "https://lo-tgps.hyundai-transys.com");
//        headers.set("Sec-Fetch-Dest", "empty");
//        headers.set("Sec-Fetch-Mode", "cors");
//        headers.set("Sec-Fetch-Site", "same-origin");
//        headers.set("Referer", "https://lo-tgps.hyundai-transys.com/loginSuccess.do");
//        headers.set("X-CSRF-TOKEN", "d0eb8a49-d6ee-4a54-bf97-2f05a9549bb2");
//        headers.set("X-Requested-With", "XMLHttpRequest");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(new SearchDto()), headers);
//        System.out.println("request.getBody() = " + request.getBody());
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//        System.out.println("response = " + response);
//        assertThat(response.getStatusCode().equals(HttpStatus.OK));
//    }

    @Test
    void 납입카드_발행_부품_정보_찾기() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/center/lo/comm/PartPopup/selectPartByVendorInfo.do";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "SCOUTER=x64ko7piqv51l2; JSESSIONID=EFC573E078B4D353DBFB8E72D591A71A");
        headers.set("X-CSRF-TOKEN", "610407b5-684b-4683-a358-b894ace5c727");

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, String> innerMap = new HashMap<>();
        innerMap.put("coporateCd", "1000");
        innerMap.put("partCd", "45455-4C000");
        innerMap.put("plantCd", "1101");
        innerMap.put("useFlag", "Y");
        innerMap.put("vendorCd", "S003");
        hashMap.put("map", innerMap);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(hashMap), headers);
        System.out.println("request.getBody() = " + request.getBody());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println("response = " + response.getBody());
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void 납입카드_발행전1() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/center/lo/comm/ComCd/getValueInspFlag.do";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "SCOUTER=x64ko7piqv51l2; JSESSIONID=EFC573E078B4D353DBFB8E72D591A71A");
        headers.set("X-CSRF-TOKEN", "610407b5-684b-4683-a358-b894ace5c727");
        String str = "{\"map\":{\"plantCd\":\"1101\",\"vendorCd\":\"S003\",\"partCd\":\"45455-4C000\"}}";

        HttpEntity<String> request = new HttpEntity<>(str, headers);
        System.out.println("request.getBody() = " + request.getBody());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println("response = " + response.getBody());
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void 납입카드_발행전2() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/center/lo/comm/ComCd/selectAbleDeliQty.do";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "SCOUTER=x64ko7piqv51l2; JSESSIONID=EFC573E078B4D353DBFB8E72D591A71A");
        headers.set("X-CSRF-TOKEN", "610407b5-684b-4683-a358-b894ace5c727");
        String str = "{\"map\":{\"plantCd\":\"1101\",\"vendorCd\":\"S003\",\"partCd\":\"45455-4C000\"}}";

        HttpEntity<String> request = new HttpEntity<>(str, headers);
        System.out.println("request.getBody() = " + request.getBody());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println("response = " + response.getBody());
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void 납입카드_발행전3() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/center/lo/comm/ComCd/selectValidateDeli.do";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "SCOUTER=x64ko7piqv51l2; JSESSIONID=EFC573E078B4D353DBFB8E72D591A71A");
        headers.set("X-CSRF-TOKEN", "610407b5-684b-4683-a358-b894ace5c727");
        String str = "{\"map\":{\"plantCd\":\"1101\",\"vendorCd\":\"S003\",\"partCd\":\"45455-4C000\"}}";

        HttpEntity<String> request = new HttpEntity<>(str, headers);
        System.out.println("request.getBody() = " + request.getBody());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println("response = " + response.getBody());
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }



    @Test
    void 납입카드_발행() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://lo-tgps.hyundai-transys.com/center/lo/em/WLOEMDL010/svWLOEMDL010PopupData.do";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "SCOUTER=x64ko7piqv51l2; JSESSIONID=EFC573E078B4D353DBFB8E72D591A71A");
        headers.set("X-CSRF-TOKEN", "610407b5-684b-4683-a358-b894ace5c727");
        String str = "{\"map\":{\"funcType\":\"I\",\"coporateCd\":\"1000\",\"sPlantCd\":\"1101\",\"deliNo\":\"\",\"deliType\":\"N\",\"deliCase\":\"10\",\"deliSt\":\"10\",\"workDt\":\"20220203\",\"workSeq\":\"\",\"vendorCd\":\"S003\",\"arriCd\":\"R100\",\"arriNm\":\"[R100] 원자재 원창(중대형)\",\"arriDt\":\"20220203\",\"arriTm\":\"1100\",\"aprAuto\":\"Y\",\"vendCd\":\"T008\",\"deliBarNo\":\"[45455-4C000=240]\",\"vendInvoiceNo\":\"\",\"userVendorGb\":\"B\",\"logiCd\":\"T008\"},\"voList\":[{\"status\":\"I\",\"id\":\"jqg_2_0\",\"coporateCd\":\"1000\",\"plantCd\":\"1101\",\"partCd\":\"45455-4C000\",\"partNm\":\"SPRING-SERVO\",\"boxInQty\":240,\"partKg\":0.012,\"boxKg\":0.769,\"totalWeight\":3.649,\"limitKg\":0,\"aprAuto\":\"Y\",\"deliPossQty\":\"720\",\"deliQty\":\"240\",\"boxQty\":1,\"inputBoxYn\":null}],\"_selected_indexList\":[0]}";

        HttpEntity<String> request = new HttpEntity<>(str, headers);
        System.out.println("request.getBody() = " + request.getBody());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println("response = " + response.getBody());
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

}

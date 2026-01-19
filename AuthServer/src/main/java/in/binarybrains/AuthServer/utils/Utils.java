package in.binarybrains.AuthServer.utils;

import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class Utils {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Object> postApiCall(String url, Map<String, Object> requestBody){
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody); // request body

        ResponseEntity<Object> response = restTemplate
//                .exchange("http://127.0.0.1:7070/payment", HttpMethod.POST, request, Object.class);
                .exchange(url, HttpMethod.POST, request, Object.class);

        return response;
    }
    public ResponseEntity<Object> getApiCall(String url){


        ResponseEntity<Object> response = restTemplate
//                .exchange("http://127.0.0.1:7070/payment", HttpMethod.POST, request, Object.class);
                .exchange(url, HttpMethod.GET, null, Object.class);

        return response;
    }
}

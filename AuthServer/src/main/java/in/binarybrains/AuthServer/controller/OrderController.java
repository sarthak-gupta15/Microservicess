package in.binarybrains.AuthServer.controller;


import in.binarybrains.AuthServer.dto.ApiResponse;
import in.binarybrains.AuthServer.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    Utils utils;

    @Value("${host.name}")
    String host;

    @Value("${order.port}")
    String port;

    @PostMapping("/place-order")
    ResponseEntity<Object> placeOrder(@RequestBody Map<String, Object> orderRequestDTO){
        log.info("request received in api gateway");
        String url = host+port+"/place-order";
        return utils.postApiCall(url, orderRequestDTO);


    }
}

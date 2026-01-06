package in.binarybrains.OrderService.controller;

import in.binarybrains.OrderService.dto.ApiResponse;
import in.binarybrains.OrderService.dto.OrderRequestDTO;
import in.binarybrains.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place-order")
    ResponseEntity<ApiResponse> placeOrder( @RequestBody OrderRequestDTO orderRequestDTO){
//        ApiResponse response = orderService.placeOrder(orderRequestDTO);
        ApiResponse response = orderService.placeOrderWithKafka(orderRequestDTO);
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));

    }
}

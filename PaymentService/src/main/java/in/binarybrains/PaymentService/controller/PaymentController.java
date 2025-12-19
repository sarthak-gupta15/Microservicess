package in.binarybrains.PaymentService.controller;

import in.binarybrains.PaymentService.dto.ApiResponse;
import in.binarybrains.PaymentService.dto.PaymentRequestDTO;
import in.binarybrains.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment")
    ResponseEntity<ApiResponse> doPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        ApiResponse response = paymentService.doPayment(paymentRequestDTO);
        return  new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }
}

package in.binarybrains.PaymentService.controller;

import in.binarybrains.PaymentService.dto.ApiResponse;
import in.binarybrains.PaymentService.dto.PaymentRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class PaymentController {

    @PostMapping("/payment")
    ResponseEntity<ApiResponse> doPayment(PaymentRequestDTO paymentRequestDTO){

    }
}

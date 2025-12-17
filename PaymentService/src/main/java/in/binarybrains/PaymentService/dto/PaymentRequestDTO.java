package in.binarybrains.PaymentService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    long userId;
    Double amount;
    String productId;
}

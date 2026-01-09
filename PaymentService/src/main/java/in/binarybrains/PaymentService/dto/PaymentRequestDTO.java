package in.binarybrains.PaymentService.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequestDTO {
    long userId;
    Double amount;
    String productId;
    UUID orderId;
}

package in.binarybrains.OrderService.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequestDTO {
    long userId;
    Double amount;
    String productId;
}

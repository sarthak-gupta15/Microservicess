package in.binarybrains.PaymentService.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class OrderKafkaDTO {
    UUID orderId;
    String remark;
    String orderStatus;
}

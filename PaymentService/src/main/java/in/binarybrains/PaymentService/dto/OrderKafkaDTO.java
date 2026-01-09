package in.binarybrains.PaymentService.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public class OrderKafkaDTO {
    UUID orderId;
    String remark;
    String orderStatus;
}

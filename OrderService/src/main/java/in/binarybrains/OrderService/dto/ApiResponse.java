package in.binarybrains.OrderService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    String message;
    String result;
    Integer code;
}

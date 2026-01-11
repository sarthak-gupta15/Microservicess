package in.binarybrains.AuthServer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    String message;
    String result;
    Integer code;
}

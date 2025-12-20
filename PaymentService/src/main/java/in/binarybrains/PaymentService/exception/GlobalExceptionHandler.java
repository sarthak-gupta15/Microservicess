package in.binarybrains.PaymentService.exception;

import in.binarybrains.PaymentService.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntimeEx(RuntimeException ex){
        return ResponseEntity.internalServerError().body(ApiResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message("Error : "+ex.getMessage())
              .result("Failed")
              .build());
    }
}

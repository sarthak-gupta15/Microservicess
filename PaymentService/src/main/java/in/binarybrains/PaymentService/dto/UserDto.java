package in.binarybrains.PaymentService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private BigDecimal balance;
    private Boolean isActive;
}

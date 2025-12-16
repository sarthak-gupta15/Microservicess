package in.binarybrains.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    String plotNum;
    String addressLine1;
    String addressLine2;
    String pinCode;
    String state;
    String city;
    String mob;
}

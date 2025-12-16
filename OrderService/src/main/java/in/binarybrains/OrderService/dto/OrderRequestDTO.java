package in.binarybrains.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    String user_Id;
    String product_Id;
    AddressDTO address;
}

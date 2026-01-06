package in.binarybrains.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    String user_Id;
    Long product_Id;
    AddressDTO address;
}

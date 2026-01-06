package in.binarybrains.OrderService.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class OrderDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer orderId;
    String user_Id;
    String product_Id;
    @OneToOne
    AddressModel address;
    String expectedDeliveryDate;
    String orderStatus;
    String remark;

}

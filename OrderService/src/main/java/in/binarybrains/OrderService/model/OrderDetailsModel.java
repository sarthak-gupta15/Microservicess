package in.binarybrains.OrderService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsModel {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID orderId;
    String user_Id;
    String product_Id;
    @OneToOne
    AddressModel address;
    String expectedDeliveryDate;
    String orderStatus;
    String remark;

}

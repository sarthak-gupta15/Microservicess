package in.binarybrains.PaymentService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class OrderTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID Id;
    String userId;
    String amount;
    String productId;

    String status;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

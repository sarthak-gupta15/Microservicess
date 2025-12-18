package in.binarybrains.PaymentService.repository;

import in.binarybrains.PaymentService.model.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderTransactionRepo extends JpaRepository<OrderTransaction, UUID> {
}

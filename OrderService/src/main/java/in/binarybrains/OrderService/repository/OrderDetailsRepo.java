package in.binarybrains.OrderService.repository;

import in.binarybrains.OrderService.model.OrderDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetailsModel, Integer> {
}

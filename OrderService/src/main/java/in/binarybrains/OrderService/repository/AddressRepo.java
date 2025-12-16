package in.binarybrains.OrderService.repository;

import in.binarybrains.OrderService.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressModel, Integer> {
}

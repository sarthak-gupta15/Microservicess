package in.binarybrains.OrderService.repository;

import in.binarybrains.OrderService.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<ProductModel, Long> {
}

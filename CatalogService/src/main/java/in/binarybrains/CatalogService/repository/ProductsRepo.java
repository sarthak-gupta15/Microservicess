package in.binarybrains.CatalogService.repository;


import in.binarybrains.CatalogService.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepo extends JpaRepository<ProductModel, Long> {


    Optional<List<ProductModel>> findByProductNameIgnoreCaseContaining(String query);
}

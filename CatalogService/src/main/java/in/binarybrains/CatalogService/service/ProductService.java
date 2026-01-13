package in.binarybrains.CatalogService.service;


import in.binarybrains.CatalogService.model.ProductModel;
import in.binarybrains.CatalogService.repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductsRepo productsRepo;

    public List<ProductModel> searchProduct(String searchQuery){
        if(!searchQuery.isEmpty() && !searchQuery.isBlank()){
            Optional<List<ProductModel>> opProductList = productsRepo.findByProductNameIgnoreCaseContaining(searchQuery);
            if(opProductList.isPresent()){
                List<ProductModel> productList = opProductList.get();
                return productList;
            }
        }
//        select * from product where productname like %shirt%;
        return null;
    }
}

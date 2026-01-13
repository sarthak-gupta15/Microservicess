package in.binarybrains.CatalogService.controller;

import in.binarybrains.CatalogService.model.ProductModel;
import in.binarybrains.CatalogService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/search-product")
    ResponseEntity<List<ProductModel>> searchProduct(@RequestParam String searchQuery){

        return ResponseEntity.ok(productService.searchProduct(searchQuery));
    }
}

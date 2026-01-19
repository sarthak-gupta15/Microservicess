package in.binarybrains.AuthServer.controller;


import in.binarybrains.AuthServer.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

    @Value("${host.name}")
    String host;

    @Value("${catalog.port}")
    String port;

    @Autowired
    Utils utils;

    @GetMapping("/search-product")
    ResponseEntity<Object> searchProduct(@RequestParam String searchQuery){
        String url = host+port+"/search-product" +"?searchQuery="+searchQuery;
        return utils.getApiCall(url);
    }
}

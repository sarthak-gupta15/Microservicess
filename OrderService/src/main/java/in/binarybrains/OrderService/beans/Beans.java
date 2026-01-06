package in.binarybrains.OrderService.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

    @Bean
    RestTemplate getRest(){
        return new RestTemplate();
    }
}

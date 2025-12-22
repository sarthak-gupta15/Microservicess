package in.binarybrains.AuthServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class Config {

//    @Bean
//    RestTemplate getrest(){ // Object store in spring Ioc
//    @Autowired
//        return new RestTemplate();
//    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http){
        SecurityFilterChain securityFilterChain = http.build();
        return  securityFilterChain;
    }

}

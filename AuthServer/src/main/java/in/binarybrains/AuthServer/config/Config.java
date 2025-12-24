package in.binarybrains.AuthServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

    Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
        @Override
        public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
            httpSecurityCsrfConfigurer.disable();
        }
    };

    Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authCustomizer;

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http){
        http.csrf(csrfCustomizer); // csrf token disable
        http.csrf((custom) -> custom.disable() );
        http.authorizeHttpRequests(req -> req.anyRequest().authenticated());
//        http.formLogin(Customizer.withDefaults()); // browser form
        http.httpBasic(Customizer.withDefaults()); // postmen form
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        SecurityFilterChain securityFilterChain = http.build();
        return  securityFilterChain;
    }

}

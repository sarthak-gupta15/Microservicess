package in.binarybrains.AuthServer.config;

import in.binarybrains.AuthServer.filter.JWTFilter;
import in.binarybrains.AuthServer.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config {
// @Configuration load first in any server
//    customUserDetailsService class was not loaded in the memory
//    but if we do dependency injection and ask springboot for object it load that class forcefully
    @Autowired
    UserDetailsService userDetailsService ;
//    UserDetailsService userDetailsService = new CustomUserDetailsService();

//    @Bean
//    RestTemplate getrest(){ // Object store in spring Ioc
//    @Autowired
//        return new RestTemplate();
//    }

    @Autowired
    JWTFilter jwtFilter;

    Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
        @Override
        public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
            httpSecurityCsrfConfigurer.disable();
        }
    };

    Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authCustomizer;

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http){
//        http.csrf(csrfCustomizer); // csrf token disable
        http.csrf((custom) -> custom.disable() );
//        http.authorizeHttpRequests(req -> {
//            req.requestMatchers("login", "register").permitAll();
//            req.anyRequest().authenticated();});
        http.authorizeHttpRequests(req -> req
                .requestMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated());
//        http.formLogin(Customizer.withDefaults()); // browser form
        http.httpBasic(Customizer.withDefaults()); // postmen form
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        SecurityFilterChain securityFilterChain = http.build();
        return  securityFilterChain;
    }

//    userDeatailAuthenctor filter
//    @Bean
//    public UserDetailsService getUserDetailsService(){
//        UserDetails user1 = User.
//                withDefaultPasswordEncoder()
//                .username("pranay")
//                .password("pranay@123")
//                .build();
//        UserDetails user2 = User.
//                withDefaultPasswordEncoder()
//                .username("nikhil")
//                .password("nikhil@123")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//
//    }

//    repo = dao

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // encrip
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // encrip
//        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager customAuthManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }




}

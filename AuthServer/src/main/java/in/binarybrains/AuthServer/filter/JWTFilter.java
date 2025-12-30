package in.binarybrains.AuthServer.filter;

import in.binarybrains.AuthServer.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
// Bearer eyJhbGciOiJIUzM4NCJ9.eyJoZWxsbyI6IndvcmxkIiwic3ViIjoic2FydGhhayIsImlhdCI6MTc2NzA2Mzk1OSwiZXhwIjoxNzY3MDY0MDY3fQ.Fksk9MAeU4EXBJSfd9WL9VcYmp15ToeuO2V3PchDe1ZVEPw0Zk7AaxCQXD-DwTmD
        String token = request.getHeader("Authorization");

        if(token!= null && token.trim().contains("")){
            token = token.substring(7);
            String userName = jwtService.extractUserName(token);


        }
    }
}

package in.binarybrains.AuthServer.filter;

import in.binarybrains.AuthServer.model.CustomUserDetails;
import in.binarybrains.AuthServer.model.User;
import in.binarybrains.AuthServer.service.CustomUserDetailsService;
import in.binarybrains.AuthServer.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

//    @Autowired
    UserDetails userDetails;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
// Bearer eyJhbGciOiJIUzM4NCJ9.eyJoZWxsbyI6IndvcmxkIiwic3ViIjoic2FydGhhayIsImlhdCI6MTc2NzA2Mzk1OSwiZXhwIjoxNzY3MDY0MDY3fQ.Fksk9MAeU4EXBJSfd9WL9VcYmp15ToeuO2V3PchDe1ZVEPw0Zk7AaxCQXD-DwTmD
        String token = request.getHeader("Authorization");
        String userName = null;
        if(token!= null && token.trim().contains("")){
            token = token.substring(7);
            userName = jwtService.extractUserName(token);
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            userDetails = new CustomUserDetailsService().loadUserByUsername(userName);
            userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(userName);
            if(jwtService.validateToken(token, userName, userDetails)){
                UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
//            userPassToken.setAuthenticated(jwtService.validateToken(token, userName, userDetails));
                userPassToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userPassToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}

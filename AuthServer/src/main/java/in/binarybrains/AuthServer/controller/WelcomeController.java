package in.binarybrains.AuthServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome(HttpServletRequest request){
        String sid = request.getSession().getId();

        return "welcome to spring security "+sid;
    }

    @GetMapping("/csrf")
    public CsrfToken createCsrf(HttpServletRequest request){
         CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
         return csrf;

    }

    @PostMapping("/student")
    public String create(){
        return "create student";
    }
}

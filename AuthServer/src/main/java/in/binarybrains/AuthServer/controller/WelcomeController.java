package in.binarybrains.AuthServer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome(HttpServletRequest request){
        request.getSession().getId();
        String key = request.getHeader("_csrf");
        return "welcome to spring security "+key;
    }

    @PostMapping("/student")
    public String create(){
        return "create student";
    }
}

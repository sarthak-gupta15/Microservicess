package in.binarybrains.AuthServer.controller;

import in.binarybrains.AuthServer.model.User;
import in.binarybrains.AuthServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody(required = false) User user){
        return ResponseEntity.ok(userService.login(user));
    }
}

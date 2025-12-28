package in.binarybrains.AuthServer.service;

import org.springframework.stereotype.Service;

@Service
public class JWTService {

    public String genrateToken(){
        return "Token JWT";
    }
}

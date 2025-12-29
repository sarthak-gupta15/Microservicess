package in.binarybrains.AuthServer.service;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    String secretKey = "5367577859703373367639792S423F452848284D6251655468576D5A71347437";

    public String genrateToken(String userName){
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*30)) //3 min
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey(){
        byte[] keyInBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyInBytes);
    }
}

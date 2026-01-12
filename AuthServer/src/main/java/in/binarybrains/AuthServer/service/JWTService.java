package in.binarybrains.AuthServer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
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
        claims.put("hello", "world");
        claims.put("role", "user");

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*300)) //30 min
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        byte[] keyInBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyInBytes);
    }

    public String extractUserName(String token){
//        extract claims (JWts parser claims by using key)
//        all claims
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();

    }

    public Boolean extractExpireTime(String token){
//        extract claims (JWts parser claims by using key)
//        all claims
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return !claims.getExpiration().before(new Date());

    }

    public Boolean validateToken(String token, String userName, UserDetails userDetails){
        return userName.equals(userDetails.getUsername()) &&  extractExpireTime(token);
    }
}

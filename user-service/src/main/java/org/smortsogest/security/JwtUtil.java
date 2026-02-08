package org.smortsogest.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String key;
    private final Long expiration;

    JwtUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.expiration}") Long expiration
    ){
        this.key = secret;
        this.expiration = expiration;
    }

    private SecretKey getSignedKey(){return Keys.hmacShaKeyFor(key.getBytes());}

    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public String generateToken(String subject, Map<String,Object> claims){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+expiration);
        return Jwts.builder().setClaims(claims != null?claims:new HashMap<>())
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpirationInSeconds(){return expiration / 100;}
}

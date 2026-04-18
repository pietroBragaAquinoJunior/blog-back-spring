package com.pietro.blog_back_spring.security;

import java.security.KeyPair;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;


@Service
public class JwtService {

    private static final SignatureAlgorithm alg = Jwts.SIG.RS512;
    private static final KeyPair pair = alg.keyPair().build();
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 horas

    // Gerando com a chave privada.
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) 
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(pair.getPrivate(), alg)
                .compact();
    }

    // Verificando com a chave publica.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String subject = Jwts.parser()
            .verifyWith(pair.getPublic())
            .build().parseSignedClaims(token).getPayload().getSubject();

        Date expiration = Jwts.parser().verifyWith(pair.getPublic())
            .build().parseSignedClaims(token).getPayload().getExpiration();
            
        if(subject.equals(userDetails.getUsername()) && new Date().before(expiration)){
            return true;
        } else{
            return false;
        }
    }

}
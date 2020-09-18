package com.qlnsitsol.demo.jwt;
import com.qlnsitsol.demo.DTO.MainUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;
    public String generateToken(Authentication authentication){
        MainUser mainUser = (MainUser) authentication.getPrincipal();
        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    public String getEmailFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken2(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException e){
            logger.error("error comprobando el token");
            e.printStackTrace();
        }
        return false;
    }


    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("token expirado");
        }catch (IllegalArgumentException e){
            logger.error("token vacío");
        }catch (SignatureException e){
            logger.error("fail en la firma");
        }
        return false;
    }
}

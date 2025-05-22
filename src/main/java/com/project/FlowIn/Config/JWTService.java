package com.project.FlowIn.Config;
import com.project.FlowIn.Usuario.Domain.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long EXPIRATION_TIME = 86400000; // 1 día

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getUsername()) // sigue siendo el claim principal
                .claim("id", usuario.getId())
                .claim("rol", usuario.getTipo().name()) // tipo es un enum
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String extractUserName(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (userDetails.getUsername().equals(extractUserName(token)) && !isTokenExpired(token));
    }
    public Long extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    public String extractRol(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("rol", String.class);
    }
}

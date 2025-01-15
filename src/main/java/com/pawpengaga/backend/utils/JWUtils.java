package com.pawpengaga.backend.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWUtils {

  @Value("${security.key}")
  private String privatekey;

  @Value("${security.user}")
  private String userGenerator;

  public String createToken(Authentication authentication){
    Algorithm algorithm = Algorithm.HMAC256(privatekey);
    String username = authentication.getPrincipal().toString();

    String authorities = authentication.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    String jwtToken = JWT.create()
      .withIssuer(userGenerator)
      .withSubject(username)
      .withClaim("authorities", authorities)
      .withIssuedAt(new Date())
      .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // Media hora de direcion del token
      .withJWTId(UUID.randomUUID().toString())
      .withNotBefore(new Date(System.currentTimeMillis()))
      .sign(algorithm);
    
    return jwtToken;

  }
  
  public DecodedJWT validateToken(String token){
    try {
      Algorithm algorithm = Algorithm.HMAC256(privatekey);

      JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(userGenerator)
        .build();

      DecodedJWT decoded = verifier.verify(token);

      return decoded;

    } catch (JWTVerificationException e) {
      throw new JWTVerificationException("Token inválido: Solicitudes no autorizadas.");
    }
  }

  /* ********************************* METODOS UTILES ********************************* */

  // Obtener el nombre de usuarios
  public String extractUserName(DecodedJWT decoded){
    return decoded.getSubject().toLowerCase();
  }

  // Obtener un claim especifico del payload
  public Claim getSpecifiedClaim(DecodedJWT decoded, String claimName){
    return decoded.getClaim(claimName);
  }

  // Un metodo que nos devuelva todos los claims
  public Map<String, Claim> getAllClaims(DecodedJWT decoded){
    return decoded.getClaims();
  }
}

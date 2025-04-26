package com.backend.recipeManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  @Value("${jwt.secret}")
  String secret;

  // Generate token and adding all the required details
  public final String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();

    // Add roles
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    claims.put("roles", roles);

    // Testing for Multiple Roles - Not done
    // List<String> roles =
    //     userDetails.getAuthorities().stream()
    //         .map(GrantedAuthority::getAuthority)
    //         .map(role -> role.replace("ROLE_", "")) // optional cleanup
    //         .collect(Collectors.toList());

    // System.out.println(roles);

    // claims.put("roles", roles);

    SecretKey keys = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder()
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 600000)) // 10 Minutes
        .signWith(keys, SIG.HS256)
        .compact();
  }

  // Extracting username from the token
  public String extractUsername(String token) {
    Claims body = extractClaims(token);
    return body.getSubject();
  }

  // Extract details from the token
  private Claims extractClaims(String token) {
    SecretKey keys = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.parser().verifyWith(keys).build().parseSignedClaims(token).getPayload();
  }

  // Validate token to ensure token is not expired and username is same between token and
  // database
  public Boolean validateToken(String username, UserDetails userDetails, String token) {
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  // Extract details from token and check if expiration time is before current time
  private Boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }

  // Extract the Roles from the token
  // Multiple Roles to be fix here
  public List<String> extractRoles(String token) {
    Claims claims = extractClaims(token);
    List<String> roles = claims.get("roles", List.class);
    return roles;
  }
}

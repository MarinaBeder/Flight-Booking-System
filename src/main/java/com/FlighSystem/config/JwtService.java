package com.FlighSystem.config;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtService {
	//GENERATE SECRET KEY ONLINE//https://www.allkeysgenerator.com/

private String secretKey="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
private long jwtExpiration = 1000L * 60 * 60 * 24 * 7; // 1 week in ms
private long refreshExpiration = 1000L * 60 * 60 * 24 * 30; // ~1 month in ms

public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
	  Map<String, Object> claims = new HashMap<>();

	  // Add authorities (roles)
	  claims.put(
	    "authorities",
	    userDetails.getAuthorities()
	               .stream()
	               .map(auth -> auth.getAuthority())
	               .collect(Collectors.toList())
	  );
	  
    return generateToken(new HashMap<>(), userDetails);
  }

 
  
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }
 
  public String generateRefreshToken(
	      UserDetails userDetails
	  ) {
	    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	  }
  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }
 
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}

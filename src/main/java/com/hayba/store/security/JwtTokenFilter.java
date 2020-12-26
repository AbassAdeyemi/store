package com.hayba.store.security;

import com.hayba.store.model.JwtConfig;
import com.hayba.store.model.JwtKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String header = request.getHeader(jwtConfig.getAuthorizationHeader());

    if (StringUtils.isBlank(header) || StringUtils.isEmpty(header)  || !header.startsWith(jwtConfig.getTokenPrefix())) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.replace(jwtConfig.getAuthorizationHeader(), "");

    try{

    Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

    Claims body = claimsJws.getBody();

    String username = body.getSubject();

    var authorities = (List<String>) body.get("authorities");

    System.out.println(authorities);

    Set<SimpleGrantedAuthority> grantedAuthorities =
        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

    Authentication auth =
        new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

    SecurityContextHolder.getContext().setAuthentication(auth);
    }
    catch (JwtException e) {
        SecurityContextHolder.clearContext();
        throw new IllegalArgumentException("Token cannot be trusted: "+token);
    }
  }

}

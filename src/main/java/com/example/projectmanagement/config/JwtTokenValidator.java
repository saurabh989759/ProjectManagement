package com.example.projectmanagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
       String jwt = request.getHeader(JwtConstant.JWT_HEADER);
//       Bearer jwt we alaways get the jwt token in this format first the bearer
       if(jwt != null){
           jwt = jwt.replace("Bearer ", "");
           try {
               SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
               Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

               String email = String.valueOf(claims.get("email"));
               String authorities = String.valueOf(claims.get("authorities"));

               List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

               Authentication authentication = new UsernamePasswordAuthenticationToken(email, authorities);
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }catch (Exception e){
               throw new BadCredentialsException("invalid token ........");
           }
       }
       filterChain.doFilter(request, response);
    }
}

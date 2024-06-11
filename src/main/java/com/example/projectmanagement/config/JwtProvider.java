package com.example.projectmanagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
     public static String generateToken(Authentication auth) {
         String jwt = Jwts.builder().setIssuedAt(new Date(new Date().getTime()*86400000))
                 .claim("email" , auth.getName())
                 .signWith(key)
                 .compact() ;
         return jwt ;
     }
     public static String generateEmailFromToken(String jwt) {
         Claims claims = Jwts.parserBuilder().setSigningKey(key)
                 .build()
                 .parseClaimsJws(jwt).getBody();  //  _____1_________
         String email = (String) claims.get("email");
         return email;
     }

}
//what is happenening here ______________1_______________
//The `getEmailFromJwtToken` method is used to extract the email claim from a JSON Web Token (JWT). Here's a breakdown of what each part of the code is doing:
//
//1. `jwt.substring(7)`: This line removes the first 7 characters from the JWT string. Typically, a JWT is formatted as "Bearer [token]", so this step removes the "Bearer " part, leaving only the token itself.
//
//2. `Jwts.parserBuilder().setSigningKey(key).build()`: This part creates a JwtParser instance from the JWT library (likely jjwt). The `setSigningKey(key)` method sets the signing key used to verify the JWT's signature. The `build()` method finalizes the construction of the parser.
//
//3. `.parseClaimsJws(jwt)`: This line parses the JWT string using the created parser instance. The `parseClaimsJws` method verifies the JWT's signature and returns a `Jws<Claims>` object containing the JWT's payload claims.
//
//4. `.getBody()`: This retrieves the `Claims` object from the `Jws<Claims>` instance, which contains the JWT's payload claims.
//
//5. `String.valueOf(claims.get("email"))`: This line retrieves the value of the "email" claim from the `Claims` object. The `get("email")` method returns the value of the "email" claim, which is then converted to a `String` using `String.valueOf()`.
//
//6. `return email`: Finally, the method returns the extracted email value as a `String`.
//
//In summary, this method takes a JWT string as input, verifies its signature using the provided signing key, and extracts the "email" claim from the JWT's payload. The extracted email value is then returned as a `String`.
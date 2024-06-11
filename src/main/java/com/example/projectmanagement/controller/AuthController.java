package com.example.projectmanagement.controller;


import com.example.projectmanagement.config.JwtProvider;
import com.example.projectmanagement.modal.User;
import com.example.projectmanagement.repository.UserRepository;
import com.example.projectmanagement.request.LoginRequest;
import com.example.projectmanagement.response.AuthResponse;
import com.example.projectmanagement.service.CustomerUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsImpl customerUserDetailsImpl;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

          User isUserExist = userRepository.findByEmail(user.getEmail());
          if (isUserExist != null) {
              throw new Exception("email already in use");
          }

          User newUser = new User();
          newUser.setEmail(user.getEmail());
          newUser.setFullName(user.getFullName());
          newUser.setPassword(passwordEncoder.encode(user.getPassword()));
          User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setMessage("signUp success");
        res.setJwt(jwt);
          return new ResponseEntity<>(res , HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> validateToken(@RequestBody LoginRequest loginRequest) {
         String username = loginRequest.getEmail() ;
         String password = loginRequest.getPassword();

         Authentication authentication = authenticate(username, password);
         SecurityContextHolder.getContext().setAuthentication(authentication);

         String token = JwtProvider.generateToken(authentication);
         AuthResponse res = new AuthResponse();
         res.setMessage("login success");
         res.setJwt(token);
         return new ResponseEntity<>(res , HttpStatus.OK);
    }

    private Authentication authenticate(String username , String password) {
        UserDetails userDetails = customerUserDetailsImpl.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("invalid username or password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

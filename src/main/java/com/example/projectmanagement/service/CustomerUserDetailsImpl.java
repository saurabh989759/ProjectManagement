package com.example.projectmanagement.service;

import com.example.projectmanagement.modal.User;
import com.example.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities); // _____1_____
    }
}

//When you implement a custom UserDetailsService lik
// e CustomerUserDetailsImpl, you are overriding Spring Security's
// default behavior for loading user details. This means you are
// responsible for providing the user details, including the
// password, roles, and authorities. If Spring Security was
// previously generating a default password for you, it no longer
// does so because you are now defining how users are loaded and
// authenticated.


//_______1_______
//The User class from org.springframework.security.core.userdetails is a concrete
//implementation of the UserDetails interface provided by Spring Security.
//By creating an instance of this User class, you're essentially packaging your application's user information
//into a format that Spring Security understands and can work with.
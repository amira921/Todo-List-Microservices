package com.microservices.security;

import com.microservices.model.User;
import com.microservices.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email){
        try {
            User user = repository.findByEmail(email);
            if (user != null) {
                log.info("user with email: " + email + " is exist");
                return user;
            }
        }catch (UsernameNotFoundException e){
            log.error(e.getMessage());
        }
        return null;
    }
}

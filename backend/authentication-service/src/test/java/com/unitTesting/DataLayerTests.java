package com.unitTesting;

import com.microservices.model.User;
import com.microservices.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataLayerTests {
    static User validUser;

    @Mock
    public UserRepository repository;

    @BeforeAll
    static void setup(){
        validUser = User.builder()
                .username("Amira Taha")
                .email("amirataha78@gmail.com")
                .password("amiraTaha@20")
                .build();
    }

    @Test
    public void testInsertUser(){
        when(repository.save(validUser)).thenReturn(validUser);
        User savedUser = repository.save(validUser);
        assertThat(savedUser).isNotNull();
    }
}


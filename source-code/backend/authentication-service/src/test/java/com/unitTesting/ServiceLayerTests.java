package com.unitTesting;

import com.microservices.dto.RegistrationRequest;
import com.microservices.mapper.UserMapper;
import com.microservices.model.User;
import com.microservices.repository.UserRepository;
import com.microservices.security.JwtUserDetailsService;
import com.microservices.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ServiceLayerTests {
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserRepository repository;

    @Mock
    private JwtUserDetailsService userDetailsService;

    @InjectMocks
    private UserServiceImpl service;

    static RegistrationRequest validUser;

    @BeforeAll
    static void setUp(){
        validUser = RegistrationRequest.builder()
                .username("amira taha")
                .email("amiraTaha798@.com")
                .password("amirataha@2045")
                .build();
    }

    @Test
    void testCreateNewAccount(){
        RegistrationRequest user = validUser;
        user.setPassword(encoder.encode(validUser.getPassword()));
        user.setCreated_at(new Date());

        assertEquals(user, service.createAccount(validUser));
    }

    @Test
    void testSaveNewUser(){
        User user = mapper.mapToEntity(validUser);
        boolean isValid = (user != null) ? true: false;
        assertEquals(isValid , service.saveAccount(validUser));
    }

    @Test
    public void testCheckIfEmailExist_EmailExists() {
        String ExistedEmail = "amirataha798@gmail.com";
        boolean result = service.isEmailExist(ExistedEmail);
        assertTrue(result);
    }

    @Test
    public void testCheckIfEmailExist_EmailDoesNotExist() {
        String NotExistedEmail = "amirataha@gmail.com";
        boolean result = service.isEmailExist(NotExistedEmail);
        System.out.println(result);
        assertFalse(result);
    }
}

package com.movierecommendations.api;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.movierecommendations.api.user.User;
import com.movierecommendations.api.user.UserRepository;
import com.movierecommendations.api.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserUnitTests {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void userHasRightAttributes() {
        Long userId = 1L;
        String testName = "John";
        String testEmail = "userunittests@mail.com";

        User userConstructor = new User(userId, testName, testEmail);
        User userSetter = new User();

        userSetter.setUserId(userId);
        userSetter.setName(testName);
        userSetter.setEmail(testEmail);

        Mockito.when(userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());

        User testUser = userRepository.save(userSetter);

        Assertions.assertEquals(testUser.getUserId(), userConstructor.getUserId());
        Assertions.assertEquals(testUser.getName(), userConstructor.getName());
        Assertions.assertEquals(testUser.getEmail(), userConstructor.getEmail());
    }
}

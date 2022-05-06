package com.deekshith.bookshelf.unit.service;

import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import com.deekshith.bookshelf.model.User;
import com.deekshith.bookshelf.repository.UserRepository;
import com.deekshith.bookshelf.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceImplTest implements UserServiceTest{
    @Mock
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl userService;

    Set<Role> userROle = new HashSet<>(Arrays.asList(new Role(ERole.ROLE_USER)));
    Set<Role> adminRole = new HashSet<>(Arrays.asList(new Role(ERole.ROLE_ADMIN)));
    User userOne = new User("625d8ad6b7f94e4cd5e5b4c4","John Doe", "john@example.com","$2a$10$oyyqrgajJf3EwWw6QOlyJuxuBsSgXA2aVAfn6.c6zbaZDa40wf9x2", userROle);
    User userTwo = new User("625d8a95b7f94e4cd5e5b4c3","Admin User", "admin@example.com","$2a$10$eOASRiZZKBrxRgCCENsBUuFNC/Tgvaw1rrY3GGKUNeokiLMJMkU6W", adminRole);
    User userThree = new User("625d8afcb7f94e4cd5e5b4c5", "Jane Doe", "jane@example.com","$2a$10$UV72M19cTBTMUKB2IYnjn.5mFLM0yk8ZpnGGkXXS64Fc9D5ECNswe", userROle);

    @Test
    public void whenGetAllUsers_shouldReturnAllUsers() {

        List<User> retrievedUsers = new ArrayList<>(List.of(userOne, userTwo, userThree));
        Mockito.when(userRepository.findAll()).thenReturn(retrievedUsers);
        assertEquals(retrievedUsers.get(1).getEmail(), userService.getUsers().get(0).getEmail());
    }

    @Test
    public void whenGetUser_shouldReturnUser() {
        Mockito.when(userRepository.findById("625d8ad6b7f94e4cd5e5b4c4")).thenReturn(Optional.ofNullable(userOne));
        assertEquals(userOne.getEmail(), userService.getUser("625d8ad6b7f94e4cd5e5b4c4").getEmail());
    }

    @Test
    public void whenGetUserByEmail_shouldReturnUser() {
        Mockito.when(userRepository.findByEmail(userOne.getEmail())).thenReturn(Optional.ofNullable(userOne));
        assertEquals(userOne.getEmail(), userService.getUserByEmail(userOne.getEmail()).getEmail());
    }

}

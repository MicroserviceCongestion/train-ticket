package auth.service;

import auth.dto.AuthDto;
import auth.entity.User;
import auth.repository.UserRepository;
import auth.service.impl.UserServiceImpl;
import edu.fudan.common.util.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;
    @Mock
    protected PasswordEncoder passwordEncoder;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        Assertions.assertEquals(null, userServiceImpl.saveUser(user));
    }

    @Test
    public void testGetAllUser() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Assertions.assertEquals(userList, userServiceImpl.getAllUser(headers));
    }

    @Test
    public void testCreateDefaultAuthUser() {
        AuthDto dto = new AuthDto(UUID.randomUUID().toString(), "username", "password");
        User user = new User();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("password");
        Assertions.assertEquals(null, userServiceImpl.createDefaultAuthUser(dto));
    }

    @Test
    public void testDeleteByUserId() {
        UUID userId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(userId.toString());
        Assertions.assertEquals(new Response(1, "DELETE USER SUCCESS", null), userServiceImpl.deleteByUserId(userId.toString(), headers));
    }

}

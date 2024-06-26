package adminuser.service;

import adminuser.dto.UserDto;
import edu.fudan.common.entity.User;
import edu.fudan.common.util.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AdminUserServiceImplTest {

    @InjectMocks
    private AdminUserServiceImpl adminUserServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        Response<List<User>> response = new Response<>(1, null, null);
        ResponseEntity<Response<List<User>>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-user-service:12342/api/v1/userservice/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<List<User>>>() {
                })).thenReturn(re);
        Response result = adminUserServiceImpl.getAllUsers(headers);
        Assertions.assertEquals(new Response<>(1, null, null), result);
    }

    @Test
    public void testDeleteUser() {
        Response response = new Response<>(1, null, null);
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                """
                http://ts-user-service:12342/api/v1/userservice/users\
                /\
                userId\
                """,
                HttpMethod.DELETE,
                requestEntity,
                Response.class)).thenReturn(re);
        Response result = adminUserServiceImpl.deleteUser("userId", headers);
        Assertions.assertEquals(new Response<>(1, null, null), result);
    }

    @Test
    public void testUpdateUser() {
        UserDto userDto = new UserDto();
        HttpEntity requestEntity2 = new HttpEntity(userDto, headers);
        Response response = new Response<>(1, null, null);
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-user-service:12342/api/v1/userservice/users",
                HttpMethod.PUT,
                requestEntity2,
                Response.class)).thenReturn(re);
        Response result = adminUserServiceImpl.updateUser(userDto, headers);
        Assertions.assertEquals(new Response<>(1, null, null), result);
    }

    @Test
    public void testAddUser() {
        UserDto userDto = new UserDto();
        HttpEntity requestEntity2 = new HttpEntity(userDto, headers);
        Response<User> response = new Response<>(1, null, null);
        ResponseEntity<Response<User>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                """
                http://ts-user-service:12342/api/v1/userservice/users\
                /register\
                """,
                HttpMethod.POST,
                requestEntity2,
                new ParameterizedTypeReference<Response<User>>() {
                })).thenReturn(re);
        Response result = adminUserServiceImpl.addUser(userDto, headers);
        Assertions.assertEquals(new Response<>(1, null, null), result);
    }

}

package nus.iss.team1.project1.services;

import nus.iss.team1.project1.dao.UserDao;
import nus.iss.team1.project1.models.User;
import nus.iss.team1.project1.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

/**
 */

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean(name="userDao")
    private UserDao userMockDAO;
    @Test
    public void login() throws Exception{
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("1234567");
        user.setType("1");
        Mockito.when(userMockDAO.validate("testUser","1234567", "1")).thenReturn(user);
        User u = userService.validate("testUser","1234567", "1");
        Assertions.assertEquals("testUser",u.getUsername());
        Assertions.assertEquals("1234567",u.getPassword());
        Assertions.assertEquals("1",u.getType());
    }


    @Test
    public void get() throws Exception{
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("1234567");
        user.setGender("F");
        user.setType("1");
        List<User> users =  new ArrayList<>();
        users.add(user);
        Mockito.when(userMockDAO.get("testUser","1234567", "F","1")).thenReturn(users);
        List<User> u = userService.get("testUser","1234567", "F","1");
        Assertions.assertEquals("testUser",u.get(0).getUsername());
        Assertions.assertEquals("1234567",u.get(0).getPassword());
        Assertions.assertEquals("1",u.get(0).getType());
        Assertions.assertEquals("F",u.get(0).getGender());
    }

    @Test
    public void modifyUser() throws Exception{
        User user = new User();
        user.setUsername("testUser");
        user.setPhone_number("81485733");
        user.setEmail("Tony1@gmail.com");
        user.setGender("M");
        user.setNRIC_FIN("Tony1@gmail.com");
        user.setType("81485733");
        Mockito.doNothing().when(userMockDAO).modify("testUser","M","81485733","Tony1@gmail.com",
                "81485733","1");
        int result = userService.modifyUser("testUser","M","81485733","Tony1@gmail.com",
                "81485733","1");
        Assertions.assertEquals(1,result);
    }

    @Test
    public void modifyPassword() throws Exception{
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("131324");
        Mockito.doNothing().when(userMockDAO).modifyPassword("testUser","131324");
        int result = userService.modifyPassword("testUser","131324");
        Assertions.assertEquals(1,result);
    }
}
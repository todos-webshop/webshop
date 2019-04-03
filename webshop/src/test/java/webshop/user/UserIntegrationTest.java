package webshop.user;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import webshop.CustomResponseStatus;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class UserIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user1 = new User(123, "Test", "Woman", "testwoman", "passTe67st", 1, UserRole.ROLE_ADMIN);
        User user2 = new User(123, "Test", "Man", "testman", "pas34sTest", 1, UserRole.ROLE_ADMIN);
        CustomResponseStatus rs1 = userController.createUser(user1);
        CustomResponseStatus rs2 = userController.createUser(user2);

        List<User> users = userController.listAllUsers();

        TestCase.assertEquals(2, users.size());
        TestCase.assertEquals(UserRole.ROLE_ADMIN, users.get(1).getUserRole());
    }

    @Test
    public void testCreateUserCheckAttributes() {
        User user1 = new User(45, "Angéla", "Tömlőssy", "edvin23", "dI3Sznosajt", 2, UserRole.ROLE_USER);
        User user2 = new User(1000, "John", "Connor", "skynet", "ilL5beback", 6, null);
        User user3 = new User(0, "Elemér", "Mák", "margitbacsi", "máK6néni", 1, UserRole.ROLE_ADMIN);

        CustomResponseStatus responseStatus1 = userController.createUser(user1);
        CustomResponseStatus responseStatus2 = userController.createUser(user2);
        long user3Id = userService.createUserAndReturnUserId(user3);
        List<User> users = userController.listAllUsers();

        TestCase.assertEquals("Success", responseStatus1.getResponse().getDescription());
        TestCase.assertEquals("User skynet successfully created.", responseStatus2.getMessage());
        TestCase.assertEquals(3, users.size());
        TestCase.assertEquals("John", users.get(2).getFirstName());
        TestCase.assertEquals(6, users.get(2).getEnabled());
        TestCase.assertEquals(UserRole.ROLE_USER, users.get(2).getUserRole());

        TestCase.assertEquals("Mák", users.get(1).getLastName());
        TestCase.assertEquals("margitbacsi", users.get(1).getUsername());
        TestCase.assertEquals(UserRole.ROLE_ADMIN, users.get(1).getUserRole());
        TestCase.assertEquals(user3Id, users.get(1).getId());
    }

    @Test
    public void testCreateUserWithExistingUsername() {
        User user1 = new User(45, "Angéla", "Tömlőssy", "edvin23", "dI3Sznosajt", 2, UserRole.ROLE_USER);
        User user2 = new User(1000, "John", "Connor", "edvin23", "ilL5beback", 6, UserRole.ROLE_ADMIN);

        CustomResponseStatus responseStatus1 = userController.createUser(user1);
        CustomResponseStatus responseStatus2 = userController.createUser(user2);
        System.out.println(responseStatus1.getMessage());
        System.out.println(responseStatus2.getMessage());
        List<User> users = userController.listAllUsers();

        TestCase.assertEquals("Success", responseStatus1.getResponse().getDescription());
        TestCase.assertEquals("User edvin23 successfully created.", responseStatus1.getMessage());

        TestCase.assertEquals("Failed", responseStatus2.getResponse().getDescription());
        TestCase.assertEquals("User already exists. New user can not be created for edvin23.", responseStatus2.getMessage());

        TestCase.assertEquals(1, users.size());
    }

    @Test
    public void testCreateUserWithEmptyFields() {
        User user1 = new User(1000, "       ", "Connor", "skynet", "ilL5beback", 6, UserRole.ROLE_ADMIN);
        User user2 = new User(1000, "John", null, "skynet", "ilL5beback", 6, UserRole.ROLE_ADMIN);
        User user3 = new User(1000, "John", "Connor", " \n \t", "ilL5beback", 6, UserRole.ROLE_ADMIN);

        CustomResponseStatus responseStatus1 = userController.createUser(user1);
        CustomResponseStatus responseStatus2 = userController.createUser(user2);
        CustomResponseStatus responseStatus3 = userController.createUser(user3);
        List<User> users = userController.listAllUsers();

        TestCase.assertEquals("Failed", responseStatus1.getResponse().getDescription());
        TestCase.assertEquals("Error! All fields are required.", responseStatus1.getMessage());

        TestCase.assertEquals("Failed", responseStatus2.getResponse().getDescription());
        TestCase.assertEquals("Error! All fields are required.", responseStatus2.getMessage());

        TestCase.assertEquals("Failed", responseStatus3.getResponse().getDescription());
        TestCase.assertEquals("Error! All fields are required.", responseStatus3.getMessage());

        TestCase.assertEquals(0, users.size());
    }


}

package webshop.user;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void testCreateUser() {
        User user = new User(123, "Test", "Woman", "testwoman", "passTest", 1, null );

        assertThat(user.getUsername(), equalTo("testwoman"));
        assertThat(user.getUserRole(), equalTo(UserRole.ROLE_USER));
    }
}

package webshop.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDao {


    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int createUser(User user) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update("insert into users" +
                "(first_name, last_name, username, password, enabled, role) values (:first_name, " +
                ":last_name, :username, :password, :enabled, :role)", Map.of(
                "first_name", user.getFirstName(), "last_name", user.getLastName(), "username",
                user.getUsername(),
                "password", user.getPassword(), "enabled",
                user.getEnabled(), "role", user.getUserRole().name()));
    }


    public List<String> getAllUsernames() {
        return jdbcTemplate.query("select username from users",
                (resultSet, i) -> resultSet.getString("username"));
    }
}
package webshop.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public User getUserByUsername(String username) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject(
                "select first_name, last_name, username, password, enabled, role from users where" +
                        " username = (:username)", Map.of("username", username), USER_ROW_MAPPER);
    }


    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        int enabled = resultSet.getInt("enabled");
        UserRole role = UserRole.valueOf(resultSet.getString("role"));
        return new User(id, firstName, lastName, username, password, enabled, role);
    };

}
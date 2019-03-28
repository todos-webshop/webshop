package webshop.user;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.CustomResponseStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {


    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public long createUserAndReturnUserId(User user) throws DuplicateKeyException {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into users " +
                                                            "(first_name, last_name, username, " +
                                                            "password, enabled, role) values " +
                                                            "(?,?,?,?,?,?)",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, user.getFirstName());
                                    ps.setString(2, user.getLastName());
                                    ps.setString(3, user.getUsername());
                                    ps.setString(4, user.getPassword());
                                    ps.setInt(5, user.getEnabled());
                                    ps.setString(6, user.getUserRole().name());
                                    return ps;
                                }
                            }, keyHolder
        );

        return keyHolder.getKey().longValue();
    }


    public List<String> getAllUsernames() {
        return jdbcTemplate.query("select username from users order by username",
                (resultSet, i) -> resultSet.getString("username"));
    }

    public User getUserByUsername(String username) {
        return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject(
                "select id, first_name, last_name, username, password, enabled, role from users " +
                        "where" +
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


    public List<User> listAllUsers() {
        return jdbcTemplate.query("select id, first_name, last_name,username,password,role,enabled from users order by username",
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("password"), rs.getInt("enabled"), UserRole.valueOf(rs.getString("role"))));

    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

  //  public void modifyUserNoPassword(long id, User user) {
    //    jdbcTemplate.update("update users set  first_name= ?, last_name= ?,username= ?,role= ?  where id = ?",
     //           user.getFirstName(), user.getLastName(), user.getUsername(),  user.getUserRole().toString(), id);
    //}


    public void modifyUser(long id, User user) {
        jdbcTemplate.update("update users set  first_name= ?, last_name= ?,username= ?,password= ?,role= ? where id = ?",
                user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getUserRole().toString(), id);
    }
    public void logicalDeleteUserById(long id) {
        jdbcTemplate.update("update users set first_name = ?,last_name= ?,username = ?,enabled= ? where id = ?", "John","Doe","DELETED_USER" + id,0, id);
    }
    public boolean isAlreadyDeleted(long id){
        List<String> status = jdbcTemplate.query("select username from users where id = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("username");
            }
        }, id);
        if (status.get(0).equals("DELETED_USER"+id)){
            return true;
        }
        return false;
    }
    public int countAllUsers() {
        return jdbcTemplate.queryForObject("select count(id) from users", ((rs, i) -> rs.getInt("count(id)")));
    }
}


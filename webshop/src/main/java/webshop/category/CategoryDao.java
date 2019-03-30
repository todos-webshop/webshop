package webshop.category;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> listAllCategories(){
        return jdbcTemplate.query("select id, name, sequence from categories order by sequence", new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Category(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("sequence")
                );
            }
        });
    }

    public Category getIdOfTheUpdatedName(Category category){
        return jdbcTemplate.queryForObject("select id, name, sequence from categories where name = ?", new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                System.out.println(category.getCategoryName());
                return new Category(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("sequence"));
            }
        }, category.getCategoryName());
    }
}

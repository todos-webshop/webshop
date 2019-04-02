package webshop.category;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.CustomResponseStatus;
import webshop.product.Product;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;

import java.sql.*;
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

    public long addNewCategoryAndGetId(Category category){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into categories (name, sequence) values (?, ?)",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, category.getCategoryName());
                                    ps.setInt(2, category.getSequence());
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public int getNumberOfCategories(){
        return jdbcTemplate.queryForObject("select COUNT(id) from categories", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("COUNT(id)");
            }
        });
    }

    public boolean doesSequenceAlreadyExist(Category category){
        List<Integer> sequence = jdbcTemplate.query("select sequence from categories where sequence = ?",
                new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("sequence");
            }
        }, category.getSequence());
        if (sequence.size() == 0){
            return false;
        }
        return true;
    }

    public int getSequenceById(long categoryId){
        return jdbcTemplate.queryForObject("select sequence from categories where id = ?", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("sequence");
            }
        }, categoryId);
    }


    public void updateSequence(int newSequence, long id){
        jdbcTemplate.update("update categories set sequence = ? where id = ?", newSequence, id);
    }

    public void updateSequenceTwo(int newSequence, Category category){
        jdbcTemplate.update("update categories set sequence = ? where id = ?", newSequence, category.getId());
    }

    public int updateCategoryById(Category category){
        return jdbcTemplate.update("update categories set name = ?, sequence = ? where id = ?", category.getCategoryName(),
                category.getSequence(), category.getId());
    }

    public void deleteCategoryById(long categoryId){
        jdbcTemplate.update("delete from categories where id = ?", categoryId);
    }


    public long getCategoryIdByCategoryName (String categoryName){
        return jdbcTemplate.queryForObject("select id from categories where name = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id");
            }
        }, categoryName);
    }

    public List<Product> listAllProductsByCategoryName(String categoryName){
        return jdbcTemplate.query("select products.id, code, products.name, address, manufacturer, price, " +
                "status from products join categories on products.category_id = categories" +
                ".id where categories.name = ?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(
                        resultSet.getLong("products.id"),
                        resultSet.getString("code"),
                        resultSet.getString("products.name"),
                        resultSet.getString("address"),
                        resultSet.getString("manufacturer"),
                        resultSet.getInt("price"),
                        ProductStatus.valueOf(resultSet.getString("status"))
                );
            }
        }, categoryName);
    }
}

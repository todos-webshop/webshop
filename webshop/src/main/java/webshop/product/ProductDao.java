package webshop.product;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao { 
    
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Product> findAll(){
        return jdbcTemplate.query("select code,name,address,manufacturer,price from products", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("manufacturer"),
                        resultSet.getInt("price"));
            }
        });
    }
    public Product findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select code,name,address,manufacturer,price from products where address = ?",
                (rs, rowNum) -> new Product(rs.getString("code"), rs.getString("name"),rs.getString("address"),rs.getString("manufacturer"),rs.getInt("price")),
                address);
    }
}

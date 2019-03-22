package webshop.product;


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
import java.text.Normalizer;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> listAllProducts() {
        return jdbcTemplate.query("select id, code, name, address, manufacturer, price, status from products order by name, manufacturer", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getString("manufacturer"),
                        resultSet.getInt("price"),
                        ProductStatus.valueOf(resultSet.getString("status")));
            }
        });
    }

    public Product findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select id,code,name,manufacturer,price, status from products where address = ?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getLong("id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getString("manufacturer"),
                        resultSet.getInt("price"),
                        ProductStatus.valueOf(resultSet.getString("status")));
            }
        },address);
    }

    public long addNewProductAndGetId(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into products (code, name, address, manufacturer, price) values (?, ?, ?, ?, ?)",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, product.getCode());
                                    ps.setString(2, product.getName());
                                    ps.setString(3, product.getAddress());
                                    ps.setString(4, product.getManufacturer());
                                    ps.setInt(5, product.getPrice());
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public boolean isCodeUnique(String code) {
        List<String> products = jdbcTemplate.query("select code from products where code = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("code");
            }
        }, code);
        if (products.size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isNameUnique(String name) {
        List<String> products = jdbcTemplate.query("select name from products where name = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("name");
            }
        }, name);
        if (products.size() == 0) {
            return true;
        }
        return false;
    }

    public int updateProduct(Product product, long id) {
        return jdbcTemplate.update("update products set code = ?, name = ?, manufacturer = ?, price = ?, status = ? where id = ?",
                product.getCode(), product.getName(), product.getManufacturer(), product.getPrice(),product.getProductStatus().name(), id);
    }

    public boolean isAddressEdited(String address, long id){
        List<String> addressFromDB = jdbcTemplate.query("select address from products where id =?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("address");
            }
        },id);
        if (address.equals(addressFromDB.get(0))){
            return false;
        }
        return true;
    }

    public boolean isIdTheSameForUpdatingTheSameCode(String code, long id) {
        List<Long> ids = jdbcTemplate.query("select id from products where code = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id");
            }
        }, code);
        if (isCodeUnique(code) || !isCodeUnique(code) && (ids.get(0) == id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isIdTheSameForUpdatingTheSameName(String name, long id) {
        List<Long> ids = jdbcTemplate.query("select id from products where name = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id");
            }
        }, name);
        if (isNameUnique(name) || !isNameUnique(name) && (ids.get(0) == id)) {
            return true;
        } else {
            return false;
        }
    }

    public void logicalDeleteProductById(long id) {
        jdbcTemplate.update("update products set status = ? where id = ?", "DELETED", id);
    }

    public boolean isAlreadyDeleted(long id){
        List<String> status = jdbcTemplate.query("select status from products where id = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("status");
            }
        }, id);
        if (status.get(0).equals("DELETED")){
            return true;
        }
        return false;
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from products");
    }


    public long getProductIdByProductCode(String productCode) {
        Long productId =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).queryForObject(
                        "SELECT id FROM products WHERE code = " +
                                "(:code)", Map.of("code",
                                productCode),
                        (rs, i) -> rs.getLong("id"));
        if (productId == null) {
            throw new IllegalStateException("Product id does not exist for product code: " + productCode);
        }
        return productId;
    }
}

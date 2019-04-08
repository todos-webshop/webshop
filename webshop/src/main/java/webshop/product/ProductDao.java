package webshop.product;


import webshop.category.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.order.Order;
import webshop.order.OrderStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    public static final String STATUS = "status";
    public static final String PRICE = "price";
    public static final String ADDRESS = "address";
    public static final String MANUFACTURER = "manufacturer";

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
                        resultSet.getString(ADDRESS),
                        resultSet.getString(MANUFACTURER),
                        resultSet.getInt(PRICE),
                        ProductStatus.valueOf(resultSet.getString(STATUS)));
                }
        });
    }

    public Object findProductByAddressTwo(String address) {
        return jdbcTemplate.queryForObject("select id,code,name,manufacturer,price, status from products where address = ?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getLong("id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getString(MANUFACTURER),
                        resultSet.getInt(PRICE),
                        ProductStatus.valueOf(resultSet.getString(STATUS)));
            }
        },address);
    }
    public long addNewProductAndGetId(Category category, long categoryId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into products (code, name, address, " +
                                                            "manufacturer, price, category_id) values (?, ?, ?, ?, ?, ?)",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, category.getProducts().get(0).getCode());
                                    ps.setString(2, category.getProducts().get(0).getName());
                                    ps.setString(3, category.getProducts().get(0).getAddress());
                                    ps.setString(4, category.getProducts().get(0).getManufacturer());
                                    ps.setInt(5, category.getProducts().get(0).getPrice());
                                    ps.setLong(6, categoryId);
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
        return products.isEmpty();
    }

    public boolean isNameUnique(String name) {
        List<String> products = jdbcTemplate.query("select name from products where name = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("name");
            }
        }, name);
        return products.isEmpty();
    }

    public int updateProduct(long id, Category category, long categoryId) {
        return jdbcTemplate.update("update products set code = ?, name = ?, address = ?,manufacturer = ?, price = ?, status = ?" +
                        ", category_id = ? where id = ?",
                category.getProducts().get(0).getCode(), category.getProducts().get(0).getName(), category.getProducts().get(0).getAddress(), category.getProducts().get(0).getManufacturer(),
                category.getProducts().get(0).getPrice(),
                category.getProducts().get(0).getProductStatus().name(), categoryId, id);

    }

    public boolean isAddressEdited(String address, long id){
        List<String> addressFromDB = jdbcTemplate.query("select address from products where id =?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(ADDRESS);
            }
        },id);
        return !address.equals(addressFromDB.get(0));
    }

    public boolean isIdTheSameForUpdatingTheSameCode(String code, long id) {
        List<Long> ids = jdbcTemplate.query("select id from products where code = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id");
            }
        }, code);
        return isCodeUnique(code) || !isCodeUnique(code) && (ids.get(0) == id);
    }

    public boolean isIdTheSameForUpdatingTheSameName(String name, long id) {
        List<Long> ids = jdbcTemplate.query("select id from products where name = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id");
            }
        }, name);
        return isNameUnique(name) || !isNameUnique(name) && (ids.get(0) == id);
    }

    public void logicalDeleteProductById(long id) {
        jdbcTemplate.update("update products set status = ? where id = ?", "DELETED", id);
    }

    public boolean isAlreadyDeleted(long id){
        List<String> status = jdbcTemplate.query("select status from products where id = ?", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(STATUS);
            }
        }, id);
        return status.get(0).equals("DELETED");
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

    public int countActiveProducts() {
        return jdbcTemplate.queryForObject("Select count(id) from products where status = 'ACTIVE'", (rs, i) -> rs.getInt("count(id)"));
    }

    public int countAllProducts() {
        return jdbcTemplate.queryForObject("Select count(id) from products", (rs, i) -> rs.getInt("count(id)"));
    }

    public List<Product> listAllProductsByCategory(Category category){
        return jdbcTemplate.query("select products.id, code, products.name, address, manufacturer, price, " +
                "status, categories.name from products join categories on products.category_id = categories" +
                ".id where categories.id = ? order by products.name", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(
                        resultSet.getLong("products.id"),
                        resultSet.getString("code"),
                        resultSet.getString("products.name"),
                        resultSet.getString(ADDRESS),
                        resultSet.getString(MANUFACTURER),
                        resultSet.getInt(PRICE),
                        ProductStatus.valueOf(resultSet.getString(STATUS))
                );
            }
        }, category.getId());
    }



    //query returns only one product in the list so here it is okay to use this
    public Category findProductByAddressWithCategory(String address) {
        return jdbcTemplate.queryForObject("select categories.id, categories.name, sequence, products.id, code, products.name," +
                "manufacturer,price, status from products join categories on categories.id = category_id where address = ?",
                new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Category(
                        resultSet.getLong("categories.id"),
                        resultSet.getString("categories.name"),
                        resultSet.getInt("sequence"),
                        List.of(new Product(resultSet.getLong("products.id"),
                        resultSet.getString("code"),
                        resultSet.getString("products.name"),
                        resultSet.getString(MANUFACTURER),
                        resultSet.getInt(PRICE),
                        ProductStatus.valueOf(resultSet.getString(STATUS))))
                );
            }
        },address);
    }


    public Product getProductByProductId(long productId) {
        return jdbcTemplate.queryForObject("select id, code, name, manufacturer, price,status from products where id=?", (rs, i) -> new Product(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),ProductStatus.valueOf(rs.getString(6))),productId);

    }

    public int updateProductCategoryIfCategoryIsDeleted(long deletedCategoryId){
        return jdbcTemplate.update("update products set category_id = 1 where category_id = ?", deletedCategoryId);
    }


/*    public Order getIdOfLatestOrder(){
        return jdbcTemplate.queryForObject("select id, user_id, max(order_time), status, shipping_address from orders",
                new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Order(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        null,
                        OrderStatus.valueOf(resultSet.getString("status")),
                        0L,
                        resultSet.getString("shipping_address"));
            }
        });
    }*/

public List<Product> lastThreeProducts() {
        return jdbcTemplate.query("select distinct orders.order_time, products.id, products.code, products.name, products.address, " +
                        "products.manufacturer, products.price, products.status from products join " +
                        "ordered_items on ordered_items.product_id=products.id join orders on " +
                        "orders.id=ordered_items.order_id where products.status = 'ACTIVE' order by orders.order_time desc " +
                        "limit 3",
    new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString(ADDRESS),
                    resultSet.getString(MANUFACTURER),
                    resultSet.getInt(PRICE),
                    ProductStatus.valueOf(resultSet.getString(STATUS)));
        }
    });
}

}

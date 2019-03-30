package webshop.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import webshop.product.Product;
import webshop.product.ProductDao;
import webshop.product.ProductStatus;
import webshop.statics.StatByProduct;
import webshop.user.User;
import webshop.user.UserDao;
import webshop.user.UserRole;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
@Repository
public class RateDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductDao productDao ;
    @Autowired
    private UserDao userDao;

    public RateDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long addNewRateAndGetId(Rate rate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Date date = Date.valueOf(rate.getDate());
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection)
                                        throws SQLException {
                                    PreparedStatement ps =
                                            connection.prepareStatement("insert into ratings ( user_id, product_id, stars, message, rating_time) values (?, ?, ?, ?, ?)",
                                                    Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, rate.getUser().getId());
                                    ps.setLong(2, rate.getProduct().getId());
                                    ps.setInt(3, rate.getStars());
                                    ps.setString(4, rate.getMessage());
                                    ps.setDate(5, date);
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public List<Rate> getRatesForProduct(Product product) {
        Product productInstance = new Product(product.getId(),"MUZ","muz","muz",0, ProductStatus.ACTIVE);
        User user = new User(15,"John","Doe","john","123456",1, UserRole.ROLE_USER );
        return jdbcTemplate.query("Select ratings.id, ratings.message, ratings.stars,ratings.rating_time , ratings.user_id  from ratings join products on ratings.product_id=products.id where products.id = ? order by ratings.rating_time",
                (rs,rowNum)-> new Rate( rs.getLong(1),rs.getString(2),rs.getInt(3), rs.getDate(4).toLocalDate(),userDao.getUserByUserId(rs.getLong(5)),productDao.getProductByProductId(product.getId())),product.getId());

    }
    public double getAvgRatesForProduct(Product product) {
        return jdbcTemplate.queryForObject("Select avg(ratings.stars) from ratings join products on ratings.product_id=products.id where products.id =? order by ratings.rating_time",
                (rs, i) -> rs.getDouble(1),product.getId());
    }

    public List<Rate> getRateForUserAndProduct(Rate rate){
        return jdbcTemplate.query("Select ratings.id, ratings.message, ratings.stars,ratings.rating_time, ratings.product_id from ratings join products on ratings.product_id=products.id where  ratings.product_id =? and ratings.user_id =? order by ratings.rating_time",
                (rs,rowNum)-> new Rate( rs.getLong(1),
                        rs.getString(2),rs.getInt(3),
                        rs.getDate(4).toLocalDate(),rate.getUser(),rate.getProduct())
                ,
                rate.getProduct().getId(),
                rate.getUser().getId());

    }

    public int updateRate(Rate rate, long rateId) {
        Date date = Date.valueOf(rate.getDate());
        return jdbcTemplate.update("update ratings set stars = ?, message = ?, rating_time = ? where id = ?",
                rate.getStars(), rate.getMessage(), date, rateId);
    }
}

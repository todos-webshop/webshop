package webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@SpringBootApplication
@EnableWebSecurity
@Configuration
public class WebshopApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/**", "/", "/js/**", "/css/**", "/api/**", "/register.html",
                        "/users",
                        "/username").permitAll()
//                .antMatchers("/basket.html").hasRole("USER")
//                .antMatchers("/basket.html").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource, PasswordEncoder passwordEncoder) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enabled " +
                        "from users " +
                        "where " +
                        "username=?")
                .authoritiesByUsernameQuery("select username, role from users where " +
                        "username = ?");

    }

}
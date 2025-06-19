package me.natsumeraku.moviewebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, RedisAutoConfiguration.class, SecurityAutoConfiguration.class, ValidationAutoConfiguration.class})
@ComponentScan(basePackages = "me.natsumeraku.moviewebsite")
public class MovieWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MovieWebsiteApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }

}

package me.natsumeraku.moviewebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;

@SpringBootApplication
public class MovieWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MovieWebsiteApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }

}

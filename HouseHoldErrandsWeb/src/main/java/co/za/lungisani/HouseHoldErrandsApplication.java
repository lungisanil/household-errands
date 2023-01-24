package co.za.lungisani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class HouseHoldErrandsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HouseHoldErrandsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HouseHoldErrandsApplication.class);
    }

}

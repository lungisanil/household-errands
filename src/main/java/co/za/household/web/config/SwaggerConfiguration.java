package co.za.household.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Value("${swagger.application.version}")
    private String applicationVersion;

    @Value("${swagger.application.description}")
    private String applicationDescription;

    @Bean
    public OpenAPI houseHoldAPI() {
        return new OpenAPI()
                .info(new Info().title("HouseHold Service API")
                        .description(applicationDescription)
                        .version(applicationVersion))
                ;
    }

}

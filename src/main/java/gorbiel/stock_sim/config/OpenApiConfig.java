package gorbiel.stock_sim.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI stockSimOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stock Simulation API")
                        .description("API for stock bank, wallets, and audit log")
                        .version("1.0"));
    }
}

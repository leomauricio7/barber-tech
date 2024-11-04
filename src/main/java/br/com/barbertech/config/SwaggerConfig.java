package br.com.barbertech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API BarberTech")
                        .version("1.0")
                        .description("API de integração com o sistema BarberTEch")
                        .termsOfService("http://barbertech.com/terms/")
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Licença da API")
                                .url("http://barbertech.com/license")));
    }
}

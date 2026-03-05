package com.vitorbnr.wallet.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Carteira Digital (Wallet)")
                        .version("1.0.0")
                        .description("API REST para transferência de valores entre usuários comuns e lojistas.")
                        .contact(new Contact()
                                .name("Vitor Bernardo Batista")
                                .email("vitorbernadotech@gmail.com")
                                .url("https://github.com/vitorbnr")));
    }
}
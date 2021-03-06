package com.teamnexters.lazy.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "밍굴맹굴 API 명세서",
        description = "당신은 얼마나 밍굴맹굴합니까?",
        version = "v1.0.0",
        contact = @Contact(name = "NEXTERS 19th 배짱이두둑",
            url = "https://teamnexters.com/"),
        license = @License(name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html")
    )
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@Configuration
public class SwaggerConfig {
}
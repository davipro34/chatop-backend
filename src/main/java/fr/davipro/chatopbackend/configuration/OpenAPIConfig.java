package fr.davipro.chatopbackend.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("david@davipro.fr");
        contact.setName("David");
        contact.setUrl("https://github.com/davipro34");
        
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        
        Info info = new Info()
        .title("ChaTop Management API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage ChaTop API.").termsOfService("https://github.com/davipro34/chatop-backend")
        .license(mitLicense);

        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));

        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        securityRequirements.add(new SecurityRequirement().addList("bearerAuth"));

        return new OpenAPI().info(info).components(new Components().addSecuritySchemes("bearerAuth", securitySchemes.get(0))).security(securityRequirements);
    }

}

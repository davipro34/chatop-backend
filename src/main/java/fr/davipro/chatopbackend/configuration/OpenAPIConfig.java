package fr.davipro.chatopbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

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

        return new OpenAPI().info(info);
    }

}

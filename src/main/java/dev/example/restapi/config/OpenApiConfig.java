package dev.example.restapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Leonardo San Blas",
                        email = "leonardosanblas@gmail.com",
                        url = "https://europa.eu/europass/eportfolio/screen/redirect-external?url=http://www.linkedin.com/in/leonardo-san-blas-sanchez-213405286"
                ),
                description = "OpenApi documentation for User REST APIs",
                title = "OpenApi specification",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}

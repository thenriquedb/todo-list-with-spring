package br.com.thenriquedb.todolist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        title = "Todo List API",
        description = "Simple Todo List API",
        version = "1.0.0",
        contact = @Contact(
                name = "Thiago Henrique Domingues",
                url = "https://www.linkedin.com/in/thenriquedomingues/"
        )
    ),
    servers = {
        @Server(
                description = "Local ENV",
                url = "http://localhost:8080"
        ),
        @Server(
                description = "Prod ENV",
                url = "https://todolist-api-icfi.onrender.com/"
        ),
    }
)
@SecurityScheme(
        name = "basic-auth",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}

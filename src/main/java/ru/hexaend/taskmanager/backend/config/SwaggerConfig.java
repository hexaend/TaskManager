package ru.hexaend.taskmanager.backend.config;//package ru.hexaend.taskmanager.backend.config;
//
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.OAuthFlow;
//import io.swagger.v3.oas.annotations.security.OAuthFlows;
//import io.swagger.v3.oas.annotations.security.OAuthScope;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@SecurityScheme(
//        name = "Oauth2",
//        type = SecuritySchemeType.OAUTH2,
//        flows = @OAuthFlows(
//                authorizationCode = @OAuthFlow(
//                        authorizationUrl = "http://localhost:8080/realms/TaskManagerRealm/protocol/openid-connect/auth",
//                        tokenUrl = "http://localhost:8080/realms/TaskManagerRealm/protocol/openid-connect/token",
//                        refreshUrl = "http://localhost:8080/realms/TaskManagerRealm/protocol/openid-connect/token",
//                        scopes = {
//                                @OAuthScope(name = "openid", description = "OpenID Connect scope"),
//                        }
//                )
//        )
//)
//public class SwaggerConfig {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components())
//                .addSecurityItem(new SecurityRequirement().addList("Oauth2"));
//    }
//}
//

// TODO: РАЗОБРАТЬСЯ ТУТ

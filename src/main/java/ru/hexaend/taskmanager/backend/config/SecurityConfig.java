package ru.hexaend.taskmanager.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.hexaend.taskmanager.backend.security.UserLazyRegistrationFilter;
import ru.hexaend.taskmanager.backend.service.UserService;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${keycloak.server-url}")
    private String keycloakAuthServerUrl;
    @Value("${keycloak.realm}")
    private String keycloakRealm;
    @Value("${keycloak.client-id}")
    private String keycloakClientId;
    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;
    @Value("${keycloak.username}")
    private String keycloakUsername;
    @Value("${keycloak.password}")
    private String keycloakPassword;


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/projects/**", "/api/users/**").hasRole("USER")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/test").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        configuration -> configuration
                                .jwt(jwt -> {
                                    JwtAuthenticationConverter jwtConfigurer = new JwtAuthenticationConverter();
                                    jwtConfigurer.setPrincipalClaimName("preferred_username");
                                    jwt.jwtAuthenticationConverter(jwtConfigurer);

                                    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

                                    JwtGrantedAuthoritiesConverter customJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                                    customJwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("spring_sec_roles");
                                    customJwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

                                    jwtConfigurer.setJwtGrantedAuthoritiesConverter(token ->
                                            Stream.concat(jwtGrantedAuthoritiesConverter.convert(token).stream(),
                                                            customJwtGrantedAuthoritiesConverter.convert(token).stream())
                                                    .toList());

                                })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new UserLazyRegistrationFilter(userService), BearerTokenAuthenticationFilter.class);



        return http.build();
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAuthServerUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(keycloakRealm)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .username(keycloakUsername)
                .password(keycloakPassword)
                .build();
    }

}

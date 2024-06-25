package com.appsdeveloperblog.ws.api.ResourceServer.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurity {

    private final KeycloakRoleConverter keycloakRoleConverter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);

        http.authorizeHttpRequests((auth) ->
                        auth.requestMatchers(HttpMethod.GET, "/users/status/check").hasAuthority("SCOPE_profile")
                                .requestMatchers(HttpMethod.GET, "/users/developers").hasRole("developer")
                                .requestMatchers(HttpMethod.GET, "/users/cats").hasAuthority("SCOPE_cats")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.build();
    }

}

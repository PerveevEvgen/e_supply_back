package ua.kpi.diploma.e_supply.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/auth").permitAll()
//                                .requestMatchers("/api/all").hasRole("admin")
                                .anyRequest().authenticated()
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).oauth2ResourceServer(
                        resourceServer -> resourceServer.jwt(
                                jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(
                                        keycloakAuthConverter()
                                )
                        )
                )
                .build();
    }

    private Converter<Jwt,? extends AbstractAuthenticationToken> keycloakAuthConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(
                new AuthoritiesConverter()
        );
        return converter;
    }

}
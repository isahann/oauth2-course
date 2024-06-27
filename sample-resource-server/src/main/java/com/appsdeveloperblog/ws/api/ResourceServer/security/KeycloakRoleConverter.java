package com.appsdeveloperblog.ws.api.ResourceServer.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

@Slf4j
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        final String realmAccessKey = "realm_access";

        if (!source.getClaims().containsKey(realmAccessKey))
            return Collections.emptyList();

        final Map<String, Object> realmAcess = (Map<String, Object>) source.getClaims().get(realmAccessKey);
        final List<String> roles = (List<String>) realmAcess.get("roles");
        final String whitespacedScopes = (String) source.getClaims().get("scope");
        final List<String> scopes = List.of(whitespacedScopes.split(" "));
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.addAll(
                scopes.stream()
                        .map(scope -> String.format("SCOPE_%s", scope))
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );

        grantedAuthorities.addAll(
                roles.stream()
                        .map(role -> String.format("ROLE_%s", role))
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );

        log.info(grantedAuthorities.toString());

        return grantedAuthorities;
    }
}

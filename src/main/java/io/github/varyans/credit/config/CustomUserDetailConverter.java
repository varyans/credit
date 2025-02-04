package io.github.varyans.credit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final ObjectMapper objectMapper;
    @SneakyThrows
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
//        System.out.println(objectMapper
//                .writeValueAsString(source));

        Map<String, Object> claims = source.getClaims();

        String userId = (String) claims.get("sid");
        String username = (String) claims.get("preferred_username");
        Collection<GrantedAuthority> authorities = extractAuthoritiesFromClaims(claims);

        UserPrincipal userPrincipal = new UserPrincipal(userId, username, authorities);

        return new UsernamePasswordAuthenticationToken(userPrincipal, "n/a", authorities);
    }

    private Collection<GrantedAuthority> extractAuthoritiesFromClaims(Map<String, Object> claims) {
        return Optional.ofNullable(claims.get("resource_access"))
                .map(Map.class::cast)
                .map(m ->(Map<String, Object>) m.get("credit"))
                .map(c ->(List<String>) c.get("roles"))
                .stream()
                .flatMap(List::stream)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }
}

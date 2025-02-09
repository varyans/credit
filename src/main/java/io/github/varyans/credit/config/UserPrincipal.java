package io.github.varyans.credit.config;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@EqualsAndHashCode
public class UserPrincipal implements UserDetails {
    @Getter
    private final String userId;
    private final String userName;
    private final String email;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final Integer creditLimit;
    @Getter
    private final Boolean isAdmin;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String userId, String username,Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = username;
        this.isAdmin = authorities.stream().map(GrantedAuthority::getAuthority).anyMatch("ADMIN"::equals);
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.creditLimit = 0;
        this.authorities = authorities;
    }

    public UserPrincipal(String userId, String username, String email, String firstName, String lastName, Integer creditLimit, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditLimit = creditLimit;
        this.isAdmin = authorities.stream().map(GrantedAuthority::getAuthority).anyMatch("ADMIN"::equals);
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userName;
    }
}

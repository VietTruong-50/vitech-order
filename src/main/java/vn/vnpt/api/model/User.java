package vn.vnpt.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.vnpt.api.repository.helper.Col;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Col("id")
    private String id;
    @Col("username")
    private String username;
    @Col("first_name")
    private String firstName;
    @Col("last_name")
    private String lastName;
    @Col("email")
    private String email;
    @Col("password")
    private String password;
    @Col("avatar")
    private String avatar;
    @Col("full_name")
    private String fullName;
    @Col("phone")
    private String phone;
    @Col("gender")
    private String gender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
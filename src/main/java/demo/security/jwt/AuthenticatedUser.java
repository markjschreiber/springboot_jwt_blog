package demo.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Very basic implementation of {@code Authentication} interface to store the username. A real world example could also populate
 * the credentials and some other fields using information derived from the JWT.
 * @author schremar
 *
 */
public class AuthenticatedUser implements Authentication {

    private String name;
    private boolean authenticated = true;

    AuthenticatedUser(String name){
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

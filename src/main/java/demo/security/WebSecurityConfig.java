package demo.security;

import demo.security.jwt.JWTAuthenticationFilter;
import demo.security.jwt.JWTLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures security for various URLs and filters to create and inspect JWTs in headers.
 * @author schremar
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching
        http.headers().cacheControl();

        http.csrf().disable() // disable csrf for our requests.
        .authorizeRequests()
        .antMatchers("/").permitAll() //permit access to the root
        .antMatchers(HttpMethod.POST,"/login").permitAll() //permit POST to /login , without it you can't login
        .anyRequest().authenticated()
        .and()
        // We filter the api /login requests
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
        // And filter other requests (such as /users to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Create a default admin user. A real world app would use another source of users, such as LDAP or a datastore.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // Create a default account
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("ADMIN");
    }

}

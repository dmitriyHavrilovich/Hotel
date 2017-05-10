package ua.iasa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

    private final JdbcUserDetailsManager manager;


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(manager);
    }

}

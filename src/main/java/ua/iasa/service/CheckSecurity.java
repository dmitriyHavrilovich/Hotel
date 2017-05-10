package ua.iasa.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("duplicates")
public class CheckSecurity {

    @Secured("admin")
    public void checkAdmin() {
    }

    @Secured("user")
    public void checkUser() {
    }

}
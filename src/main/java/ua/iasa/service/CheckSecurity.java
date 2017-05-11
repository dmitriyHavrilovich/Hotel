package ua.iasa.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
public class CheckSecurity {

    @Secured("admin")
    public void checkAdmin() throws AccessDeniedException{
    }

    @Secured("user")
    public void checkUser() throws AccessDeniedException{
    }

}
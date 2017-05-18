package ua.iasa.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
public class CheckSecurity {

    @Secured({"ROLE_ADMIN"})
    public void checkAdmin() throws AccessDeniedException{
    }

}
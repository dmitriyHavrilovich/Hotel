package ua.iasa.service;

import javafx.scene.control.Alert;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityPopUpSender {

    @ExceptionHandler(value = AccessDeniedException.class)
    public void handleAccessDenied(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Access Denied!");
        alert.show();
    }
}

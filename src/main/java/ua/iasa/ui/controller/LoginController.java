package ua.iasa.ui.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import ua.iasa.config.View;

import javax.annotation.PostConstruct;

@NoArgsConstructor
public class LoginController {

    @FXML
    public Button loginBtn;
    @FXML
    private javafx.scene.control.TextField loginField;
    @FXML
    private PasswordField password;

    @Qualifier("mainView")
    @Autowired
    private View view;

    @Autowired
    private UserDetailsManager manager;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     * <p>
     * Обрати внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     * <p>
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct.
     * Который вызовется спрингом, после того,
     * как им будут произведены все оставшиеся инъекции.
     * {@link LoginController#init()}
     */
    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {

    }

    @FXML
    public void login(ActionEvent event) {
        UserDetails userDetails = manager.loadUserByUsername(loginField.getText());
        Authentication auth = new UsernamePasswordAuthenticationToken(loginField.getText(), password.getText()
                , userDetails.getAuthorities());
        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            publisher.publishEvent(new InteractiveAuthenticationSuccessEvent(auth, this.getClass()));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(new Scene(view.getView()));
            stage.setResizable(true);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong login or password");
            alert.show();
        }
    }
}

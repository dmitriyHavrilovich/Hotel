package ua.iasa.ui;


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
import ua.iasa.config.View;
import ua.iasa.entity.User;
import ua.iasa.repository.UserRepository;

import javax.annotation.PostConstruct;

@NoArgsConstructor
public class LoginController {

    @FXML
    public Button loginBtn;
    @Autowired
    private UserRepository userRepository;
    @FXML
    private javafx.scene.control.TextField loginField;
    @FXML
    private PasswordField password;
    @Qualifier("mainView")
    @Autowired
    private View view;


    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     *
     * Обрати внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     *
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
    public void init(){

    }
    @FXML
    public void login(ActionEvent event) {
        User user = userRepository.findByUsernameAndPassword(loginField.getText(),password.getText());
        if (user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Wrong login or password");
            alert.show();
        }
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.show();
    }
}

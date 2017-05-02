package ua.iasa.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iasa.entity.User;
import ua.iasa.repository.UserRepository;

import javax.annotation.PostConstruct;

@NoArgsConstructor
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @FXML
    private javafx.scene.control.TextField loginField;
    @FXML
    private PasswordField password;

    /**
     * Инициализация контроллера от JavaFX.
     * Метод вызывается после того как FXML загрузчик произвел инъекции полей.
     *
     * Обратите внимание, что имя метода <b>обязательно</b> должно быть "initialize",
     * в противном случае, метод не вызовется.
     *
     * Также на этом этапе еще отсутствуют бины спринга
     * и для инициализации лучше использовать метод,
     * описанный аннотацией @PostConstruct.
     * Который вызовется спрингом, после того,
     * как им будут произведены все оставшиеся инъекции.
     * {@link MainController#init()}
     */
    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init(){

    }
    @FXML
    public void login(){
        User user = userRepository.findByUsernameAndPassword(loginField.getText(),password.getText());
        if (user != null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Wrong login or password");
            alert.show();
        }
    }
}

package ua.iasa.config;

import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.iasa.ui.LoginController;
import ua.iasa.ui.MainMenuController;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FxmlControllersConfig {

    @Bean(name = "loginView")
    public View getLoginView() throws IOException {
        return loadView("fxml/login.fxml");
    }

    /**
     * Именно благодаря этому методу мы добавили контроллер в контекст спринга,
     * и заставили его сделать произвести все необходимые инъекции.
     */
    @Bean
    public LoginController getLoginController() throws IOException {
        return (LoginController) getLoginView().getController();
    }

    @Bean(name = "mainView")
    public View getMainView() throws IOException {
        return loadView("fxml/main.fxml");
    }

    @Bean
    public MainMenuController getMainController() throws IOException {
        return (MainMenuController) getMainView().getController();
    }

    /**
     * Самый обыкновенный способ использовать FXML загрузчик.
     * Как раз-таки на этом этапе будет создан объект-контроллер,
     * произведены все FXML инъекции и вызван метод инициализации контроллера.
     */
    private View loadView(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new View(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }
}

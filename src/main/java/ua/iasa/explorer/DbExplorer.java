package ua.iasa.explorer;

import javafx.scene.Scene;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ua.iasa.config.View;

@SpringBootApplication
@Lazy
@EnableJpaRepositories(basePackages = "ua.iasa.repository")
@ComponentScan(basePackages = "ua.iasa")
@EntityScan(basePackages = "ua.iasa.entity")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class DbExplorer extends AbstractJavaFxApplicationSupport {

    @Value("${ui.title:Welcome to the Hotel California!}")//
    private String windowTitle;

    @Qualifier("loginView")
    @Autowired
    private View view;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        //ImagePattern pattern = new ImagePattern(myImage);
        //stage.setFill(pattern);
        stage.show();
    }


    public static void main(String[] args) {
        launchApp(DbExplorer.class, args);
    }

}

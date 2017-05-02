package ua.iasa.config;

import javafx.scene.Parent;
import lombok.Data;

/**
 * Класс - оболочка: контроллер мы обязаны указать в качестве бина,
 * а view - представление, нам предстоит использовать в точке входа {@link ua.iasa.explorer.DbExplorer}.
 */
@Data
public class View {
    private Parent view;
    private Object controller;

    public View(Parent view, Object controller) {
        this.view = view;
        this.controller = controller;
    }


}

package ua.iasa.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.MovementDocument;
import ua.iasa.entity.NaturalPerson;
import ua.iasa.repository.NaturalPersonRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
public class MainMenuController {
    private ObservableList<NaturalPerson> naturalPersonData, juridicalPersonData;

    @FXML
    public Button addPhysicalButton;
    @Autowired
    private NaturalPersonRepository natpersrepo;
    @FXML
    private javafx.scene.control.TextField physicalSurnameTextField;
    @FXML
    private javafx.scene.control.TextField physicalNameTextField;
    @FXML
    private javafx.scene.control.TextField physicalFathersNameTextField;
    @FXML
    private TableView<NaturalPerson> physicalTable;

    @FXML
    private TableColumn<NaturalPerson, String> physicalSurnameColumn;
    @FXML
    private TableColumn<NaturalPerson, String> physicalNameColumn;
    @FXML
    private TableColumn<NaturalPerson, String> physicalFathersNameColumn;
    @Qualifier("mainView")
    @Autowired
    private View view;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {

    }

    @FXML
    public void clicked_addPhysicalButton(ActionEvent actionEvent) {
        Set<MovementDocument> movementDocumentSet = new HashSet<>();
        NaturalPerson pers = new NaturalPerson(null, null, movementDocumentSet
                , physicalSurnameTextField.getText(),
                physicalNameTextField.getText(),
                physicalFathersNameTextField.getText(), null);
        NaturalPerson p = natpersrepo.save(pers);
        naturalPersonData.add(p);

    }

    @FXML
    public void clicked_refreshPhysicalTable(ActionEvent actionEvent) {
        List<NaturalPerson> natpersons = (List) natpersrepo.findAll();
        naturalPersonData = FXCollections.observableArrayList(natpersons);
        // Столбцы таблицы
        //TableColumn<NaturalPerson, String> nameColumn = new TableColumn<>("Прізвище");
        physicalSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        //TableColumn<NaturalPerson, String> phoneColumn = new TableColumn<>("Ім я");
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //TableColumn<NaturalPerson, String> emailColumn = new TableColumn<>("По-батькові");
        physicalFathersNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        physicalTable.setItems(naturalPersonData);

    }

    public void clicked_choosePhysicalButton(ActionEvent actionEvent) {
    }

    public void clicked_searchLegalButton(ActionEvent actionEvent) {
    }

    public void clicked_searchPhysicalButton(ActionEvent actionEvent) {
    }

    public void clicked_chooseLegalButton(ActionEvent actionEvent) {
    }

    public void clicked_addLegalButton(ActionEvent actionEvent) {
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

    public void clicked_refreshLegalTable(ActionEvent actionEvent) {
    }
}

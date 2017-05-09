package ua.iasa.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.Document;
import ua.iasa.entity.JuridicalPerson;
import ua.iasa.entity.NaturalPerson;
import ua.iasa.repository.JuridicalPersonRepository;
import ua.iasa.repository.NaturalPersonRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
public class MainMenuController {


    //PART FOR NATURAL PERSON TAB

    private ObservableList<NaturalPerson> natpersdata;
    @Autowired
    private NaturalPersonRepository natpersrepo;
    @FXML
    public Button addPhysicalButton;
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
    //@FXML private javafx.scene.control.MenuItem createDocId;
    @Qualifier("newDocumentView")
    @Autowired
    private View view;
    private static Stage primaryStage;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
    }

    private boolean isStringNotEmpty(String text) {
        if (text.equals("") || text.length() == 0 || isStringContainsOnlySpaces(text))
            return false;
        return true;
    }

    private boolean isStringContainsOnlySpaces(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != ' ') return false;
        }
        return true;
    }

    private boolean isPhysicalNameFilled() {
        return isStringNotEmpty(physicalNameTextField.getText());
    }

    private boolean isPhysicalSurnameFilled() {
        return isStringNotEmpty(physicalSurnameTextField.getText());
    }

    private boolean isPhysicalFathersNameFilled() {
        return isStringNotEmpty(physicalFathersNameTextField.getText());
    }

    private boolean isAllPhysicalDataFilled() {
        return (isPhysicalNameFilled() && isPhysicalFathersNameFilled() && isPhysicalSurnameFilled());
    }

    private boolean isAnyPhysicalDataFilled() {
        return (isPhysicalNameFilled() || isPhysicalFathersNameFilled() || isPhysicalSurnameFilled());
    }

    @FXML
    public void AddNaturalPerson(ActionEvent actionEvent) {
        if (isAllPhysicalDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            NaturalPerson pers = new NaturalPerson(null, null, DocumentSet
                    , physicalNameTextField.getText(),
                    physicalSurnameTextField.getText(),
                    physicalFathersNameTextField.getText(), null);
            NaturalPerson p = natpersrepo.save(pers);
            natpersdata.add(p);
            //refreshNaturalTable(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    @FXML
    public void refreshNaturalTable(ActionEvent actionEvent) {
        //natpersons = FXCollections.observableArrayList();
        //List<NaturalPerson> natpersons = new ArrayList<>();
        List<NaturalPerson> natpersons = (List) natpersrepo.findAll();
        natpersdata = FXCollections.observableArrayList(natpersons);
        // Столбцы таблицы
        //TableColumn<NaturalPerson, String> SurnameColumn = new TableColumn<>("Прізвище");
        physicalSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        //TableColumn<NaturalPerson, String> NameColumn = new TableColumn<>("Ім я");
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //TableColumn<NaturalPerson, String> FathersNameColumn = new TableColumn<>("По-батькові");
        physicalFathersNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        physicalTable.setItems(natpersdata);

    }

    //TODO
    public void clicked_choosePhysicalButton(ActionEvent actionEvent) {
    }


    @FXML
    public void searchNaturalPerson(ActionEvent actionEvent) {
        if (isAnyPhysicalDataFilled()) {
            NaturalPerson searchperson = natpersrepo.findBySurnameAndNameAndPatronymic(physicalSurnameTextField.getText(),
                    physicalNameTextField.getText(), physicalFathersNameTextField.getText());
            natpersdata = FXCollections.observableArrayList(searchperson);
            physicalSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            physicalFathersNameColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
            physicalTable.setItems(natpersdata);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    //PART FOR JURIDICAL PERSON TAB

    private ObservableList<JuridicalPerson> jurpersdata;
    @Autowired
    private JuridicalPersonRepository jurpersrepo;
    @FXML
    private TextField edrpouTextField;
    @FXML
    private TextField JurNameTextField;

    @FXML
    private Button searchLegalButton;
    @FXML
    private Button addLegalButton;


    @FXML
    private TableView<JuridicalPerson> legalTable;

    @FXML
    private TableColumn<JuridicalPerson, String> legalNameColumn;
    @FXML
    private TableColumn<JuridicalPerson, Integer> edrpouColumn;

    private boolean isJurNameFilled() {
        return isStringNotEmpty(JurNameTextField.getText());
    }


    private boolean isEdrpouFilled() {
        return isStringNotEmpty(edrpouTextField.getText());
    }

    private boolean isAllJurDataFilled() {
        return (isJurNameFilled() && isEdrpouFilled());
    }

    private boolean isAnyJurlDataFilled() {
        return (isJurNameFilled() || isEdrpouFilled());
    }

    //TODO
    public void clicked_chooseLegalButton(ActionEvent actionEvent) {
    }

    @FXML
    public void addJuridicalPerson(ActionEvent actionEvent) {
        if (isAllJurDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            JuridicalPerson pers = new JuridicalPerson(null, null, DocumentSet
                    , JurNameTextField.getText(), edrpouTextField.getText());
            JuridicalPerson p = jurpersrepo.save(pers);
            jurpersdata.add(p);
            //refreshNaturalTable(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    @FXML
    public void searchJuridicalPerson(ActionEvent actionEvent) {
        if (isAnyJurlDataFilled()) {
            JuridicalPerson searchperson = jurpersrepo.findByNameAndEdrpou(JurNameTextField.getText(), edrpouTextField.getText());
            jurpersdata = FXCollections.observableArrayList(searchperson);
            legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            edrpouColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
            legalTable.setItems(jurpersdata);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    @FXML
    public void refreshJuridicalTable(ActionEvent actionEvent) {
        List<JuridicalPerson> jurpersons = (List) jurpersrepo.findAll();
        jurpersdata = FXCollections.observableArrayList(jurpersons);
        // Столбцы таблицы
        legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        edrpouColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
        legalTable.setItems(jurpersdata);
    }

    public void createDoc() {
        Stage stage = (Stage) addPhysicalButton.getScene().getWindow();
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.show();
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

}

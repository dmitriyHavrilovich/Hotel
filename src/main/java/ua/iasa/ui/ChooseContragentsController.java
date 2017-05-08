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
import lombok.Data;
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
import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mahaon on 07.05.2017.
 */

@NoArgsConstructor
public class ChooseContragentsController {

    //PART FOR NATURAL PERSON TAB
    private ObservableList<NaturalPerson> natpersdata;
    @Autowired
    private NaturalPersonRepository natpersrepo;
    @FXML private TextField physicalSurnameTextField;
    @FXML private TextField physicalNameTextField;
    @FXML private TextField physicalFathersNameTextField;

    @FXML private Button searchPhysicalButton;
    @FXML private Button choosePhysicalButton;
    @FXML private Button addPhysicalButton;
    @FXML private Button editPhysicalButton;
    @FXML private Button deletePhysicalButton;
    @FXML private Button closeButton1;
    @FXML private Button refreshPhysicalTable;
    @FXML private Button refreshLegalTable;

    @FXML private TableView<NaturalPerson> physicalTable;

    @FXML private TableColumn<NaturalPerson, String> physicalSurnameColumn;
    @FXML private TableColumn<NaturalPerson, String> physicalNameColumn;
    @FXML private TableColumn<NaturalPerson, String> physicalFathersNameColumn;

    @Autowired
    private NewDocumentController newdoccontrooler;
    @Qualifier("newDocumentView")
    @Autowired
    private View view;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
        //initialize columns
        physicalSurnameColumn.setCellValueFactory(new PropertyValueFactory<NaturalPerson, String>("surname"));
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<NaturalPerson, String>("name"));
        physicalFathersNameColumn.setCellValueFactory(new PropertyValueFactory<NaturalPerson, String>("patronymic"));

        legalNameColumn.setCellValueFactory(new PropertyValueFactory<JuridicalPerson, String>("name"));
        legalCodeColumn.setCellValueFactory(new PropertyValueFactory<JuridicalPerson, Integer>("edrpou"));

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
    public void clicked_searchPhysicalButton(ActionEvent actionEvent) {
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
    }}

    public void clicked_choosePhysicalButton(ActionEvent actionEvent) {

        NaturalPerson data = physicalTable.getSelectionModel().getSelectedItem();
        if (data == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, choose contragent");
            alert.show();
        }

        try {
            newdoccontrooler.setContragent(data.getSurname() + " " + data.getName() + " " + data.getPatronymic(), data.getId());
            Stage stage = (Stage) choosePhysicalButton.getScene().getWindow();
            stage.setScene(new Scene(view.getView()));
            stage.setResizable(true);
            stage.show();
            //stage.close();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't set contragent to the document");
            alert.show();

        }
    }

    public void clicked_addPhysicalButton(ActionEvent actionEvent) {
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

    public void clicked_refreshPhysicalTable(ActionEvent actionEvent) {
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
    //PART FOR JURIDICAL PERSON TAB
    private ObservableList<JuridicalPerson> jurpersdata;
    @Autowired
    private JuridicalPersonRepository jurpersrepo;

    @FXML private TextField legalCodeTextField;
    @FXML private TextField legalNameTextField;

    @FXML private Button searchLegalButton;
    @FXML private Button chooseLegalButton;
    @FXML private Button addLegalButton;
    @FXML private Button editLegalButton;
    @FXML private Button deleteLegalButton;
    @FXML private Button closeButton2;

    @FXML private TableView <JuridicalPerson> legalTable;

    @FXML private TableColumn<JuridicalPerson, String> legalNameColumn;
    @FXML private TableColumn<JuridicalPerson, Integer> legalCodeColumn;

    private boolean isJurNameFilled() {
        return isStringNotEmpty(legalNameTextField.getText());
    }


    private boolean isEdrpouFilled() {
        return isStringNotEmpty(legalCodeTextField.getText());
    }

    private boolean isAllJurDataFilled() {
        return (isJurNameFilled() && isEdrpouFilled());
    }

    private boolean isAnyJurlDataFilled() {
        return (isJurNameFilled() || isEdrpouFilled());
    }
    public void clicked_searchLegalButton(ActionEvent actionEvent) {

        if (isAnyJurlDataFilled()) {
            JuridicalPerson searchperson = jurpersrepo.findByNameAndEdrpou(legalNameTextField.getText(),
                    legalCodeTextField.getText());
            jurpersdata = FXCollections.observableArrayList(searchperson);
            legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            legalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
            legalTable.setItems(jurpersdata);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    public void clicked_chooseLegalButton(ActionEvent actionEvent) {

    }

    public void clicked_addLegalButton(ActionEvent actionEvent) {
        if (isAllJurDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            JuridicalPerson pers = new JuridicalPerson(null, null, DocumentSet
                    , legalNameTextField.getText(), legalCodeTextField.getText());
            JuridicalPerson p = jurpersrepo.save(pers);
            jurpersdata.add(p);
            //refreshNaturalTable(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

    public void clicked_refreshLegalTable(ActionEvent actionEvent) {

        List<JuridicalPerson> jurpersons = (List) jurpersrepo.findAll();
        jurpersdata = FXCollections.observableArrayList(jurpersons);
        // Столбцы таблицы
        legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        legalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
        legalTable.setItems(jurpersdata);
    }


}

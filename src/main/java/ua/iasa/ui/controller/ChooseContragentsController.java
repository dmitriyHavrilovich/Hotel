package ua.iasa.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class ChooseContragentsController {
    public static Boolean isShown = false;

    //PART FOR NATURAL PERSON TAB
    private ObservableList<NaturalPerson> naturalPerson;
    @Autowired
    private NaturalPersonRepository naturalPersonRepository;
    @FXML
    private TextField physicalSurnameTextField;
    @FXML
    private TextField physicalNameTextField;
    @FXML
    private TextField physicalFathersNameTextField;

    @FXML
    private Button searchPhysicalButton;
    @FXML
    private Button choosePhysicalButton;
    @FXML
    private Button addPhysicalButton;
    @FXML
    private Button editPhysicalButton;
    @FXML
    private Button deletePhysicalButton;
    @FXML
    private Button closeButton1;
    @FXML
    private Button refreshPhysicalTable;
    @FXML
    private Button refreshLegalTable;

    @FXML
    private TableView<NaturalPerson> physicalTable;

    @FXML
    private TableColumn<NaturalPerson, String> physicalNameColumn;


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
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        legalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));

    }

    private boolean isStringNotEmpty(String text) {
        return !(text.equals("") || text.length() == 0 || isStringContainsOnlySpaces(text));
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
            String name = physicalSurnameTextField.getText() + " " + physicalNameTextField.getText() + " " +
                    physicalFathersNameTextField.getText();
            NaturalPerson searchperson = naturalPersonRepository.findByName(name);
            naturalPerson = FXCollections.observableArrayList(searchperson);
            physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            physicalTable.setItems(naturalPerson);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    public void clicked_choosePhysicalButton(ActionEvent actionEvent) {

        NaturalPerson data = physicalTable.getSelectionModel().getSelectedItem();
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, choose contragent");
            alert.show();
        }

        try {
            newdoccontrooler.setContragent(data.getName(), data.getId());
            Stage stage = (Stage) choosePhysicalButton.getScene().getWindow();
            stage.setScene(view.getView().getScene());
            stage.show();
            //stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't set contragent to the document");
            alert.show();

        }
    }

    public void clicked_addPhysicalButton(ActionEvent actionEvent) {
        if (isAllPhysicalDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            String name = physicalSurnameTextField.getText() + " " + physicalNameTextField.getText() + " " +
                    physicalFathersNameTextField.getText();
            NaturalPerson pers = new NaturalPerson(null, null, name, DocumentSet, null);
            NaturalPerson p = naturalPersonRepository.save(pers);
            naturalPerson.add(p);
            //refreshNaturalTable(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    public void clicked_refreshPhysicalTable(ActionEvent actionEvent) {
        List<NaturalPerson> naturalPersonList = (List<NaturalPerson>) naturalPersonRepository.findAll();
        naturalPerson = FXCollections.observableArrayList(naturalPersonList);
        // Столбцы таблицы
        //TableColumn<NaturalPerson, String> NameColumn = new TableColumn<>("Ім я");
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        physicalTable.setItems(naturalPerson);
    }

    //PART FOR JURIDICAL PERSON TAB
    private ObservableList<JuridicalPerson> juridicalPersons;
    @Autowired
    private JuridicalPersonRepository juridicalPersonRepository;

    @FXML
    private TextField legalCodeTextField;
    @FXML
    private TextField legalNameTextField;

    @FXML
    private Button searchLegalButton;
    @FXML
    private Button chooseLegalButton;
    @FXML
    private Button addLegalButton;
    @FXML
    private Button editLegalButton;
    @FXML
    private Button deleteLegalButton;
    @FXML
    private Button closeButton2;

    @FXML
    private TableView<JuridicalPerson> legalTable;

    @FXML
    private TableColumn<JuridicalPerson, String> legalNameColumn;
    @FXML
    private TableColumn<JuridicalPerson, Integer> legalCodeColumn;

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
            JuridicalPerson searchperson = juridicalPersonRepository.findByNameAndEdrpou(legalNameTextField.getText(),
                    legalCodeTextField.getText());
            juridicalPersons = FXCollections.observableArrayList(searchperson);
            legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            legalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
            legalTable.setItems(juridicalPersons);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    public void clicked_chooseLegalButton(ActionEvent actionEvent) {
        JuridicalPerson data = legalTable.getSelectionModel().getSelectedItem();
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, choose contragent");
            alert.show();
        }

        try {
            newdoccontrooler.setContragent(data.getName() + " " + data.getEdrpou(), data.getId());
            Stage stage = (Stage) chooseLegalButton.getScene().getWindow();
            stage.setScene(view.getView().getScene());
            stage.show();
            //stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't set contragent to the document");
            alert.show();

        }

    }

    public void clicked_addLegalButton(ActionEvent actionEvent) {
        if (isAllJurDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            JuridicalPerson pers = new JuridicalPerson(null, null, legalNameTextField.getText(), DocumentSet
                    , legalCodeTextField.getText());
            JuridicalPerson p = juridicalPersonRepository.save(pers);
            juridicalPersons.add(p);
            //refreshNaturalTable(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

    public void clicked_refreshLegalTable(ActionEvent actionEvent) {

        List<JuridicalPerson> juridicalPersons = (List<JuridicalPerson>) juridicalPersonRepository.findAll();
        this.juridicalPersons = FXCollections.observableArrayList(juridicalPersons);
        // Столбцы таблицы
        legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        legalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
        legalTable.setItems(this.juridicalPersons);
    }


}

package ua.iasa.ui.controller;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import ua.iasa.config.View;
import ua.iasa.entity.Document;
import ua.iasa.entity.JuridicalPerson;
import ua.iasa.entity.NaturalPerson;
import ua.iasa.entity.Room;
import ua.iasa.repository.*;
import ua.iasa.service.CheckSecurity;
import ua.iasa.ui.entity.ReferenceDocument;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@NoArgsConstructor
public class MainMenuController {


    @FXML
    private ComboBox<String> ChooseRoomBox;
    @FXML
    private DatePicker datePicker;

    @Autowired
    private RoomRepository roomrepo;

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
    private TableColumn<NaturalPerson, String> physicalNameColumn;
    @FXML
    private TableColumn<NaturalPerson, String> birthDateColumn;
    @Autowired
    private ReferenceDocumentsDao referenceDocumentsDao;
    @Autowired
    private ReferenceRoomDao referenceRoomDao;


    @Qualifier("newDocumentView")
    @Autowired
    private View view;
    private ObjectProperty<Predicate<ReferenceRoom>> roomFilter = new SimpleObjectProperty<>();
    @Qualifier("moveGoodRoomView")
    @Autowired
    private View view1;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {

        //set referenceDocumentTable
        ObservableList<ReferenceDocument> documents = FXCollections.observableArrayList(referenceDocumentsDao.getReferencesOfDocuments());

        //initialize columns
        idDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        goodsColumn.setCellValueFactory(new PropertyValueFactory<>("name_type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        documentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("doc_type"));
        contragentColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dovidnikDocumentsTable.setItems(documents);

        //setRoomTab
        ObservableList<ReferenceRoom> rooms = FXCollections.observableArrayList(referenceRoomDao.getReferencesOfRoom());
        RoomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        RoomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        goods.setCellValueFactory(new PropertyValueFactory<>("name_type"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).

        List<Room> roomes = (List<Room>) roomrepo.findAll();
        ObservableList<String> roome = FXCollections.observableArrayList(roomes.stream()
                .map(Room::getRoomNumber).distinct().collect(Collectors.toList()));
        ChooseRoomBox.setItems(roome);
        ChooseRoomBox.setValue(roomrepo.findByRoomType("Store").getRoomNumber());
        roomFilter.bind(Bindings.createObjectBinding(() ->
                        roomn -> ChooseRoomBox.getValue()
                                == null || Objects.equals(ChooseRoomBox.getValue(), roomn.getNumber()),
                ChooseRoomBox.valueProperty()));
        FilteredList<ReferenceRoom> filteredData = new FilteredList<>(rooms);
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> roomFilter.get(),
                roomFilter));
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ReferenceRoom> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(referenceRoomTable.comparatorProperty());
        referenceRoomTable.setItems(sortedData);

        //SET CONTRACTORS TAB
        //NATURAL
        List<NaturalPerson> natpersons = (List<NaturalPerson>) natpersrepo.findAll();
        natpersdata = FXCollections.observableArrayList(natpersons);
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        physicalTable.setItems(natpersdata);
        //JURIDICAL
        List<JuridicalPerson> juridicalPeople = (List<JuridicalPerson>) juridicalPersonRepository.findAll();
        this.juridicalPeople = FXCollections.observableArrayList(juridicalPeople);
        // Столбцы таблицы
        legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        edrpouColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
        legalTable.setItems(this.juridicalPeople);

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
            if(isDateOkay(datePicker)) {
                Set<Document> DocumentSet = new HashSet<>();
                String name = physicalSurnameTextField.getText() + " " + physicalNameTextField.getText() + " " +
                        physicalFathersNameTextField.getText();
                NaturalPerson pers = new NaturalPerson(null, null, name, DocumentSet,
                        datePicker.getValue().toString());
                NaturalPerson p = natpersrepo.save(pers);
                natpersdata.add(p);
                //refreshNaturalTable(actionEvent);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Only adults, please!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    @FXML
    public void refreshNaturalTable(ActionEvent actionEvent) {
        List<NaturalPerson> natpersons = (List<NaturalPerson>) natpersrepo.findAll();
        natpersdata = FXCollections.observableArrayList(natpersons);
        physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        physicalTable.setItems(natpersdata);

    }

    @FXML
    public void searchNaturalPerson(ActionEvent actionEvent) {
        if (isAnyPhysicalDataFilled()) {
            String name = physicalSurnameTextField.getText() + " " + physicalNameTextField.getText() + " " +
                    physicalFathersNameTextField.getText();
            NaturalPerson searchperson = natpersrepo.findByName(name);
            natpersdata = FXCollections.observableArrayList(searchperson);
            physicalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            physicalTable.setItems(natpersdata);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }
    private static boolean isDateOkay(DatePicker datePicker){
        return !(LocalDate.now().getYear()-datePicker.getValue().getYear()<18);

    }

    //PART FOR JURIDICAL PERSON TAB

    private ObservableList<JuridicalPerson> juridicalPeople;
    @Autowired
    private JuridicalPersonRepository juridicalPersonRepository;
    @FXML
    private TextField edrpouTextField;
    @FXML
    private TextField JurNameTextField;

    @Autowired
    private NewDocumentController newDocumentController;
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


    @FXML
    public void addJuridicalPerson(ActionEvent actionEvent) {
        try{
        if (isAllJurDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            JuridicalPerson pers = new JuridicalPerson(null, null, JurNameTextField.getText(), DocumentSet
                    , edrpouTextField.getText());
            JuridicalPerson p = juridicalPersonRepository.save(pers);
            juridicalPeople.add(p);
            //refreshNaturalTable(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }}
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Alreade exists " +
                    "or contact developer");
            alert.show();
        }
    }

    @FXML
    public void searchJuridicalPerson(ActionEvent actionEvent) {
        if (isAnyJurlDataFilled()) {
            JuridicalPerson searchPerson = juridicalPersonRepository.findByNameAndEdrpou(JurNameTextField.getText(),
                    edrpouTextField.getText());
            juridicalPeople = FXCollections.observableArrayList(searchPerson);
            legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            edrpouColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
            legalTable.setItems(juridicalPeople);


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty fields");
            alert.show();
        }
    }

    @FXML
    public void refreshJuridicalTable(ActionEvent actionEvent) {
        List<JuridicalPerson> juridicalPeople = (List<JuridicalPerson>) juridicalPersonRepository.findAll();
        this.juridicalPeople = FXCollections.observableArrayList(juridicalPeople);
        // Столбцы таблицы
        legalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        edrpouColumn.setCellValueFactory(new PropertyValueFactory<>("edrpou"));
        legalTable.setItems(this.juridicalPeople);
    }

    @Autowired
    private CheckSecurity checkSecurity;
    @FXML
    public void createDoc() {
        Boolean access = true;
        try {
            checkSecurity.checkAdmin();
        }
        catch (AccessDeniedException exception){
            access = false;
        }
        if(access) {
            if (!NewDocumentController.isShown) {
                Stage stage = (Stage) addPhysicalButton.getScene().getWindow();
                stage.setScene(new Scene(view.getView()));
                stage.setResizable(true);
                stage.show();
                NewDocumentController.isShown = true;
            } else {
                newDocumentController.clearAllFields();
                Stage stage = (Stage) addPhysicalButton.getScene().getWindow();
                stage.setScene(view.getView().getScene());
                stage.setResizable(true);
                stage.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Users can't create documents");
            alert.show();
        }
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

    //TAB FOR REFERENCE DOCUMENT

    public void setReferenceDocumentTable() {
        ObservableSet<ReferenceDocument> documents =
                FXCollections.observableSet(referenceDocumentsDao.getReferencesOfDocuments());

        //initialize columns
        idDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        goodsColumn.setCellValueFactory(new PropertyValueFactory<>("name_type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        documentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("doc_type"));
        contragentColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dovidnikDocumentsTable.setItems(FXCollections.observableArrayList(documents));

    }

    @FXML
    private TableView<ReferenceDocument> dovidnikDocumentsTable;

    @FXML
    private TableColumn<ReferenceDocument, Long> idDocumentColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> dateColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> goodsColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> unitsColumn;
    @FXML
    private TableColumn<ReferenceDocument, Double> amountColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> currencyColumn;
    @FXML
    private TableColumn<ReferenceDocument, Double> priceColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> documentTypeColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> contragentColumn;
    @FXML
    private TableColumn<ReferenceDocument, String> employeeColumn;

    @FXML
    private Button reportButton;

    @SneakyThrows
    public String toCSV(Set<ReferenceDocument> referencesOfDocuments) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(ReferenceDocument.class).withHeader();
        return mapper.writer(schema).writeValueAsString(referenceDocumentsDao.getReferencesOfDocuments());
    }

    @FXML
    @SneakyThrows
    public void Reporting(ActionEvent actionEvent) {
        File file = new File("report.csv");
        String report = toCSV(referenceDocumentsDao.getReferencesOfDocuments());
        FileUtils.writeStringToFile(file, report);
    }

    @SneakyThrows
    @FXML
    public void Report(ActionEvent actionEvent) {
        // File file= new File("report.txt");
        // System.out.println(file.getAbsolutePath());
        //String report = toCSV((List)referenceDocumentsDao.getReferencesOfDocuments());
        //FileUtils.writeStringToFile(file, report);

    }

    //TAB FOR ROOM REFERENCE

    @FXML
    private TableView<ReferenceRoom> referenceRoomTable;

    @FXML
    private TableColumn<ReferenceRoom, String> goods;
    @FXML
    private TableColumn<ReferenceRoom, Double> amount;
    @FXML
    private TableColumn<ReferenceRoom, String> RoomNumberColumn;
    @FXML
    private TableColumn<ReferenceRoom, String> RoomTypeColumn;

    @FXML
    public Button MoveButton;


    @FXML
    public void MoveGood(ActionEvent actionEvent) throws IOException {
        Boolean access = true;
        try {
            checkSecurity.checkAdmin();
        }
        catch (AccessDeniedException exception){
            access = false;
        }
        if(access) {
        if (!MoveGoodRoomController.isShown) {
            Stage stage = (Stage) MoveButton.getScene().getWindow();
            stage.setScene(new Scene(view1.getView()));
            stage.setResizable(true);
            stage.show();
            MoveGoodRoomController.isShown = true;
        } else {
            Stage stage = (Stage) MoveButton.getScene().getWindow();
            stage.setScene(view1.getView().getScene());
            stage.setResizable(true);
            stage.show();
        }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Access denied!");
            alert.show();
        }
    }

    public void setReferenceRoomTable(ObservableList<ReferenceRoom> rooms) {
        rooms = FXCollections.observableArrayList(referenceRoomDao.getReferencesOfRoom());
        RoomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        RoomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        goods.setCellValueFactory(new PropertyValueFactory<>("name_type"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        roomFilter.bind(Bindings.createObjectBinding(() ->
                        roomn -> ChooseRoomBox.getValue()
                                == null || Objects.equals(ChooseRoomBox.getValue(), roomn.getNumber()),
                ChooseRoomBox.valueProperty()));
        FilteredList<ReferenceRoom> filteredData = new FilteredList<>(rooms);
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                () -> roomFilter.get(),
                roomFilter));
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ReferenceRoom> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(referenceRoomTable.comparatorProperty());
        referenceRoomTable.setItems(sortedData);

    }

}

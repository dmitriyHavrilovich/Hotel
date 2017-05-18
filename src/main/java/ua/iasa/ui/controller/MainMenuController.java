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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.Document;
import ua.iasa.entity.JuridicalPerson;
import ua.iasa.entity.NaturalPerson;
import ua.iasa.entity.Room;
import ua.iasa.repository.*;
import ua.iasa.ui.entity.ReferenceDocument;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@NoArgsConstructor
public class MainMenuController {


    @FXML
    private ComboBox ChooseRoomBox;
    @FXML
    private DatePicker datePicker;

    private ObservableList<String> roome;
    @Autowired private RoomRepository roomrepo;

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
    ObjectProperty<Predicate<ReferenceRoom>> roomFilter = new SimpleObjectProperty<>();

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {

        //set referenceDocumentTable
        documents = FXCollections.observableArrayList(referenceDocumentsDao.getReferencesOfDocuments());

        //initialize columns
        idDocumentColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, Long>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("date"));
        goodsColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("name_type"));
        //  unitsColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("currency"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, Double>("amount"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("currency"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, Double>("price"));
        documentTypeColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("doc_type"));
        contragentColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("name"));
        //employeeColumn.setCellValueFactory(new PropertyValueFactory<ReferencesDocumentView, String>("employee"));
        dovidnikDocumentsTable.setItems(documents);

        //setRoomTab
        rooms = FXCollections.observableArrayList(referenceRoomDao.getReferencesOfRoom());
        idRoom.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, Long>("id"));
        goods.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, String>("name_type"));
        //  unitsColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("currency"));
        amount.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, Double>("amount"));
        //currency.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, String>("currency"));
        //price.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, Double>("price"));

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).

        List<Room> roomes = (List<Room>) roomrepo.findAll();
        roome = FXCollections.observableArrayList(roomes.stream()
                .map(Room::getRoomNumber).distinct().collect(Collectors.toList()));
        ChooseRoomBox.setItems(roome);
        ChooseRoomBox.setValue(roomrepo.findByRoomType("Store").getRoomNumber());
        roomFilter.bind(Bindings.createObjectBinding(() ->
                        roomn -> ChooseRoomBox.getValue()
                                == null || ChooseRoomBox.getValue()
                                == roomn.getNumber(),
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
            String name = physicalSurnameTextField.getText() + " " + physicalNameTextField.getText() + " " +
                    physicalFathersNameTextField.getText();
            NaturalPerson pers = new NaturalPerson(null, null, name, DocumentSet,
                    datePicker.getValue().toString());
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
        //TableColumn<NaturalPerson, String> NameColumn = new TableColumn<>("Ім я");
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

    //PART FOR JURIDICAL PERSON TAB

    private ObservableList<JuridicalPerson> jurpersdata;
    @Autowired
    private JuridicalPersonRepository jurpersrepo;
    @FXML
    private TextField edrpouTextField;
    @FXML
    private TextField JurNameTextField;

    @Autowired private NewDocumentController newDocumentController;
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
        if (isAllJurDataFilled()) {
            Set<Document> DocumentSet = new HashSet<>();
            JuridicalPerson pers = new JuridicalPerson(null, null, JurNameTextField.getText(), DocumentSet
                    , edrpouTextField.getText());
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
            JuridicalPerson searchperson = jurpersrepo.findByNameAndEdrpou(JurNameTextField.getText(),
                    edrpouTextField.getText());
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
        if (!NewDocumentController.isShown){
            Stage stage = (Stage) addPhysicalButton.getScene().getWindow();
            stage.setScene(new Scene(view.getView()));
            stage.setResizable(true);
            stage.show();
            NewDocumentController.isShown = true;
        }
        else {
            newDocumentController.clearAllFields();
            Stage stage = (Stage) addPhysicalButton.getScene().getWindow();
            stage.setScene(view.getView().getScene());
            stage.setResizable(true);
            stage.show();
        }
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

    //TAB FOR REFERENCE DOCUMENT

    public void setReferenceDocumentTable(ObservableSet<ReferenceDocument> documents) {
        documents = FXCollections.observableSet(referenceDocumentsDao.getReferencesOfDocuments());

        //initialize columns
        idDocumentColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, Long>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("date"));

        goodsColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("name_type"));
        //  unitsColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("currency"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, Double>("amount"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("currency"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, Double>("price"));
        documentTypeColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("doc_type"));
        contragentColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("name"));
        //employeeColumn.setCellValueFactory(new PropertyValueFactory<ReferencesDocumentView, String>("employee"));
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

    private ObservableList<ReferenceDocument> documents;

    @SneakyThrows
   public String toCSV (Set<ReferenceDocument> listOfPojos){
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
    private TableColumn<ReferenceRoom, Long> idRoom;
    @FXML
    private TableColumn<ReferenceRoom, String> goods;
    @FXML
    private TableColumn<ReferenceRoom, Double> amount;


    private ObservableList<ReferenceRoom> rooms;
    @FXML public Button MoveButton;



    @FXML
    public void MoveGood(ActionEvent actionEvent) throws IOException{
        if (!MoveGoodRoomController.isShown){
            Stage stage = (Stage) MoveButton.getScene().getWindow();
            stage.setScene(new Scene(view.getView()));
            stage.setResizable(true);
            stage.show();
            ChooseContragentsController.isShown = true;
        }
        else {
            Stage stage = (Stage) MoveButton.getScene().getWindow();
            stage.setScene(view.getView().getScene());
            stage.setResizable(true);
            stage.show();
        }
    }
    public void setReferenceRoomTable(ObservableList<ReferenceRoom> rooms) {
        rooms = FXCollections.observableArrayList(referenceRoomDao.getReferencesOfRoom());
        //rooms = FXCollections.observableSet(referenceRoomDao.getReferencesOfRoom());
        idRoom.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, Long>("id"));
        goods.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, String>("name_type"));
        //  unitsColumn.setCellValueFactory(new PropertyValueFactory<ReferenceDocument, String>("currency"));
        amount.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, Double>("amount"));
        //currency.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, String>("currency"));
        //price.setCellValueFactory(new PropertyValueFactory<ReferenceRoom, Double>("price"));
        //referenceRoomTable.setItems(FXCollections.observableArrayList(rooms));
        roomFilter.bind(Bindings.createObjectBinding(() ->
                        roomn -> ChooseRoomBox.getValue()
                                == null || ChooseRoomBox.getValue()
                                == roomn.getNumber(),
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

package ua.iasa.ui.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.*;
import ua.iasa.repository.*;
import ua.iasa.ui.entity.ReferenceDocument;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Slf4j
@Data
public class NewDocumentController {
    public static Boolean isShown = false;
    @FXML
    public Button createButton;
    @FXML
    public TextField productField;
    @FXML
    public ChoiceBox<String> employeeChoiceBox;
    @FXML
    private Button cancelButton;
    private ObservableList<Product> goodsInTable;
    private Long contragentId;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button chooseContragentButton;
    @FXML
    private ChoiceBox documentTypeChoiceBox;
    @FXML
    private Button addGoodButton;
    @FXML
    private TableView<Product> chosenGoodsTable;

    @FXML
    private ChoiceBox currencyChoiceBox;
    @FXML
    private TableColumn<Product, String> goodColumn;
    @FXML
    private TableColumn<Product, String> currencyColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Double> amountColumn;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField contragentTextField;
    @FXML
    private TextField currentAmountTextField;
    private ObservableList<String> productnamedata;
    private ObservableList<String> doctypedata;
    @Autowired
    private ProductRepository procrepo;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private DocumentTypeRepository doctyperepo;
    @Autowired
    private ContractorRepository contractorRepository;
    @Qualifier("chooseContragentsView")
    @Autowired
    private View view;
    @Qualifier("mainView")
    @Autowired
    private View view1;
    @Autowired
    private MainMenuController mainMenuController;
    private ObservableSet<ReferenceDocument> documents;
    @Autowired
    private ReferenceDocumentsDao referenceDocumentsDao;
    @Autowired
    private ReferenceRoomDao referenceRoomDao;
    private ObservableList<ReferenceRoom> rooms;
    @Autowired
    private EntityManager em;

    @FXML
    public void initialize() {
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<String> currency = new ArrayList<>();
        currency.add("uah");
        currency.add("usd");
        currencyChoiceBox.setItems(FXCollections.observableArrayList(currency));

        List<DocumentType> doctype = (List) doctyperepo.findAll();
        doctypedata = FXCollections.observableArrayList(doctype.stream()
                .map(DocumentType::getType).distinct().collect(Collectors.toList()));
        documentTypeChoiceBox.setItems(doctypedata);

        List<Personal> personalList = (List<Personal>) personalRepository.findAll();
        ObservableList<String> pers = FXCollections.observableArrayList(personalList.stream()
                .map(Personal::getNamep).distinct().collect(Collectors.toList()));
        employeeChoiceBox.setItems(pers);
        //initialize array of data
        goodsInTable = FXCollections.observableArrayList();
        //initialize columns
        amountColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAmount()));
        priceColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPrice()));
        currencyColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDocument().getCurrency()));
        goodColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNameType()));
    }

    @FXML
    public void clicked_ChooseContragentButton(ActionEvent actionEvent) throws IOException {
        if (!ChooseContragentsController.isShown) {
            Stage stage = (Stage) chooseContragentButton.getScene().getWindow();
            stage.setScene(new Scene(view.getView()));
            stage.setResizable(true);
            stage.show();
            ChooseContragentsController.isShown = true;
        } else {
            Stage stage = (Stage) chooseContragentButton.getScene().getWindow();
            stage.setScene(view.getView().getScene());
            stage.setResizable(true);
            stage.show();
        }
    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }


    @FXML
    public void addProduct(ActionEvent actionEvent) {
        if (priceTextField.getText().equals("")
                || amountTextField.getText().equals("")
                || Double.parseDouble(amountTextField.getText())<0
                || Double.parseDouble(priceTextField.getText())<0
                || productField.getText().equals("")
                || currencyChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please, fill all fields in goods section or input right amount or price!");
            alert.show();
        } else if (isGoodInGoodsBasket(productField.getText(), goodsInTable)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Such good is already present in basket!");
            alert.show();
        } else
            try {
                log.info("Adding good into table");
                goodsInTable.add(new Product(null, productField.getText(),
                        "",
                        Double.parseDouble(amountTextField.getText()),
                        Double.parseDouble(priceTextField.getText()),
                        new Document(null,
                                datePicker.getValue().toString(),
                                new DocumentType(null, documentTypeChoiceBox.getValue().toString()),
                                null,
                                currencyChoiceBox.getValue().toString(),
                                new Contractor(null, "", contragentTextField.getText(), null),
                                new Personal(null, employeeChoiceBox.getValue(), null))));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Problems with fields data!");
                alert.show();
            }
        try {
            chosenGoodsTable.setItems(goodsInTable);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't refresh the table!");
            alert.show();
        }
    }

    public void clicked_cancelButton(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(view1.getView().getScene());
        stage.show();
    }

    public void clicked_createButton(ActionEvent actionEvent) {
        if (isAllFieldsAreFilled()) {
            if (isDateOkay(datePicker)) {
                Contractor contractor = contractorRepository.findByName(contragentTextField.getText());
                Personal per = personalRepository.findByNamep(employeeChoiceBox.getValue());
                log.info("Creating document in create button");
                Document document = new Document(null,
                        datePicker.getValue().toString(),
                        new DocumentType(null, documentTypeChoiceBox.getValue().toString()),
                        null,
                        currencyChoiceBox.getValue().toString(),
                        contractor, per);
                for (Product p : goodsInTable) {
                    p.setDocument(document);
                }
                document.setProducts(goodsInTable);
                contractor.getDocument().add(document);
                per.getDocument().add(document);
                contractorRepository.save(contractor);
                //personalRepository.save(per);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "New document was added.");
                alert.show();

                documents = FXCollections.observableSet(referenceDocumentsDao.getReferencesOfDocuments());
                mainMenuController.setReferenceDocumentTable();
                rooms = FXCollections.observableArrayList(referenceRoomDao.getReferencesOfRoom());
                mainMenuController.setReferenceRoomTable(rooms);
                Stage stage = (Stage) createButton.getScene().getWindow();
                stage.setScene(view1.getView().getScene());
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Please, insert correct date!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please, fill all fields to create document");
            alert.show();
        }

    }

    @FXML
    public void clicked_deleteGoodButton() {
        if (!goodsInTable.isEmpty()) {
            try {
                int goodInBasketId = chosenGoodsTable.getSelectionModel().getSelectedIndex();
                goodsInTable.remove(goodInBasketId);
                chosenGoodsTable.setItems(goodsInTable);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product deleted from list.");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Unknown error during deletion of good from basket!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can't delete an item from empty list!");
            alert.show();
        }
        //if (chosenGoodsTable.getSelectionModel().is)
    }

    public void setContragent(String name, Long contragentId) {
        contragentTextField.setText(name);
        this.contragentId = contragentId;
    }

    public void clearAllFields() {
        contragentTextField.clear();
        amountTextField.clear();
        priceTextField.clear();
        goodsInTable.clear();

    }

    private static boolean isDateOkay(DatePicker datePicker) {
        return !datePicker.getValue().isAfter(LocalDate.now());

    }

    private static boolean isGoodInGoodsBasket(String good, ObservableList<Product> goodsInBasket) {
        for (int i = 0; i < goodsInBasket.size(); i++)
            if (good.equals(goodsInBasket.get(i).getNameType()))
                return true;
        return false;
    }

    private boolean isAllFieldsAreFilled() {
        return !(goodsInTable.isEmpty()
                || datePicker.getEditor().getText().isEmpty()
                || contragentTextField.getText().isEmpty());
    }
}

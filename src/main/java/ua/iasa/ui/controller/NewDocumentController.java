package ua.iasa.ui.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.Contractor;
import ua.iasa.entity.Document;
import ua.iasa.entity.DocumentType;
import ua.iasa.entity.Product;
import ua.iasa.repository.ContractorRepository;
import ua.iasa.repository.DocumentTypeRepository;
import ua.iasa.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class NewDocumentController {

    @FXML
    public Button createButton;
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
    private ChoiceBox goodChoiceBox;
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
    private DocumentTypeRepository doctyperepo;
    @Autowired
    private ContractorRepository contractorRepository;
    @Qualifier("chooseContragentsView")
    @Autowired
    private View view;
    @Qualifier("mainView")
    @Autowired
    private View view1;

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
        List<Product> prods = (List) procrepo.findAll();
        productnamedata = FXCollections.observableArrayList(prods.stream()
                .map(Product::getNameType).distinct().collect(Collectors.toList()));
        goodChoiceBox.setItems(productnamedata);
        List<DocumentType> doctype = (List) doctyperepo.findAll();
        doctypedata = FXCollections.observableArrayList(doctype.stream()
                .map(DocumentType::getType).distinct().collect(Collectors.toList()));
        documentTypeChoiceBox.setItems(doctypedata);

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
        Stage stage = (Stage) chooseContragentButton.getScene().getWindow();
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.show();
    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }

    public void clicked_deleteGoodButton(ActionEvent actionEvent) {
    }

    @FXML
    public void addProduct(ActionEvent actionEvent) {
        goodsInTable.add(new Product(null, goodChoiceBox.getValue().toString(),
                "",
                Double.parseDouble(amountTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                new Document(null,
                        datePicker.getValue().toString(),
                        new DocumentType(null, documentTypeChoiceBox.getValue().toString()),
                        null,
                        currencyChoiceBox.getValue().toString()
                )));
        chosenGoodsTable.setItems(goodsInTable);
    }

    public void clicked_cancelButton(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(view1.getView().getScene());
        stage.show();
    }

    public void clicked_createButton(ActionEvent actionEvent) {

        Document document = new Document(null,
                datePicker.getValue().toString(),
                new DocumentType(null, documentTypeChoiceBox.getValue().toString()),
                goodsInTable,
                currencyChoiceBox.getValue().toString());
        Contractor contractor = contractorRepository.findByName(contragentTextField.getText());
        Set<Document> documentSet = new HashSet<>();
        documentSet.add(document);
        contractor.setDocument(documentSet);
        contractorRepository.save(contractor);
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.setScene(view1.getView().getScene());
        stage.show();
    }



    public void setContragent(String name, Long contragentId) {
        contragentTextField.setText(name);
        this.contragentId = contragentId;
    }


}

package ua.iasa.ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iasa.entity.*;
import ua.iasa.repository.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class NewDocumentController {


    @FXML
    public DatePicker datePicker;
    @FXML
    public Button chooseContragentButton;
    @FXML
    public ChoiceBox documentTypeChoiceBox;
    @FXML
    public Button refreshBtn;
    @FXML
    private Button addGoodButton;
    @FXML
    private
    TableView<MovementDocument> chosenGoodsTable;
    @FXML
    private ChoiceBox goodChoiceBox;
    @FXML
    private ChoiceBox currencyChoiceBox;
    @FXML
    private TableColumn<MovementDocument, String> goodColumn;
    @FXML
    private TableColumn<MovementDocument, String> currencyColumn;
    @FXML
    private TableColumn<MovementDocument, Double> priceColumn;
    @FXML
    private TableColumn<MovementDocument, Double> amountColumn;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField contragentTextField;
    @FXML
    private TextField currentAmountTextField;
    private ObservableList<MovementDocument> movdocdata;
    private ObservableList<String> curdata;
    private ObservableList<String> productnamedata;
    private ObservableList<String> doctypedata;
    private ObservableList<Product> productdata;
    @Autowired
    private MovementDocumentRepository movdocrepo;
    @Autowired
    private CurrencyRepository currepo;
    @Autowired
    private ProductRepository procrepo;
    @Autowired
    private DocumentTypeRepository doctyperepo;


    @FXML
    public void initialize() {
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<Currency> currency = (List) currepo.findAll();
        curdata = FXCollections.observableArrayList(currency.stream()
                .map(Currency::getName).distinct().collect(Collectors.toList()));
        currencyChoiceBox.setItems(curdata);
        List<Product> prods = (List) procrepo.findAll();
        productnamedata = FXCollections.observableArrayList(prods.stream()
                .map(Product::getNameType).distinct().collect(Collectors.toList()));
        goodChoiceBox.setItems(productnamedata);
        List<DocumentType> doctype = (List) doctyperepo.findAll();
        doctypedata = FXCollections.observableArrayList(doctype.stream()
                .map(DocumentType::getType).distinct().collect(Collectors.toList()));
        documentTypeChoiceBox.setItems(doctypedata);

    }


    public void clicked_ChooseContragentButton(ActionEvent actionEvent) {
    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }

    public void clicked_deleteGoodButton(ActionEvent actionEvent) {
    }
    @FXML
    public void addProduct(ActionEvent actionEvent) {
        //chosenGoodsTable.setItems(movdocdata);

        //Set<Product> productSet = new HashSet<>();
        Set<MovementDocument> documents = new HashSet<>();
        MovementDocument movdoc = new MovementDocument(null,
              Long.parseLong(amountTextField.getText()), Double.parseDouble(priceTextField.getText()),
                new Currency(null, currencyChoiceBox.getValue().toString()), null, null);
        documents.add(movdoc);
        Product pr = new Product(null, goodChoiceBox.getValue().toString(),null, documents);
        //productSet.add(pr);
        movdoc.setProduct(pr);
        MovementDocument p = movdocrepo.save(movdoc);
        Product proc = procrepo.save(pr);
        movdocdata = FXCollections.observableArrayList(p);
        productdata = FXCollections.observableArrayList(proc);
        goodColumn.setCellValueFactory(product ->new ReadOnlyStringWrapper
                (product.getValue().getProduct().getNameType()));
        //currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency.name"));
        currencyColumn.setCellValueFactory(currency ->new ReadOnlyStringWrapper(currency.getValue()
                .getCurrency().getName()));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        chosenGoodsTable.setItems(movdocdata);
        //productdata.add(proc);
        //movdocdata.add(p);


    }

    public void clicked_cancelButton(ActionEvent actionEvent) {
    }

    public void clicked_createButton(ActionEvent actionEvent) {
    }

    public void refreshTable(ActionEvent actionEvent) {
      //  goodColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
        //currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency.name"));
       // currencyColumn.setCellValueFactory(currency ->new ReadOnlyStringWrapper(currency.getValue()
         //       .getCurrency().getName()));
        //priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        //chosenGoodsTable.;
    }
}

package ua.iasa.ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iasa.entity.Currency;
import ua.iasa.entity.DocumentType;
import ua.iasa.entity.MovementDocument;
import ua.iasa.entity.Product;
import ua.iasa.repository.CurrencyRepository;
import ua.iasa.repository.DocumentTypeRepository;
import ua.iasa.repository.MovementDocumentRepository;
import ua.iasa.repository.ProductRepository;

import javax.annotation.PostConstruct;
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

    public void addProduct(ActionEvent actionEvent) {
        chosenGoodsTable.setItems(movdocdata);
        Set<Product> productSet = new HashSet<>();
       MovementDocument movdoc = new MovementDocument(null, datePicker.getValue().toString(),
                        new DocumentType(null, documentTypeChoiceBox.getValue().toString()),
                Long.parseLong(amountTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                new Currency(null, currencyChoiceBox.getValue().toString()), null, null);
        productSet.add(new Product(null, goodChoiceBox.getValue().toString(),null, movdoc));
        movdoc.setProducts(productSet);
        MovementDocument p = movdocrepo.save(movdoc);
        movdocdata = FXCollections.observableArrayList(p);
        goodColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        //currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency.name"));
        currencyColumn.setCellValueFactory(currency -> new ReadOnlyStringWrapper(currency.getValue().getCurrency().getName()));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        movdocdata.add(p);


    }

    public void clicked_cancelButton(ActionEvent actionEvent) {
    }

    public void clicked_createButton(ActionEvent actionEvent) {
    }
}

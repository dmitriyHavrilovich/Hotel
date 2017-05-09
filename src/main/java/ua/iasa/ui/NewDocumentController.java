package ua.iasa.ui;

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
import ua.iasa.entity.*;
import ua.iasa.repository.*;
import ua.iasa.ui.entity.GoodInTable;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class NewDocumentController {

    @FXML
    public Button cancelButton;
    @Autowired private ChooseContragentsController chooseContragentsController;
    private static Stage primaryStage;
    private ObservableList<GoodInTable> goodsInTable;
    private Long contragentId ;
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
    TableView<GoodInTable> chosenGoodsTable;
    @FXML
    private ChoiceBox goodChoiceBox;
    @FXML
    private ChoiceBox currencyChoiceBox;
    @FXML
    private TableColumn<GoodInTable, String> goodColumn;
    @FXML
    private TableColumn<GoodInTable, String> currencyColumn;
    @FXML
    private TableColumn<GoodInTable, Double> priceColumn;
    @FXML
    private TableColumn<GoodInTable, Double> amountColumn;
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

        //initialize array of data
        goodsInTable = FXCollections.observableArrayList();
        //initialize columns
        amountColumn.setCellValueFactory(new PropertyValueFactory<GoodInTable, Double>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<GoodInTable, Double>("price"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<GoodInTable, String>("currency"));
        goodColumn.setCellValueFactory(new PropertyValueFactory<GoodInTable, String>("good"));
    }

    @FXML
    public void clicked_ChooseContragentButton(ActionEvent actionEvent) throws IOException  {
        //Stage stage;
        //Parent root;
       // if(actionEvent.getSource()==chooseContragentButton){
            //get reference to the button's stage
            //stage=(Stage) chooseContragentButton.getScene().getWindow();
            //load up OTHER FXML document
            //root = FXMLLoader.load(getClass().getResource("../resources/fxml/chooseContragents.fxml"));
            //create a new scene with root and set the stage
           // Scene scene = new Scene(root);
            //stage.setScene(scene);
            //stage.show();
    //}
        //ChooseContragentsController = this;
        //new Create_Contragents().start(Main.getPrimaryStage());

    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }

    public void clicked_deleteGoodButton(ActionEvent actionEvent) {
    }
    @FXML
    public void addProduct(ActionEvent actionEvent) {
        //chosenGoodsTable.setItems(movdocdata);

        //Set<Product> productSet = new HashSet<>();
       // Set<MovementDocument> documents = new HashSet<>();
       // MovementDocument movdoc = new MovementDocument(null,
         //     Long.parseLong(amountTextField.getText()), Double.parseDouble(priceTextField.getText()),
           //     new Currency(null, currencyChoiceBox.getValue().toString()), null, null);
        //documents.add(movdoc);
        //Product pr = new Product(null, goodChoiceBox.getValue().toString(),null, documents);
        //productSet.add(pr);
        //movdoc.setProduct(pr);
        //MovementDocument p = movdocrepo.save(movdoc);
        //Product proc = procrepo.save(pr);
        //movdocdata = FXCollections.observableArrayList(p);
        //productdata = FXCollections.observableArrayList(proc);
        //goodColumn.setCellValueFactory(product ->new ReadOnlyStringWrapper
          //      (product.getValue().getProduct().getNameType()));
        //currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency.name"));
        //currencyColumn.setCellValueFactory(currency ->new ReadOnlyStringWrapper(currency.getValue()
          //      .getCurrency().getName()));
        //priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        //chosenGoodsTable.setItems(movdocdata);
        //productdata.add(proc);
        //movdocdata.add(p);
        goodsInTable.add(new GoodInTable(Double.parseDouble(amountTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                currencyChoiceBox.getValue().toString(),
                goodChoiceBox.getValue().toString()));
        chosenGoodsTable.setItems(goodsInTable);
    }
    @FXML
    public void clicked_cancelButton(ActionEvent actionEvent) throws IOException{

    }

    public void clicked_createButton(ActionEvent actionEvent) {


    }

    private static boolean isGoodInGoodsTable(String good, ObservableList<GoodInTable> goodsInTable){
        for (int i = 0; i < goodsInTable.size(); i++)
            if (good.equals(goodsInTable.get(i).getGood()))
                return true;
        return false;
    }

    public void setContragent(String name, Long contragentId){
        contragentTextField.setText(name);
        this.contragentId = contragentId;
    }


}

package ua.iasa.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.Product;
import ua.iasa.entity.Room;
import ua.iasa.repository.ProductRepository;
import ua.iasa.repository.ReferenceRoomDao;
import ua.iasa.repository.RoomRepository;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class MoveGoodRoomController {

    public static Boolean isShown = false;
    @FXML
    public ComboBox<String> fromRoomList;
    @FXML
    public ComboBox<String> toRoomList;
    @FXML
    public Button MoveGoodButton;
    @FXML
    public ChoiceBox<String> goodChoiceBox;
    @FXML
    public TextField amountTextField;
    @Autowired
    private RoomRepository roomrepo;
    @Autowired
    private ReferenceRoomDao referenceRoomDao;
    @Qualifier("mainView")
    @Autowired
    private View view1;
    //private ObservableList<String> productnamedata;
    //private ObservableList<String> room;
    @Autowired
    private ProductRepository procrepo;
    @Autowired
    private MainMenuController mainMenuController;
   // private ObservableList<ReferenceRoom> rooms;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
        List<Room> rooms = (List<Room>) roomrepo.findAll();
        ObservableList<String> room = FXCollections.observableArrayList(rooms.stream()
                .map(Room::getRoomNumber).distinct().collect(Collectors.toList()));
        fromRoomList.setItems(room);
        toRoomList.setItems(room);
        List<Product> prods = (List<Product>) procrepo.findAll();
        ObservableList<String> productnamedata = FXCollections.observableArrayList(prods.stream()
                .map(Product::getNameType).distinct().collect(Collectors.toList()));
        goodChoiceBox.setItems(productnamedata);
    }

    @FXML
    public void moveGood(ActionEvent actionEvent) {
         try {
        referenceRoomDao.moveProduct(fromRoomList.getValue(),
                toRoomList.getValue(),
                goodChoiceBox.getValue(),
                Double.parseDouble(amountTextField.getText()));
        } catch (Exception e) {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot move product");
          alert.show();
            // e.getMessage();
        }
        ObservableList<ReferenceRoom> rooms =
                FXCollections.observableArrayList(referenceRoomDao.getReferencesOfRoom());
        mainMenuController.setReferenceRoomTable(rooms);
        Stage stage = (Stage) MoveGoodButton.getScene().getWindow();
        stage.setScene(view1.getView().getScene());
        stage.show();
    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }
}

package ua.iasa.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iasa.entity.Room;
import ua.iasa.repository.ReferenceRoomDao;
import ua.iasa.repository.RoomRepository;

import javax.annotation.PostConstruct;
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
    public ChoiceBox goodChoiceBox;
    @FXML
    public TextField amountTextField;
    @Autowired
    private RoomRepository roomrepo;
    @Autowired
    private ReferenceRoomDao referenceRoomDao;

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
    }

    @FXML
    public void moveGood(ActionEvent actionEvent) {
        try {
            referenceRoomDao.MoveProduct(fromRoomList.getValue(),
                    toRoomList.getValue(),
                    goodChoiceBox.getValue().toString(),
                    Double.parseDouble(amountTextField.getText()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot move product");
            alert.show();
        }
    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }
}

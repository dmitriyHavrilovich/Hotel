package ua.iasa.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iasa.entity.NaturalPerson;
import ua.iasa.entity.Product;
import ua.iasa.entity.Room;
import ua.iasa.repository.RoomRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mahaon on 14.05.2017.
 */
@NoArgsConstructor
public class MoveGoodRoomController {
    @FXML
    public ComboBox FromRoomList;
    @FXML
    public ComboBox ToRoomList;
    @FXML
    public Button MoveGoodButton;
    @FXML
    public ChoiceBox goodChoiceBox;
    @FXML
    public TextField amountTextField;
    @Autowired private RoomRepository roomrepo;
    private ObservableList<String> room;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
        List<Room> rooms = (List) roomrepo.findAll();
        room = FXCollections.observableArrayList(rooms.stream()
                .map(Room::getRoomNumber).distinct().collect(Collectors.toList()));
        FromRoomList.setItems(room);
        ToRoomList.setItems(room);
    }

    @FXML
    public void moveGood(ActionEvent actionEvent) {
    }

    public void action_amountTextField(KeyEvent keyEvent) {
    }
}

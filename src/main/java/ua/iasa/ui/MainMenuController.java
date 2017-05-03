package ua.iasa.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;
import org.postgresql.replication.fluent.physical.PhysicalCreateSlotBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.iasa.config.View;
import ua.iasa.entity.MovementDocument;
import ua.iasa.entity.NaturalPerson;
import ua.iasa.entity.User;
import ua.iasa.repository.NaturalPersonRepository;
import ua.iasa.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
public class MainMenuController {
    private ObservableList<NaturalPerson> natpersdata, jurpersdata;

    @FXML public Button addPhysicalButton;
    @Autowired private NaturalPersonRepository natpersrepo;
    @FXML private javafx.scene.control.TextField physicalSurnameTextField;
    @FXML private javafx.scene.control.TextField physicalNameTextField;
    @FXML private javafx.scene.control.TextField physicalFathersNameTextField;
    @FXML private TableView<NaturalPerson> physicalTable;

    @FXML private TableColumn<NaturalPerson, String> physicalSurnameColumn;
    @FXML private TableColumn<NaturalPerson, String> physicalNameColumn;
    @FXML private TableColumn<NaturalPerson, String> physicalFathersNameColumn;
    @Qualifier("mainView")
    @Autowired
    private View view;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {

    }

    @FXML
    public void clicked_addPhysicalButton(ActionEvent actionEvent) {
        Set<MovementDocument> movementDocumentSet = new HashSet<>();
        NaturalPerson pers= new NaturalPerson(null, null, movementDocumentSet
                ,physicalSurnameTextField.getText(),
                physicalNameTextField.getText(),
                physicalFathersNameTextField.getText(), null);
        NaturalPerson p = natpersrepo.save(pers);

    }
    @FXML
    public void clicked_refreshPhysicalTable(ActionEvent actionEvent) {
        //natpersons = FXCollections.observableArrayList();
        //List<NaturalPerson> natpersons = new ArrayList<>();
        List<NaturalPerson> natpersons = (List)natpersrepo.findAll();
        natpersdata = FXCollections.observableArrayList(natpersons);
        physicalTable.setItems(natpersdata);

    }

    public void clicked_choosePhysicalButton(ActionEvent actionEvent) {
    }

    public void clicked_searchLegalButton(ActionEvent actionEvent) {
    }

    public void clicked_searchPhysicalButton(ActionEvent actionEvent) {
    }

    public void clicked_chooseLegalButton(ActionEvent actionEvent) {
    }

    public void clicked_addLegalButton(ActionEvent actionEvent) {
    }

    public void keyTypedCode(KeyEvent keyEvent) {
    }

    public void clicked_refreshLegalTable(ActionEvent actionEvent) {
    }
}
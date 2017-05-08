package ua.iasa.ui.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Mahaon on 07.05.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodInTable {


    private Double amount;
    private Double price ;
    private String currency = null, good = null;
   // private ObservableList<String> goodsList = FXCollections.observableArrayList(),
     //       currencyList = FXCollections.observableArrayList();


}

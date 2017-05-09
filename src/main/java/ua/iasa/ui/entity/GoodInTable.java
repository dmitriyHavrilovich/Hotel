package ua.iasa.ui.entity;

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
    private String currency;
    private String good;



}

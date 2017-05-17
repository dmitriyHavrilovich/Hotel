package ua.iasa.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceRoom {
    private Long id;
    private String room_type;
    private String number;
    private String name_type;
    private Double amount;
    //private Double measure;
    //private Double price;
}

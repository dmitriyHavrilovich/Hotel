package ua.iasa.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceDocument {

    private Long id;
    private String doc_type;
    private String currency;
    private String date;
    private String name;
    private String name_type;
    private Double amount;
    private Double measure;
    private Double price;
}

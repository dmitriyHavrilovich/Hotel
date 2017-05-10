package ua.iasa.ui.entity;

import lombok.Data;

@Data
public class ReferencesDocumentView {
    private Long doc_id;
    private String date;
    private String good;
    private String units;
    private String currency;
    private String documentType;
    private String contragent;
    private String employee;
    private Double amount;
    private Double price;
}

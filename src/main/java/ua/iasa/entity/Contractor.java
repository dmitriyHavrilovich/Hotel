package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@MappedSuperclass
@Table(name = "contractor")
public class Contractor {

    @Id
    private Long id;
    @Column(name = "contr_phone")
    private String phone;
    private Set<Document> document;


}

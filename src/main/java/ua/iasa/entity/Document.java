package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "document")
@Entity
public class Document implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String date;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_doc_id")
    private DocumentType documentType;

}

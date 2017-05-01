package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "type_of_document")
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "doc_type")
    private String type;
}

package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "document")
@MappedSuperclass
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String date;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DocumentType documentType;

}

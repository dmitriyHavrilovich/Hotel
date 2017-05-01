package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "document")
@MappedSuperclass
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    @Temporal(value = TemporalType.DATE)
    private String date;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DocumentType documentType;

}

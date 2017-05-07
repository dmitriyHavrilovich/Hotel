package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name_type")
    private String nameType;
    private String measure;
   // @ManyToOne()
    //@JoinColumn(name="movement_document_id", insertable=false, updatable=false)
    //private MovementDocument movementDocument;
   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name="product_id")
   private Set<MovementDocument> movementDocumentSet;
}

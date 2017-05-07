package ua.iasa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

//@EqualsAndHashCode(callSuper = true, exclude = "products")
@Entity
@Data
@Table(name = "movement_document")
@NoArgsConstructor
public class MovementDocument  {

    public MovementDocument(Long id, Long amount, Double price, Currency currency,
                            Room room,
                            Product product
                            ) {
        this.id = id;
        this.amount = amount;
        this.price = price;
        this.currency = currency;
        this.room = room;
        this.product = product;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long amount;
    @Column
    private Double price;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne()
    @JoinColumn(name="movement_document_id", insertable=false, updatable=false)
    private Product product;
    //@OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name="movement_document_id")
    //private Set<Product> products;
}

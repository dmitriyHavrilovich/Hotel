package ua.iasa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "products")
@Entity
@Data
@Table(name = "movement_document")
@NoArgsConstructor
public class MovementDocument extends Document {

    public MovementDocument(Long id, String date, DocumentType documentType,
                            Long amount, Double price, Currency currency,
                            Room room, Set<Product> products) {
        super(id, date, documentType);
        this.amount = amount;
        this.price = price;
        this.currency = currency;
        this.room = room;
        this.products = products;

    }

    private Long amount;
    private Double price;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "room_id")
    private Room room;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="movement_document_id")
    private Set<Product> products;
}

package ua.iasa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "movement_document")
@NoArgsConstructor
public class MovementDocument extends Document {

    public MovementDocument(Long id, String date, DocumentType documentType,
                            Long amount, Double price, Currency currency,
                            Room room, Product product) {
        super(id, date, documentType);
        this.amount = amount;
        this.price = price;
        this.currency = currency;
        this.room = room;
        this.product = product;

    }

    private Long amount;
    private Double price;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id")
    private Product product;
}

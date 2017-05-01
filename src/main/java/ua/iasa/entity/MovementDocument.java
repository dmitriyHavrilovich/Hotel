package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "movement_document")
@PrimaryKeyJoinColumn(name = "doc_id")
public class MovementDocument extends Document implements Serializable {

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

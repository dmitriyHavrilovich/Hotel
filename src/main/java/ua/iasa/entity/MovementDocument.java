package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class MovementDocument extends Document{

    private Long amount;
    private Double price;
}

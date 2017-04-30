package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Room {

    @Id
    private Long id;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "number")
    private String roomNumber;
    private MovementDocument movementDocument;
}

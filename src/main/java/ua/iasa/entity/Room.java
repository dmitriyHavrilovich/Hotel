package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "number")
    private String roomNumber;
}

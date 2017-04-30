package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Document {

    @Id
    private Long id;

    private String date;
}

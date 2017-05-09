package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String workDays;
    private String beginTime;
    private String endTime;
//TODO One to one relationship ro person on post
}


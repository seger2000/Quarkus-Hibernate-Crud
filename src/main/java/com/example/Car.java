package com.example;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity

public class Car extends PanacheEntity {

    public  String name;

    public String model;

    public int numbermoldel;

        public LocalDateTime datatime;
    @ManyToOne
    @JsonIgnore
    public Deposit deposit;





}

package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Deposit extends PanacheEntity {


    public String name_deposit;

    public String country;

    @OneToMany(mappedBy = "deposit", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<Car> car;





}

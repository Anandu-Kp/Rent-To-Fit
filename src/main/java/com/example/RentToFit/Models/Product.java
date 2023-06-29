package com.example.RentToFit.Models;

import com.example.RentToFit.ENUMS.productCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Enumerated(EnumType.STRING)
    private productCategory category;
    private String brand;
    private  String model;
    private boolean isAvailible;
    private int productCount;
    private int pricePerHour;
    private String imageUrl;

    @ManyToOne
    User user;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    List<Transactions> transactionsList=new ArrayList<>();

}

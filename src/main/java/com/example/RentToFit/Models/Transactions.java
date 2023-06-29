package com.example.RentToFit.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

//We should always store each transaction for future use
//this model is to store each transaction

@Entity
@Table(name = "transatcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private Date date;

    private Date dueDate;

    @ManyToOne
    Product product;

    @ManyToOne
    User user;

}

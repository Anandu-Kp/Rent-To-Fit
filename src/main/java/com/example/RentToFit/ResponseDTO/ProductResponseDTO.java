package com.example.RentToFit.ResponseDTO;


import com.example.RentToFit.ENUMS.productCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProductResponseDTO {

    @Enumerated(EnumType.STRING)
    private productCategory category;
    private String brand;
    private  String model;
    private int totalAmount;
    private String imageUrl;
}

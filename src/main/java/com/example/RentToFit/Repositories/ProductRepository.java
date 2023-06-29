package com.example.RentToFit.Repositories;

import com.example.RentToFit.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "select * from product where category=:s is_availible=true", nativeQuery = true)
    ArrayList<Product> getAvailibleProductList(String s);
}

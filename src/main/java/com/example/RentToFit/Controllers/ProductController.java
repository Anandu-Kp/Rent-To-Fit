package com.example.RentToFit.Controllers;


import com.example.RentToFit.Models.Product;
import com.example.RentToFit.Models.User;
import com.example.RentToFit.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

//    for adding a product
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product)
    {
        String s=productService.addProduct( product);

        if(s=="product already exist , try to update") return new ResponseEntity<>(s, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }
// we should also consider the situation that adding new stocks of an existing product
//    this api is for that
    @PutMapping("/updateProduct")
    public ResponseEntity<String> updateUser(@RequestParam int id,@RequestParam int productCount)
    {
        String s=productService.updateProduct(id,productCount);

        return new ResponseEntity<>(s,HttpStatus.CREATED);
    }
}

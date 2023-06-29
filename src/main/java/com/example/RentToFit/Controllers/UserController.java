package com.example.RentToFit.Controllers;

import com.example.RentToFit.Models.User;
import com.example.RentToFit.ResponseDTO.ProductResponseDTO;
import com.example.RentToFit.ResponseDTO.TransactionResponseDTO;
import com.example.RentToFit.Services.ProductService;
import com.example.RentToFit.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

//    we should add a user first
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user)
    {
        String s=userService.addUser(user);

        if(s=="User Already Exist") return new ResponseEntity<>(s, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

//   this is the api that is responsible for returning a list of products which their category is given by user
//    iam taking the required time in hour format for make it easy
//    here iam also checking the availability of the product
//    also calling the product service for the execution of this API
    @GetMapping("/getProduct")
    public ResponseEntity<ArrayList<ProductResponseDTO>> getProducts(@RequestParam String category,@RequestParam  int requiredHours)
    {

        ArrayList<ProductResponseDTO> productList=productService.getProducts(category,requiredHours);

        return new ResponseEntity<>(productList,HttpStatus.FOUND);
    }

//    it is the API that s used to book a product
//    taking productId ,userId ,Required Hours and number of products required as input
//    And here iam not returning the entire transaction object as output, instead iam returning a transaction response DTO(Data Transfer Object) with required details only
    @PostMapping("/bookProduct")
    public ResponseEntity<TransactionResponseDTO> bookProduct(@RequestParam int productId,@RequestParam int userId,@RequestParam int requiredHours,@RequestParam int count)
    {
        TransactionResponseDTO transactionResponseDTO=  productService.bookProduct(productId,userId,requiredHours,count);

        return  new ResponseEntity<>(transactionResponseDTO,HttpStatus.ACCEPTED);
    }


}

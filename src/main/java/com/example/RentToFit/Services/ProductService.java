package com.example.RentToFit.Services;

import com.example.RentToFit.Models.Product;
import com.example.RentToFit.Models.Transactions;
import com.example.RentToFit.Models.User;
import com.example.RentToFit.Repositories.ProductRepository;
import com.example.RentToFit.Repositories.TransactionRepository;
import com.example.RentToFit.Repositories.UserRepository;
import com.example.RentToFit.ResponseDTO.ProductResponseDTO;
import com.example.RentToFit.ResponseDTO.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

//    used to add a product to the database

    public String addProduct(Product product) {

        if (productRepository.findById(product.getId()).get() != null) return "product already exist , try to update";

        productRepository.save(product);

        return "product Added Successfully";

    }

//used for the case of adding new stocks of an existing product
    public String updateProduct(int id, int productCount) {

        Product product=productRepository.findById(id).get();
        if(product==null) return "Product not Exist, Try to add Product";
        int count=product.getProductCount();
        count+=productCount;
        product.setProductCount(count);
        productRepository.save(product);

        return "Successfully Updated";
    }

//    used to return products that is in the category that user gave
    public ArrayList<ProductResponseDTO> getProducts(String category, int requiredHours) {

//        Because there isn't any methode to get a list of elements after checking some conditions , I used @query annotation to write a sql code in the repository layer
//        it check the category and availability and returns the list of elements which satisfies the conditions

        ArrayList<Product> products=productRepository.getAvailibleProductList(category);
        ArrayList<ProductResponseDTO> productResponseDTOS=new ArrayList<>();
        for(Product p:products)
        {
            ProductResponseDTO productResponseDTO=new ProductResponseDTO();
            productResponseDTO.setBrand(p.getBrand());
            productResponseDTO.setCategory(p.getCategory());
            productResponseDTO.setModel(p.getModel());
            productResponseDTO.setImageUrl(p.getImageUrl());
            int totalAmount=p.getPricePerHour()*requiredHours;
            productResponseDTO.setTotalAmount(totalAmount);
        }
        return productResponseDTOS;
    }


//    Used to book a product
    public TransactionResponseDTO bookProduct(int productId,int userId, int requiredHours, int count) {


//        taking out the Product that user wants to book out
        Product product=productRepository.findById(productId).get();

//        checking the product is available for the given number
        if(product.getProductCount()<count) return null;

//        if its availible we should update the ProductCount(currently available stocks)
        product.setProductCount(product.getProductCount()-count);
        if(product.getProductCount()==0) product.setAvailible(false);

//        craeting a transaction object to stare the transactions
        Transactions transaction=new Transactions();
        transaction.setProduct(product);

//        taking out user the object to add into transaction object(mapping)
        User user=userRepository.findById(userId).get();
        transaction.setUser(user);

//        Since the date is stored as Date type we should convert it to instant object to update the time to find the due date
        Instant instant = transaction.getDate().toInstant();
        Duration duration = Duration.ofHours( requiredHours );
        Instant instantHourLater = instant.plus( duration );

//        converting the instant object back to Date type add into transaction
        transaction.setDueDate(Date.from(instantHourLater));
        transactionRepository.save(transaction);

//Below two paragraphs are used to update transaction object lists in both user and product because of Bidirectional mapping

       List<Transactions> transactions= user.getTransactionsList();
       transactions.add(transaction);
       user.setTransactionsList(transactions);
       List<Product> products= user.getProductList();
       products.add(product);
       user.setProductList(products);
       userRepository.save(user);

      transactions=product.getTransactionsList();
        transactions.add(transaction);
        product.setTransactionsList(transactions);
        product.setUser(user);
        productRepository.save(product);



//Creating the transaction response dto to return
        TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
        transactionResponseDTO.setDate(transaction.getDate());
        transactionResponseDTO.setDueDate(transaction.getDueDate());

        return transactionResponseDTO;
    }
}

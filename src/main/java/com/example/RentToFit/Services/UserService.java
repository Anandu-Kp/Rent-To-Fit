package com.example.RentToFit.Services;

import com.example.RentToFit.Models.User;
import com.example.RentToFit.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

//    to add user
    public String addUser(User user) {

        if(userRepository.findById(user.getId()).get()!= null) return "User Already Exist";

        userRepository.save(user);

        return "Successfully Added The User";
    }
}

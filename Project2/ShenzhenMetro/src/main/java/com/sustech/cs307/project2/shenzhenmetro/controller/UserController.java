package com.sustech.cs307.project2.shenzhenmetro.controller;

import com.sustech.cs307.project2.shenzhenmetro.object.Card;
import com.sustech.cs307.project2.shenzhenmetro.object.Passenger;
import com.sustech.cs307.project2.shenzhenmetro.repository.CardRepository;
import com.sustech.cs307.project2.shenzhenmetro.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping("/{userNum}")
    public String getUserDetails(@PathVariable String userNum, Model model) {
        if (userNum.length() == 9) {
            Card card = cardRepository.findById(userNum).get();
            model.addAttribute("card", card);
            return "users/card";
        } else if (userNum.length() == 18) {
            Passenger passenger = passengerRepository.findById(userNum).get();
            model.addAttribute("passenger", passenger);
            return "users/passenger";
        } else {
            return "error";
        }
    }
}

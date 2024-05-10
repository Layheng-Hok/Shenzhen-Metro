package com.sustech.cs307.project2.ChineseSubwaySystem.controller;

import com.sustech.cs307.project2.ChineseSubwaySystem.model.Card;
import com.sustech.cs307.project2.ChineseSubwaySystem.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/")
    public String getPage() {
        return "Welcome!";
    }

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @GetMapping("/cards/{id}")
    public Card getCardById(@PathVariable String id) {
        return cardRepository.findById(id).orElse(null);
    }

    @PostMapping("/cards/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String createCard(@RequestBody Card card) {
        cardRepository.save(card);
        return "Card created!";
    }

    @PutMapping("/cards/update/{id}")
    public String updateCard(@PathVariable String id, @RequestBody Card card) {
        Card updatedCard = cardRepository.findById(id).get();
        updatedCard.setMoney(card.getMoney());
        updatedCard.setCreateTime(card.getCreateTime());
        cardRepository.save(updatedCard);
        return "Card updated!";
    }

    @DeleteMapping("/cards/delete/{id}")
    public String deleteCard(@PathVariable String id) {
        cardRepository.deleteById(id);
        return "Card deleted!";
    }
}

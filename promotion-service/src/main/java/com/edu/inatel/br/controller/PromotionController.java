package com.edu.inatel.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.inatel.br.model.Promotion;
import com.edu.inatel.br.service.PromotionService;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) throws Exception {
        return ResponseEntity.ok(promotionService.createPromotion(promotion));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Promotion>> getPromotionsByRestaurant(@PathVariable String restaurantId) throws Exception {
        return ResponseEntity.ok(promotionService.getPromotionsByRestaurant(restaurantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable String id, @RequestBody Promotion promotion) throws Exception {
        return ResponseEntity.ok(promotionService.updatePromotion(id, promotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() throws Exception {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }


}
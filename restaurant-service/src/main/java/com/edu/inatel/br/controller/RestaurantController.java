package com.edu.inatel.br.controller;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.inatel.br.model.Restaurant;
import com.edu.inatel.br.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant, Principal principal) throws ExecutionException, InterruptedException {
        String userId = principal.getName();
        restaurant.setUserId(userId);
        
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(createdRestaurant);
    }

    @GetMapping("/my-restaurants")
    public ResponseEntity<List<Restaurant>> getMyRestaurants(Principal principal) throws ExecutionException, InterruptedException {
        String userId = principal.getName();
        List<Restaurant> restaurants = restaurantService.getRestaurantsByUserId(userId);
        return ResponseEntity.ok(restaurants);
    }
}
package com.edu.inatel.br.controller;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.inatel.br.model.Novelty;
import com.edu.inatel.br.service.NoveltyService;

@RestController
@RequestMapping("/novelties")
public class NoveltyController {

    @Autowired
    private NoveltyService noveltyService;

    @GetMapping
    public ResponseEntity<List<Novelty>> getMyNovelties(Principal principal) throws ExecutionException, InterruptedException {
        String userId = principal.getName();
        return ResponseEntity.ok(noveltyService.findByUser(userId));
    }
}
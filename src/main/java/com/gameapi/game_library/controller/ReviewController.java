package com.gameapi.game_library.controller;

import com.gameapi.game_library.domain.Review;
import com.gameapi.game_library.exception.ResourceNotFoundException;
import com.gameapi.game_library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/reviews/{gameId}")
    public ResponseEntity<?> addReviewToGame(@PathVariable Long gameId, @RequestBody Review review){
        reviewService.addReview(gameId, review);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/reviews/{reviewId}/games/{gameId}")
    public ResponseEntity<?> deleteReviewOfGame(@PathVariable Long reviewId, @PathVariable Long gameId){
        reviewService.deleteReview(reviewId, gameId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getEveryReview());
    }

    @GetMapping("reviews/{gameId}/reviews")
    public ResponseEntity<List<Review>> getAllReviewsForGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(reviewService.getAllReviewsOnGame(gameId));
    }

    @GetMapping("reviews/{reviewId}/games/{gameId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId, @PathVariable Long gameId){
        return ResponseEntity.ok(reviewService.getSingleReview(reviewId, gameId));
    }




}

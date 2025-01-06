package com.gameapi.game_library.service;

import com.gameapi.game_library.domain.Game;
import com.gameapi.game_library.domain.Review;
import com.gameapi.game_library.exception.ResourceNotFoundException;
import com.gameapi.game_library.repository.GameRepository;
import com.gameapi.game_library.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    public void addReview(Long gameId, Review review){
        Game game = gameService.getSingleGameById(gameId);
        review.setGame(game);
        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId, Long gameId) {
        Review review = reviewRepository.findByIdAndGameId(reviewId, gameId);
        reviewRepository.delete(review);
    }

    public List<Review> getEveryReview() {
        return reviewRepository.findAll();
    }

    public List<Review> getAllReviewsOnGame(Long gameId){
        List<Review> allGameReviews = new ArrayList<>();
        allGameReviews.addAll(reviewRepository.findByGameId(gameId));
        return allGameReviews;
    }

    public Review getSingleReview(Long reviewId, Long gameId) throws ResourceNotFoundException {
        Review review = reviewRepository.findByIdAndGameId(reviewId, gameId);
        if (review.getReviewId().equals(reviewId)){
            return review;
        }
        throw new ResourceNotFoundException("Review not found.");
    }



}

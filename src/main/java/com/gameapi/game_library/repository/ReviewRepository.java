package com.gameapi.game_library.repository;

import com.gameapi.game_library.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    Review findByIdAndGameId(Long id, Long gameId);

    List<Review> findByGameId(Long gameId);

    List<Review> findAll();
}

package com.gameapi.game_library.repository;

import com.gameapi.game_library.domain.Game;
import com.gameapi.game_library.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    Game findByTitleIgnoreCase(String title);

    List<Game> findByTitleContainingIgnoreCase(String title);

    List<Game> findByYear(Integer year);

    List<Game> findByCategoryContainingIgnoreCase(String category);

    List<Game> findByPublisherContainingIgnoreCase(String publisher);



}

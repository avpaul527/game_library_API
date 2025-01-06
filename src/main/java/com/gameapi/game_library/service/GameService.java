package com.gameapi.game_library.service;


import com.gameapi.game_library.domain.Game;
import com.gameapi.game_library.exception.ResourceNotFoundException;
import com.gameapi.game_library.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;


    public List<Game> showAllGames(){
        List<Game> allGames = new ArrayList<>();

        for (Game game : gameRepository.findAll()) {
            allGames.add(game);
        }
        return allGames;

    }

    public void addGame(Game game) {
        gameRepository.save(game);
    }

    public Game getSingleGameById(Long gameId){
        for (Game game : showAllGames()){
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }
        throw new ResourceNotFoundException("Game with id of " + gameId + "not found.");
    }

    public void editGame(Long gameId, Game game) {
        Game g = getSingleGameById(gameId);
        if(g.getGameId().equals(gameId)){
            g.setTitle(game.getTitle());
            g.setYear(game.getYear());
            g.setCategory(game.getCategory());
            g.setPublisher(game.getPublisher());
            gameRepository.save(g);
        }
    }

    public void deleteGameById(Long gameId){
        gameRepository.deleteById(gameId);
    }

    public Game showGameByTitle(String gameTitle){
        for (Game game : showAllGames()) {
            if(game.getTitle().equalsIgnoreCase(gameTitle)){
                return game;
            }
        }
        throw new ResourceNotFoundException("Game with title: " + gameTitle + " not found");
    }

    public List<Game> searchGames(String title, String category, String publisher, Integer year){

        List<Game> titleSearch = gameRepository.findByTitleContainingIgnoreCase(title);
        List<Game> categorySearch = gameRepository.findByCategoryContainingIgnoreCase(category);
        List<Game> publisherSearch = gameRepository.findByPublisherContainingIgnoreCase(publisher);
        List<Game> yearSearch = gameRepository.findByYear(year);
            if (title != null) {
                return titleSearch;
           } else if (category != null) {
                return categorySearch;
           } else if (publisher != null) {
               return publisherSearch;
           } else if (year != null) {
                return yearSearch;
           }
        return null;
    }



}

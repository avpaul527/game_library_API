package com.gameapi.game_library.controller;

import com.gameapi.game_library.domain.Game;
import com.gameapi.game_library.service.GameService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    private final Logger logger = LoggerFactory.getLogger(GameService.class);


    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames(){
        logger.info("Successfully returned all games.");
        return ResponseEntity.ok(gameService.showAllGames());
    }


    @PostMapping("/games")
    public ResponseEntity<Void> createGame(@Valid @RequestBody Game game){
        gameService.addGame(game);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{gameId}")
                .buildAndExpand(game.getGameId())
                .toUri();
        responseHeaders.setLocation(location);
        logger.info("Created game successfully [title: " + game.getTitle() + "].");
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable Long gameId){
        logger.info("Successful responded with game [id: " + gameId + "].");
        Game game = gameService.getSingleGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/games/{gameId}")
    public ResponseEntity<?> updateGame(@PathVariable Long gameId, @RequestBody Game game) {
        logger.info("Updated game info successfully [id: " + gameId + "].");
        gameService.editGame(gameId, game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGameById(gameId);
        logger.info("Successfully deleted game.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/game")
    public ResponseEntity<?> getGameByTitle(@RequestParam String title) {
        Game game = gameService.showGameByTitle(title);
        logger.info("Responded successfully with game entitled " + title + ".");
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @GetMapping("/games/search")
    public ResponseEntity<?> searchGames(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "category", required = false) String category, @RequestParam(name = "publisher", required = false) String publisher, @RequestParam(name = "year", required = false) Integer year){
        List<Game> gamesFound = gameService.searchGames(title, category, publisher, year);
        logger.info("Responded successfully with games found for search.");
        return new ResponseEntity<>(gamesFound, HttpStatus.OK);
    }


}

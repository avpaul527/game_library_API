package com.gameapi.game_library.controller;


import com.gameapi.game_library.domain.Game;
import com.gameapi.game_library.domain.Player;
import com.gameapi.game_library.exception.ResourceNotFoundException;
import com.gameapi.game_library.service.PlayerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PlayerController {

    private final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    PlayerService playerService;

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(){
        logger.info("Successfully returned all players.");
        return ResponseEntity.ok(playerService.showAllPlayers());
    }

    @PostMapping("/players")
    public ResponseEntity<Void> createPlayer(@Valid @RequestBody Player player){
        playerService.addPlayer(player);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(player.getUserId())
                .toUri();
        responseHeaders.setLocation(location);
        logger.info("Created player successfully [username: " + player.getUsername() + "].");
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/players/{userId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long userId){
        logger.info("Successful responded with player [id: " + userId + "].");
        Player player = playerService.getSinglePlayerById(userId);
        return ResponseEntity.ok(player);
    }

    @PutMapping("/players/{userId}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long userId, @RequestBody Player player) {
        logger.info("Updated player info successfully [id: " + userId + "].");
        playerService.editPlayer(userId, player);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/players/{userId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long userId) {
        playerService.deletePlayerById(userId);
        logger.info("Successfully deleted player.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/player/{username}")
    public ResponseEntity<Player> getPlayerByUserName(@PathVariable String username){
        logger.info("Successful responded with player [username: " + username + "].");
        Player player = playerService.showPlayerByUserName(username);
        return ResponseEntity.ok(player);
    }

    @PutMapping("/players/{userId}/games")
    public ResponseEntity<?> saveGameToPlayerGamesByTitle(@PathVariable Long userId, @RequestBody Game game) throws ResourceNotFoundException {
        playerService.addGameToSavedGamesByTitle(userId, game.getTitle());
        logger.info("Successfully added game [" + game.getTitle() + "] to saved games for user [id: "+ userId + "].");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("players/{userId}/games/remove")
    public ResponseEntity<?> removeGameFromPlayerGamesByTitle(@PathVariable Long userId, @RequestBody Game game) {
        playerService.deleteGameFromSavedGamesByTitle(userId, game.getTitle());
        logger.info("Successfully removed game [" + game.getTitle() + "] to saved games for user [id: "+ userId + "].");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

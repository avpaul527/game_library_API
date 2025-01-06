package com.gameapi.game_library.service;


import com.gameapi.game_library.domain.Game;
import com.gameapi.game_library.domain.Player;
import com.gameapi.game_library.exception.ResourceNotFoundException;
import com.gameapi.game_library.repository.GameRepository;
import com.gameapi.game_library.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    public List<Player> showAllPlayers(){
        List<Player> allPlayers = new ArrayList<>();

        for (Player player : playerRepository.findAll()){
            allPlayers.add(player);
        }
        return allPlayers;
    }

    public void addPlayer(Player player){
        playerRepository.save(player);
    }

    public Player getSinglePlayerById(Long userId){
        for (Player player : showAllPlayers()){
            if (player.getUserId().equals(userId)) {
                return player;
            }
        }
        throw new ResourceNotFoundException("Player with id of " + userId + "not found.");
    }

    public void editPlayer(Long userId, Player player) {
        Player p = getSinglePlayerById(userId);
        if(p.getUserId().equals(userId)){
            p.setUsername(player.getUsername());
            p.setSavedGames(player.getSavedGames());
            playerRepository.save(p);
        }
    }

    public void deletePlayerById(Long userId){
        playerRepository.deleteById(userId);
    }

    public Player showPlayerByUserName(String username){
        for (Player player : showAllPlayers()) {
            if(player.getUsername().equalsIgnoreCase(username)){
                return player;
            }
        }
        throw new ResourceNotFoundException("Player with username: " + username + " not found");
    }

    public void addGameToSavedGamesByTitle (Long userId, String title) throws ResourceNotFoundException{
        Player p = getSinglePlayerById(userId);
        Game addedGame = gameRepository.findByTitleIgnoreCase(title);
        Set<Game> playerGames = p.getSavedGames();
        for (Game game : gameService.showAllGames()) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                playerGames.add(addedGame);
                p.setSavedGames(playerGames);
                playerRepository.save(p);
            }
        }
        throw new ResourceNotFoundException("Game with title [" + title + "] not found.");
    }

    public void deleteGameFromSavedGamesByTitle (Long userId, String title) {
        Player p = getSinglePlayerById(userId);
        Game removedGame = gameRepository.findByTitleIgnoreCase(title);
        Set<Game> playerGames = p.getSavedGames();
        for (Game game : gameService.showAllGames()) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                playerGames.remove(removedGame);
                p.setSavedGames(playerGames);
                playerRepository.save(p);
            }
        }
    }

}

package com.ajit.game.kalah.service;

import com.ajit.game.kalah.domain.Game;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for managing state of the Games
 * Created by Ajit Kumar Giri on 7/26/2019.
 */

@Service
public class GameService {

    //hold all the active Games with game id as key
    Map<String, Game> allActiveGames = new ConcurrentHashMap<>();


    /**
     * Start the Game
     *
     * @return the newly created {@link Game} with all initial values
     */
    public Game createGame() {
        Game game = new Game();
        addToActiveGames(game);
        return game;
    }

    private void addToActiveGames(Game game) {
        if (game != null) {
            allActiveGames.put(game.getId(), game);
        }
    }

    /**
     * Get active game with state by game id
     *
     * @param id id for the Game
     * @return the game with all active state
     */
    public Game getActiveGameById(String id) {
        if (id != null) {
            return allActiveGames.get(id);
        }
        return null;
    }


    public Game play(Game game, int pitNumber) {
        if (game != null && pitNumber != 0) {
            game.movePit(pitNumber);
        }
        return game;
    }

}

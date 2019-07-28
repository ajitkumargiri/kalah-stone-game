package com.ajit.game.kalah.rest.controller;

import com.ajit.game.kalah.domain.Game;
import com.ajit.game.kalah.rest.error.GameError;
import com.ajit.game.kalah.rest.resource.GameResource;
import com.ajit.game.kalah.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for handling kalah Game with {@link com.ajit.game.kalah.rest.resource.GameResource}
 * This is the entry point of the game
 * This contains All the endpoints related to Game
 * <p>
 * Created by Ajit Kumar Giri on 7/26/2019.
 */

@RequestMapping("/games")
@RestController
public class GameController {

    Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private GameService gameService;

    /**
     * construction Injection for GameService
     *
     * @param gameService
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * create a new Game to play with all initial settings
     * REST endpoint POST /games
     *
     * @return the {@link ResponseEntity} with http status code 201 and newly created Game
     * with unique id which will be use for further play
     */
    @PostMapping("")
    public ResponseEntity<GameResource> createGame(HttpServletRequest request) {
        LOGGER.debug("Rest call to create a new Game for playing kalah");
        Game game = gameService.createGame();
        showBoard(game);
        return new ResponseEntity<>(new GameResource(game.getId(), request.getRequestURL().append("/").append(game.getId()).toString()), HttpStatus.CREATED);
    }


    /**
     * @param gameId unique id of the active Game
     * @param pitId  id of the pit selected by a player to move. pits are 1-6 for player1 and 8-13 for player2
     *               and 7 and 14 are Kalah for player1 and player2 respectively
     * @return
     */
    @PutMapping("/{gameId}/pits/{pitId}")
    public ResponseEntity<Object> movePits(HttpServletRequest request, @PathVariable String gameId, @PathVariable int pitId) {
        Game game = gameService.getActiveGameById(gameId);
        if (game == null) {
            return new ResponseEntity<>(new GameError("The requested Game is not found .Please start a new Game"),
                    HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Board of game id {} is :{}", gameId, game.getGameBoard());
        if (!game.isRunning()) {
            return new ResponseEntity<>(new GameError("The Game is already Won by " + game.getWinner().getName()),
                    HttpStatus.BAD_REQUEST);
        }
        if (game.isNotValidMove(pitId)) {
            return new ResponseEntity<>(new GameError("This is not a valid move. Please select from your own pits or a non empty pit ,Please move the pit between " + game.getActivePlayer().getStartingPitNumber() + " to " + game.getActivePlayer().getEndPitNumber()),
                    HttpStatus.BAD_REQUEST);
        }
        game = gameService.play(game, pitId);
        if (!game.isRunning()) {
            if (game.getWinner() != null) {
                return new ResponseEntity<>(new GameResource("Game Over. The WINNER is " + game.getWinner().getName()),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new GameResource("Game Over. There is a tie between both of you"),
                        HttpStatus.OK);
            }

        }
        LOGGER.info("Board after pit {} move: {} ", pitId, game.getGameBoard());
        showBoard(game);
        return new ResponseEntity<>(new GameResource(game.getId(), buildBaseUrl(request).append("/games/").append(gameId).toString(), game.getGameBoard()),
                HttpStatus.OK);
    }

    private StringBuilder buildBaseUrl(HttpServletRequest request) {
        String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/games/"));
        return new StringBuilder(url);
    }

    //log game board in beautiful format
    private void showBoard(Game game) {

        LOGGER.info("\n--------------------------------------------------------\n" +
                        "                   |{} | {} | {} | {} | {} | {}  <---- Player2  \n" +
                        "| Player2 Kalah: {}|                            |{} :Player1 Kalah | \n" +
                        "  Player1 --->      {} | {} | {} | {} | {} | {}|\n" +
                        "---------------------------------------------------------",
                game.getGameBoard().get(13),
                game.getGameBoard().get(12),
                game.getGameBoard().get(11),
                game.getGameBoard().get(10),
                game.getGameBoard().get(9),
                game.getGameBoard().get(8),
                game.getGameBoard().get(14),
                game.getGameBoard().get(7),
                game.getGameBoard().get(1),
                game.getGameBoard().get(2),
                game.getGameBoard().get(3),
                game.getGameBoard().get(4),
                game.getGameBoard().get(5),
                game.getGameBoard().get(6)
        );
    }
}

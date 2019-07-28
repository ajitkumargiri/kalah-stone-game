package com.ajit.game.kalah.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.ajit.game.kalah.config.Constants.NUMBER_OF_STONE;
import static com.ajit.game.kalah.config.Constants.PIT_OPPOSITE_NUMBER_MAP;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_ID;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_KALAH_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_STARTING_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_ID;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_KALAH_PIT_NUMBER;

/**
 * This is the class of kalah Game which has all initial settings for the Game
 * Here we set all pits, kalah and player details
 * To Start the Game we need to initialize this
 * <p>
 * Created by Ajit Kumar Giri on 7/2/2019.
 */

@Getter
@Setter
@ToString()
public class Game {

    private String id;
    private Map<Integer, Integer> gameBoard;
    private Player activePlayer;
    private Player playerOne;
    private Player playerTwo;
    private Player winner;
    private boolean isRunning;

    /**
     * Constructs a new Game with a unique ID.
     */
    public Game() {
        this.id = String.valueOf(Math.abs(new Random().nextInt()));
        this.gameBoard = createBoard();
        this.playerOne = new Player(PLAYER_ONE_ID, "player-one");
        this.playerTwo = new Player(PLAYER_TWO_ID, "player-two");
        this.activePlayer = this.playerOne;
        this.isRunning = true;
    }

    /**
     * Create game board with each pits having initial number of stones to play
     *
     * @return
     */
    private Map<Integer, Integer> createBoard() {
        Map<Integer, Integer> gameBoard = new HashMap<>();
        for (int i = PLAYER_ONE_STARTING_PIT_NUMBER; i <= PLAYER_TWO_KALAH_PIT_NUMBER; i++) {

            if (i == PLAYER_ONE_KALAH_PIT_NUMBER || i == PLAYER_TWO_KALAH_PIT_NUMBER) {
                gameBoard.put(i, 0);
            } else {
                gameBoard.put(i, NUMBER_OF_STONE);
            }
        }
        return gameBoard;
    }

    /**
     * valid Pit number should be belongs to the active player
     * and at least one stone should be present to move.
     *
     * @param pitNumber pitNumber moved by player
     * @return
     */
    public boolean isNotValidMove(int pitNumber) {
        return !isOwnPit(pitNumber) || isPitEmpty(pitNumber);
    }

    /**
     * Player move a valid pit to play.
     * It validate and execute all game rules and update the game board state.
     *
     * @param pitNumber
     */
    public void movePit(int pitNumber) {
        if (isNotValidMove(pitNumber)) {
            return;
        }
        //getting all available stones from pit
        int availableStonesInPits = gameBoard.get(pitNumber);
        setPitToZero(pitNumber);
        int currentPitNumberToAdd = pitNumber;
        for (int i = availableStonesInPits; i > 0; i--) {
            currentPitNumberToAdd = getNextPitNumber(currentPitNumberToAdd);
            addStoneToPit(currentPitNumberToAdd);
        }

        //After all iteration currentPitNumberToAdd is equal to last pit number where active player drop his/her last stone
        if (isOwnPit(currentPitNumberToAdd) && gameBoard.get(currentPitNumberToAdd).equals(1)) {
            collectStonesToKalah(getOppositePitNumber(currentPitNumberToAdd), activePlayer.getKalahPitNumber());
        }

        //Check if game is over(once any of the player's all pits are empty)
        if (isAllPitsEmpty(playerOne) || isAllPitsEmpty(playerTwo)) {
            setRunning(false);
            if (isAllPitsEmpty(playerOne)) {
                collectAllPitStonesToKalah(playerTwo);
            }
            if (isAllPitsEmpty(playerTwo)) {
                collectAllPitStonesToKalah(playerOne);
            }
            findWinner();
        }

        //rotate turn to next player
        if (currentPitNumberToAdd != activePlayer.getKalahPitNumber() && isRunning()) {
            rotateTurn();
        }

    }

    private void setPitToZero(int pitNumber) {
        gameBoard.put(pitNumber, 0);
    }

    private void addStoneToPit(int pitNumber) {
        if (pitNumber != activePlayer.getOpponentKalahPitNumber()) {
            gameBoard.put(pitNumber, gameBoard.get(pitNumber) + 1);
        }

    }

    private boolean isPitEmpty(int pitNumber) {
        return gameBoard.get(pitNumber) == 0;
    }

    private boolean isOwnPit(int pitNumber) {
        if (pitNumber >= this.activePlayer.getStartingPitNumber() && pitNumber <= this.activePlayer.getEndPitNumber()) {
            return true;
        }
        return false;
    }

    private int getNextPitNumber(int pitNumber) {
        if (pitNumber == PLAYER_TWO_KALAH_PIT_NUMBER) {
            return PLAYER_ONE_STARTING_PIT_NUMBER;
        } else {
            return pitNumber + 1;
        }

    }

    private int getOppositePitNumber(int pitNumber) {
        return PIT_OPPOSITE_NUMBER_MAP.get(pitNumber);

    }

    /**
     * Once game is over all the stones need to be collected to kalah for corresponding player
     *
     * @param pitNumber   stones from pitNumber need to collect
     * @param kalahNumber kalah number of the player
     */
    private void collectStonesToKalah(int pitNumber, int kalahNumber) {
        gameBoard.put(kalahNumber, gameBoard.get(kalahNumber) + gameBoard.get(pitNumber));
        //set pit to zero once stones are collected
        gameBoard.put(pitNumber, 0);

    }

    /**
     * checking if game is over, If all pits are empty for a player
     *
     * @param player if all pits are empty for the player
     * @return
     */
    private boolean isAllPitsEmpty(Player player) {
        for (int pitNumber = player.getStartingPitNumber(); pitNumber <= player.getEndPitNumber(); pitNumber++) {
            if (!isPitEmpty(pitNumber)) {
                return false;
            }
        }
        return true;
    }

    private void collectAllPitStonesToKalah(Player player) {
        for (int pitNumber = player.getStartingPitNumber(); pitNumber <= player.getEndPitNumber(); pitNumber++) {
            if (!isPitEmpty(pitNumber)) {
                collectStonesToKalah(pitNumber, player.getKalahPitNumber());
            }
        }
    }

    private void findWinner() {
        if (gameBoard.get(PLAYER_ONE_KALAH_PIT_NUMBER) > gameBoard.get(PLAYER_TWO_KALAH_PIT_NUMBER)) {
            setWinner(playerOne);
        } else if (gameBoard.get(PLAYER_ONE_KALAH_PIT_NUMBER) < gameBoard.get(PLAYER_TWO_KALAH_PIT_NUMBER)) {
            setWinner(playerTwo);
        }
    }

    private void rotateTurn() {
        if (activePlayer.getId() == PLAYER_ONE_ID) {
            activePlayer = playerTwo;
        } else {
            activePlayer = playerOne;
        }
    }
}

package com.ajit.game.kalah.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_END_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_ID;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_KALAH_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_STARTING_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_END_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_ID;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_KALAH_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_STARTING_PIT_NUMBER;

/**
 * Player for Game
 * Created by Ajit Kumar Giri on 7/26/2019.
 */

@Getter
@Setter
@ToString()
public class Player {

    //id of the player
    private int id;

    //name of the player
    private String name;

    //starting pit number of the player,  playerOne-1 and playerTwo-8
    private int startingPitNumber;

    //ending pit number of the player,  playerOne-6 and playerTwo-13
    private int endPitNumber;

    //kalah pit number of the player, playerOne-7 and playerTwo-14
    private int kalahPitNumber;

    //kalah pit number of the opponent player,  playerOne-14 and playerTwo-7
    private int opponentKalahPitNumber;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        if (this.id == PLAYER_ONE_ID) {
            this.startingPitNumber = PLAYER_ONE_STARTING_PIT_NUMBER;
            this.endPitNumber = PLAYER_ONE_END_PIT_NUMBER;
            this.kalahPitNumber = PLAYER_ONE_KALAH_PIT_NUMBER;
            this.opponentKalahPitNumber = PLAYER_TWO_KALAH_PIT_NUMBER;
        }
        if (this.id == PLAYER_TWO_ID) {
            this.startingPitNumber = PLAYER_TWO_STARTING_PIT_NUMBER;
            this.endPitNumber = PLAYER_TWO_END_PIT_NUMBER;
            this.kalahPitNumber = PLAYER_TWO_KALAH_PIT_NUMBER;
            this.opponentKalahPitNumber = PLAYER_ONE_KALAH_PIT_NUMBER;
        }

    }
}

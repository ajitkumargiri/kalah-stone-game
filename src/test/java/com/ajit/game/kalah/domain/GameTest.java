package com.ajit.game.kalah.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ajit.game.kalah.config.Constants.NUMBER_OF_STONE;
import static com.ajit.game.kalah.config.Constants.PIT_OPPOSITE_NUMBER_MAP;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_ID;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_KALAH_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_ONE_STARTING_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_ID;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_KALAH_PIT_NUMBER;
import static com.ajit.game.kalah.config.Constants.PLAYER_TWO_STARTING_PIT_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link Game}
 * Created by Ajit Kumar Giri on 7/28/2019.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTest {

    private Game game;

    @Before
    public void init() {
        game = new Game();
    }


    @Test
    public void testConstructor() {
        assertThat(game.getId()).isNotBlank();
        assertThat(game.isRunning()).isTrue();
        assertThat(game.getWinner()).isNull();
        assertThat(game.getActivePlayer().getId()).isEqualTo(PLAYER_ONE_ID);
        assertThat(game.getPlayerOne().getId()).isEqualTo(PLAYER_ONE_ID);
        assertThat(game.getPlayerOne().getOpponentKalahPitNumber()).isEqualTo(PLAYER_TWO_KALAH_PIT_NUMBER);
        assertThat(game.getPlayerTwo().getId()).isEqualTo(PLAYER_TWO_ID);
        assertThat(game.getPlayerTwo().getOpponentKalahPitNumber()).isEqualTo(PLAYER_ONE_KALAH_PIT_NUMBER);
        assertThat(game.getGameBoard().get(PLAYER_ONE_STARTING_PIT_NUMBER)).isEqualTo(NUMBER_OF_STONE);
        assertThat(game.getGameBoard().get(PLAYER_ONE_KALAH_PIT_NUMBER)).isEqualTo(0);
        assertThat(game.getGameBoard().get(PLAYER_TWO_STARTING_PIT_NUMBER)).isEqualTo(NUMBER_OF_STONE);
        assertThat(game.getGameBoard().get(PLAYER_TWO_KALAH_PIT_NUMBER)).isEqualTo(0);
    }


    @Test
    public void moveInvalidOrEmptyPit() {
        game.movePit(8);
        assertThat(game.getGameBoard().get(8)).isEqualTo(NUMBER_OF_STONE);
        game.movePit(0);
        assertThat(game.getGameBoard().get(1)).isEqualTo(NUMBER_OF_STONE);
        game.movePit(14);
        assertThat(game.getGameBoard().get(14)).isEqualTo(0);
        game.movePit(7);
        assertThat(game.getGameBoard().get(7)).isEqualTo(0);
    }

    @Test
    public void movePitWithOneMoreTurnPlayerOne() {
        game.movePit(1);
        assertThat(game.getGameBoard().get(1)).isEqualTo(0);
        assertThat(game.getGameBoard().get(2)).isEqualTo(NUMBER_OF_STONE + 1);
        assertThat(game.getGameBoard().get(PLAYER_ONE_KALAH_PIT_NUMBER)).isEqualTo(1);
        assertThat(game.getActivePlayer().getId()).isEqualTo(PLAYER_ONE_ID);
    }

    @Test
    public void movePitRotateTurnPlayerOne() {
        game.movePit(2);
        assertThat(game.getGameBoard().get(1)).isEqualTo(NUMBER_OF_STONE);
        assertThat(game.getGameBoard().get(2)).isEqualTo(0);
        assertThat(game.getGameBoard().get(3)).isEqualTo(NUMBER_OF_STONE + 1);
        assertThat(game.getActivePlayer().getId()).isEqualTo(PLAYER_TWO_ID);
    }

    @Test
    public void movePitWithOneMoreTurnPlayerTwo() {
        game.setActivePlayer(game.getPlayerTwo());
        game.movePit(8);
        assertThat(game.getGameBoard().get(8)).isEqualTo(0);
        assertThat(game.getGameBoard().get(9)).isEqualTo(NUMBER_OF_STONE + 1);
        assertThat(game.getGameBoard().get(PLAYER_TWO_KALAH_PIT_NUMBER)).isEqualTo(1);
        assertThat(game.getActivePlayer().getId()).isEqualTo(PLAYER_TWO_ID);
    }

    @Test
    public void movePitRotateTurnPlayerTwo() {
        game.setActivePlayer(game.getPlayerTwo());
        game.movePit(9);
        assertThat(game.getGameBoard().get(8)).isEqualTo(NUMBER_OF_STONE);
        assertThat(game.getGameBoard().get(9)).isEqualTo(0);
        assertThat(game.getGameBoard().get(10)).isEqualTo(NUMBER_OF_STONE + 1);
        assertThat(game.getActivePlayer().getId()).isEqualTo(PLAYER_ONE_ID);
    }

    @Test
    public void movePitLastStoneInOwnEmptyPitPlayerOne() {
        game.getGameBoard().put(1, 3);
        game.getGameBoard().put(4, 0);
        game.getGameBoard().put(game.getActivePlayer().getKalahPitNumber(), 5);
        game.getGameBoard().put(PIT_OPPOSITE_NUMBER_MAP.get(4), 5);

        //If move pit number 1 then the last stone will be in 4 pit
        game.movePit(1);
        assertThat(game.getGameBoard().get(game.getPlayerOne().getKalahPitNumber())).isEqualTo(10);
    }

    @Test
    public void movePitLastStoneInOwnEmptyPitPlayerTwo() {
        game.setActivePlayer(game.getPlayerTwo());
        game.getGameBoard().put(8, 3);
        game.getGameBoard().put(11, 0);
        game.getGameBoard().put(game.getActivePlayer().getKalahPitNumber(), 5);
        game.getGameBoard().put(PIT_OPPOSITE_NUMBER_MAP.get(11), 5);

        //If move pit number 8 then the last stone will be in 11 pit
        game.movePit(8);
        assertThat(game.getGameBoard().get(game.getPlayerTwo().getKalahPitNumber())).isEqualTo(10);
    }

    @Test
    public void movePitAllPitsEmptyPlayerOne() {
        makePlayerOneAllPitsEmpty();
        game.getGameBoard().put(6, 1);

        game.movePit(6);
        assertThat(game.getGameBoard().get(game.getPlayerTwo().getKalahPitNumber())).isEqualTo(36);
        assertThat(game.getWinner().getId()).isEqualTo(game.getPlayerTwo().getId());
        assertThat(game.isRunning()).isFalse();
    }

    @Test
    public void movePitAllPitsEmptyPlayerTwo() {
        makePlayerTwoAllPitsEmpty();
        game.setActivePlayer(game.getPlayerTwo());
        game.getGameBoard().put(13, 1);

        game.movePit(13);
        assertThat(game.getGameBoard().get(game.getPlayerOne().getKalahPitNumber())).isEqualTo(36);
        assertThat(game.getWinner().getId()).isEqualTo(game.getPlayerOne().getId());
        assertThat(game.isRunning()).isFalse();
    }


    @Test
    public void movePitWonPlayerOne() {
        makePlayerOneAllPitsEmpty();
        game.getGameBoard().put(6, 1);
        game.getGameBoard().put(game.getPlayerOne().getKalahPitNumber(), 40);

        game.movePit(6);
        assertThat(game.getWinner().getId()).isEqualTo(game.getPlayerOne().getId());
        assertThat(game.isRunning()).isFalse();
    }

    @Test
    public void movePitWonPlayerTwo() {
        game.setActivePlayer(game.getPlayerTwo());
        makePlayerTwoAllPitsEmpty();
        game.getGameBoard().put(13, 1);
        game.getGameBoard().put(game.getPlayerTwo().getKalahPitNumber(), 40);
        game.movePit(13);
        assertThat(game.getWinner().getId()).isEqualTo(game.getPlayerTwo().getId());
        assertThat(game.isRunning()).isFalse();
    }


    @Test
    public void movePitGameTie() {
        makePlayerOneAllPitsEmpty();
        game.getGameBoard().put(6, 1);
        game.getGameBoard().put(game.getPlayerOne().getKalahPitNumber(), 35);
        game.movePit(6);
        assertThat(game.getGameBoard().get(game.getPlayerTwo().getKalahPitNumber())).isEqualTo(36);
        assertThat(game.isRunning()).isFalse();
        assertThat(game.getWinner()).isNull();
    }

    private void makePlayerOneAllPitsEmpty() {
        game.getGameBoard().put(1, 0);
        game.getGameBoard().put(2, 0);
        game.getGameBoard().put(3, 0);
        game.getGameBoard().put(4, 0);
        game.getGameBoard().put(5, 0);
        game.getGameBoard().put(6, 0);
    }

    private void makePlayerTwoAllPitsEmpty() {
        game.getGameBoard().put(8, 0);
        game.getGameBoard().put(9, 0);
        game.getGameBoard().put(10, 0);
        game.getGameBoard().put(11, 0);
        game.getGameBoard().put(12, 0);
        game.getGameBoard().put(13, 0);
    }

}

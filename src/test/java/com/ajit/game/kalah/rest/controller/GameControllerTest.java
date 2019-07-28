package com.ajit.game.kalah.rest.controller;

import com.ajit.game.kalah.domain.Game;
import com.ajit.game.kalah.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test for {@link GameController}
 * <p>
 * Created by Ajit Kumar Giri on 7/27/2019.
 */

@SpringBootTest
public class GameControllerTest {

    MockMvc mockMvc;

    @Mock
    private GameService mockGameService;
    @InjectMocks
    private GameController gameController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(gameController)
                .build();
    }

    @Test
    public void createGame() throws Exception {
        when(mockGameService.createGame()).thenReturn(new Game());
        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.url").value(notNullValue()));
    }

    @Test
    public void gameNotFound() throws Exception {
        when(mockGameService.getActiveGameById(any())).thenReturn(null);
        mockMvc.perform(put("/games/1234/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The requested Game is not found .Please start a new Game"));
    }

    @Test
    public void gameAlreadyCompleted() throws Exception {
        Game completedGame = new Game();
        completedGame.setRunning(false);
        completedGame.setWinner(completedGame.getPlayerOne());
        when(mockGameService.getActiveGameById(anyString())).thenReturn(completedGame);
        mockMvc.perform(put("/games/1234/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The Game is already Won by player-one"));
    }

    @Test
    public void invalidPitMoveForPlayerOne() throws Exception {
        when(mockGameService.getActiveGameById(anyString())).thenReturn(new Game());
        mockMvc.perform(put("/games/1234/pits/8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("This is not a valid move. Please select from your own pits or a non empty pit ,Please move the pit between 1 to 6"));
    }


    @Test
    public void invalidPitMoveForPlayerTwo() throws Exception {
        Game playerTwoGame = new Game();
        playerTwoGame.setActivePlayer(playerTwoGame.getPlayerTwo());
        when(mockGameService.getActiveGameById(anyString())).thenReturn(playerTwoGame);
        mockMvc.perform(put("/games/1234/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("This is not a valid move. Please select from your own pits or a non empty pit ,Please move the pit between 8 to 13"));
    }

    @Test
    public void gameOver() throws Exception {
        when(mockGameService.getActiveGameById(anyString())).thenReturn(new Game());
        Game completedGame = new Game();
        completedGame.setRunning(false);
        completedGame.setWinner(completedGame.getPlayerOne());
        when(mockGameService.play(any(), anyInt())).thenReturn(completedGame);
        mockMvc.perform(put("/games/1234/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Game Over. The WINNER is player-one"));
    }

    @Test
    public void gameOverWithTie() throws Exception {
        when(mockGameService.getActiveGameById(anyString())).thenReturn(new Game());
        Game completedGame = new Game();
        completedGame.setRunning(false);
        completedGame.setWinner(null);
        when(mockGameService.play(any(), anyInt())).thenReturn(completedGame);
        mockMvc.perform(put("/games/1234/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Game Over. There is a tie between both of you"));
    }

    @Test
    public void validPitMovePlayerOne() throws Exception {
        Game game = new Game();
        game.setId("1234");
        when(mockGameService.getActiveGameById(anyString())).thenReturn(game);
        Game nextTurnGame = new Game();
        nextTurnGame.setId("1234");
        nextTurnGame.setActivePlayer(nextTurnGame.getPlayerTwo());
        when(mockGameService.play(any(), anyInt())).thenReturn(nextTurnGame);
        mockMvc.perform(put("/games/1234/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1234"))
                .andExpect(jsonPath("$.status").value(notNullValue()));
    }

    @Test
    public void validPitMovePlayerTwo() throws Exception {
        Game game = new Game();
        game.setId("1234");
        game.setActivePlayer(game.getPlayerTwo());
        when(mockGameService.getActiveGameById(anyString())).thenReturn(game);
        Game nextTurnGame = new Game();
        nextTurnGame.setId("1234");
        nextTurnGame.setActivePlayer(nextTurnGame.getPlayerOne());
        when(mockGameService.play(any(), anyInt())).thenReturn(nextTurnGame);
        mockMvc.perform(put("/games/1234/pits/8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1234"))
                .andExpect(jsonPath("$.status").value(notNullValue()));
    }
}

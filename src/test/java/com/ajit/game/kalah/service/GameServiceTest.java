package com.ajit.game.kalah.service;

import com.ajit.game.kalah.domain.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link GameService}
 * Created by Ajit Kumar Giri on 7/28/2019.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    public void createGame() {
        Game game = gameService.createGame();
        assertThat(game.getId()).isNotEqualTo(0);
    }

    @Test
    public void getActiveGameById() {
        Game game = gameService.createGame();
        Game activeGame = gameService.getActiveGameById(game.getId());
        assertThat(activeGame.getId()).isEqualTo(game.getId());
    }

    @Test
    public void getActiveNullGameById() {
        Game game = gameService.createGame();
        Game activeGame = gameService.getActiveGameById(null);
        assertThat(activeGame).isNull();
    }
}

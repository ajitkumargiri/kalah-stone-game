package com.ajit.game.kalah.rest.error;

import lombok.Getter;
import lombok.Setter;

/**
 * Error response layout for Game
 *
 * Created by Ajit Kumar Giri on 7/27/2019.
 */

@Getter
@Setter
public class GameError {

    private String message;

    public GameError(String message) {
        super();
        this.message = message;
    }
}

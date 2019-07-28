package com.ajit.game.kalah.rest.resource;

import com.ajit.game.kalah.domain.Game;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Request and response representation of REST api
 * <p>
 * Created by Ajit Kumar Giri on 7/26/2019.
 */

@Getter
@Setter
@ToString()
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResource {

    private String id;
    private String url;
    private String message;
    private Map<Integer, Integer> status;


    /**
     * Constructs a new GameResource.
     */
    public GameResource() {
    }

    /**
     * @param id  the id of the newly created {@link com.ajit.game.kalah.domain.Game}
     * @param url the endpoint of the games with id
     */
    public GameResource(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public GameResource(String message) {
        this.message = message;
    }

    /**
     * Create {@link GameResource} using Game details
     *
     * @param id     id  the id of the newly created {@link com.ajit.game.kalah.domain.Game}
     * @param url    url is the path to active {@link Game} with id
     * @param status status , a json object key-value, where key is the pit number and value is the number of stones in the pit
     */
    public GameResource(String id, String url, Map<Integer, Integer> status) {
        this.id = id;
        this.url = url;
        this.status = status;
    }
}



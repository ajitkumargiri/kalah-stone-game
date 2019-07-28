package com.ajit.game.kalah.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ajit Kumar Giri on 7/26/2019.
 */
public class Constants {

    /**
     * number of stones per pit(6 stones game)
     * change NUMBER_OF_STONE = 4 to make 4 stones game
     */
    public static final int NUMBER_OF_STONE = 6;
    public static final int PLAYER_ONE_ID = 1;
    public static final int PLAYER_TWO_ID = 2;


    //Game board constants
    //pit number is starting from 1 to 14
    public static final int PLAYER_ONE_STARTING_PIT_NUMBER = 1;
    public static final int PLAYER_ONE_END_PIT_NUMBER = 6;
    public static final int PLAYER_ONE_KALAH_PIT_NUMBER = 7;

    public static final int PLAYER_TWO_STARTING_PIT_NUMBER = 8;
    public static final int PLAYER_TWO_END_PIT_NUMBER = 13;
    public static final int PLAYER_TWO_KALAH_PIT_NUMBER = 14;

    public static final Map<Integer, Integer> PIT_OPPOSITE_NUMBER_MAP = new HashMap<Integer, Integer>();

    static {
        PIT_OPPOSITE_NUMBER_MAP.put(1, 13);
        PIT_OPPOSITE_NUMBER_MAP.put(2, 12);
        PIT_OPPOSITE_NUMBER_MAP.put(3, 11);
        PIT_OPPOSITE_NUMBER_MAP.put(4, 10);
        PIT_OPPOSITE_NUMBER_MAP.put(5, 9);
        PIT_OPPOSITE_NUMBER_MAP.put(6, 8);
        PIT_OPPOSITE_NUMBER_MAP.put(8, 6);
        PIT_OPPOSITE_NUMBER_MAP.put(9, 5);
        PIT_OPPOSITE_NUMBER_MAP.put(10, 4);
        PIT_OPPOSITE_NUMBER_MAP.put(11, 3);
        PIT_OPPOSITE_NUMBER_MAP.put(12, 2);
        PIT_OPPOSITE_NUMBER_MAP.put(13, 1);
    }


}

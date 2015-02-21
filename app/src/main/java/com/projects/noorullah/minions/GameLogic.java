package com.projects.noorullah.minions;

import android.content.Context;
import android.content.SharedPreferences;
import android.R.integer;

/**
 * Created by Nate on 20/02/2015.
 */
public class GameLogic extends MinionActiviy{

    private int gameBoard[][];
    private Context context;
    private int numOfMinions;
    private int NUM_OF_ROWS;
    private int NUM_OF_COLS;

    public GameLogic(Context context) {

        this.context = context;

        SharedPreferences optionStorage = context.getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);

        int boardOption;
        int minionsOption;

        boardOption = optionStorage.getInt(RADIO_GROUP_KEY1, 0);
        minionsOption = optionStorage.getInt(RADIO_GROUP_KEY2, 0);

        // Set up game board
        if (boardOption == 0) {
            NUM_OF_ROWS = 3;
            NUM_OF_COLS = 4;
        } else if (boardOption == 1) {
            NUM_OF_ROWS = 4;
            NUM_OF_COLS = 6;
        } else if (boardOption == 2) {
            NUM_OF_ROWS = 8;
            NUM_OF_COLS = 12;
        }

        // set up number of minions
        if (minionsOption == 0) {
            numOfMinions = 6;
        } else if (minionsOption == 1) {
            numOfMinions = 10;
        } else if (minionsOption == 2) {
            numOfMinions = 15;

        } else if (minionsOption == 3) {
            numOfMinions = 20;
        }

    }

    public int decrementMinions(){
        numOfMinions--;
        return numOfMinions;
    }

    public int[][] getGameBoard(){
        return gameBoard;
    }

    public int getRowGameBoard(){
        return NUM_OF_ROWS;
    }

    public int getColGameBoard(){
        return NUM_OF_COLS;
    }

    public int getMinions(){
        return numOfMinions;
    }
}





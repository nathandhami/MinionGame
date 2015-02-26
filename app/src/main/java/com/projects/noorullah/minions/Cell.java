package com.projects.noorullah.minions;

import android.content.Context;
import android.content.SharedPreferences;
import android.R.integer;

import java.util.Random;

/**
 * Created by Nate on 20/02/2015.
 */
public class Cell extends MinionActiviy{

    private int gameBoard[][];
    private Context context;
    private int numOfMinions;
    private int NUM_OF_ROWS;
    int playerX;
    int playerY;
    private int NUM_OF_COLS;
    int map[][];

    public Cell(Context context) {

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

        map = new int[NUM_OF_ROWS][NUM_OF_COLS];
        for(int i =0; i < NUM_OF_ROWS; i++){
            for(int j=0; j< NUM_OF_COLS; j++){
                map[i][j] = -1;
            }
        }
    }

    public void moveUser(int location[][],int x,int y){

        location[playerX][playerY] = -1;
        location[playerX+1][playerY] = 1;

    }

    public int getUserX(){
        return playerX;
    }

    public int getUserY(){
        return playerY;
    }

    public void assignDefault(int location[][],int x,int y){
        map[x][y] = -1;
    }

    public void assignUser(int location[][]){
        Random generate = new Random();
        int startRow = generate.nextInt(NUM_OF_ROWS);
        int startCol = generate.nextInt(NUM_OF_COLS);
        playerX = startRow;
        playerY = startCol;
        map[startRow][startCol] = 1;
    }


    public void assignMinions(int location[][]){
        Random generate = new Random();
        int startRow = generate.nextInt(NUM_OF_ROWS);
        int startCol = generate.nextInt(NUM_OF_COLS);

        while(occupiedPosition(startRow,startCol) == true){
             startRow = generate.nextInt(NUM_OF_ROWS);
             startCol = generate.nextInt(NUM_OF_COLS);
        }

        map[startRow][startCol] = 0;// 0 for minion // 1 for user // -1 unocupied


    }

    public boolean playerExists(int x, int y){
        if(map[x][y] == 1){
            return true;
        }
        return false;
    }

    public boolean occupiedPosition(int x,int y){
        if(map[x][y] != -1){
            return true;
        }
        return false;
    }

    public int decrementMinions(){
        numOfMinions--;
        return numOfMinions;
    }

    public int[][] getMap(){
        return map;
    }

    public int getNumOfRows(){
        return NUM_OF_ROWS;
    }

    public int getNumOfColumns(){
        return NUM_OF_COLS;
    }

    public int getMinions(){
        return numOfMinions;
    }
}





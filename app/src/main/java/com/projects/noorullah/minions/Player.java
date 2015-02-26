package com.projects.noorullah.minions;

/**
 * Created by Nate on 25/02/2015.
 */
public class Player {

    int location[][];

    public Player(Cell mapTracker){
        int numOfRows = mapTracker.getNumOfRows();
        int numOfColumns = mapTracker.getNumOfColumns();

        location = new int[numOfRows][numOfColumns];

    }

    public int [][] getLocation(){
        return location;
    }

    public boolean doesPlayerExistHere(int x,int y){
        if(location[x][y] == 1){
            return true;
        }

        return false;
    }
}

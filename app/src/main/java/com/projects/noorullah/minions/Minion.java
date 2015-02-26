package com.projects.noorullah.minions;

/**
 * Created by Nate on 25/02/2015.
 */
public class Minion {

    int location[][];

    public Minion(Cell mapTracker){

        int numOfRows = mapTracker.getNumOfRows();
        int numOfColumns = mapTracker.getNumOfColumns();
        location = new int[numOfRows][numOfColumns];
    }

    public int[][] getLocation(){
        return location;
    }

    // 0 for minion
    // 1 for user
    // -1 for unoccupied
    public boolean whichMinionExist(int x,int y){

        if(this.location[x][y] == 0){
            return true;
        }
        return false;
    }

}

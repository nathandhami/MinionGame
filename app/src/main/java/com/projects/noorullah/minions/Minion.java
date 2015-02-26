package com.projects.noorullah.minions;

/**
 * Created by Nate on 25/02/2015.
 */
public class Minion {

    private int location[][];
    private int numOfRows;
    private int numOfColumns;
    private int x;
    private int y;

    public Minion(Cell mapTracker){
         numOfRows = mapTracker.getNumOfRows();
         numOfColumns = mapTracker.getNumOfColumns();
         location = new int[numOfRows][numOfColumns];

        for(int i=0; i < numOfRows;i++){
            for(int j=0; j< numOfColumns;j++){
                location[i][j] = -1;
            }
        }


    }

    public int[][] getLocation(){
        return location;
    }


    public void getCoordinates(){
        for(int i=0; i < numOfRows;i++){
            for(int j =0; j< numOfColumns;j++){
                if(location[i][j] == 0){
                    x = i;
                    y = j;
                }
            }
        }
    }

    public int getMinionXCoordinate(){
        return x;
    }

    public int getMinionYCoordinate(){
        return y;
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

    public void updateLocation(int x, int y){
        location[getMinionXCoordinate()][getMinionYCoordinate()] = -1;
        location[x][y] = 0;
        this.x = x;
        this.y = y;
    }

    public void ImmobilizeMinions(int x, int y){

        location[x][y] = -1;

    }

}

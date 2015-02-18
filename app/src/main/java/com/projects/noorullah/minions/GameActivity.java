package com.projects.noorullah.minions;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class GameActivity extends MinionActiviy {

    private int row;
    private int col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        SharedPreferences optionStorage = getSharedPreferences(GAME_PREFERENCES,MODE_PRIVATE);

        int boardOption;
        int minionsOption;
        int numOfMinionsLeft = 0;

        boardOption = optionStorage.getInt(RADIO_GROUP_KEY1,0);
        minionsOption= optionStorage.getInt(RADIO_GROUP_KEY2,0);

        if(boardOption == 0){
            row = 3;
            col = 4;
        }

        else if(boardOption ==1){
            row = 4;
            col = 6;
        }
        else if(boardOption ==2){
            row =8;
            col = 12;
        }

        if(minionsOption ==0 ){
            numOfMinionsLeft = 6;
        }
        else if(minionsOption==1){
            numOfMinionsLeft = 10;
        }
        else if(minionsOption==2){
            numOfMinionsLeft =15;

        }
        else if(minionsOption == 3){
            numOfMinionsLeft = 20;
        }

        String minionRemaining = (String)getResources().getText(R.string.minionsRemain) + " " + numOfMinionsLeft;
        TextView textview = (TextView) findViewById(R.id.txt_remain);
        textview.setText(minionRemaining);


        // Dynamically allocate grid buttons here
        populateButtons();


        // Store Games played
        SharedPreferences settings = getSharedPreferences(GAME_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        int numOfGamesPlayed = settings.getInt("GAMES_PLAYED",0);
        displayGamesPlayed(numOfGamesPlayed);
        numOfGamesPlayed++;
        editor.putInt("GAMES_PLAYED",numOfGamesPlayed);
        editor.commit();




    }

    private void displayGamesPlayed(int numOfPlayed) {
        String displayNumOfPlayed = (String)getResources().getText(R.string.gamesplayed) + numOfPlayed;
        TextView textview = (TextView) findViewById(R.id.txt_played);
        textview.setText(displayNumOfPlayed);
    }

    private void populateButtons(){

        TableLayout table  = (TableLayout) findViewById(R.id.tableForButtons);

        for(int i =0; i < row; i++){
        TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView((tableRow));
            for(int j =0; j < col; j++){
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                tableRow.addView(button);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

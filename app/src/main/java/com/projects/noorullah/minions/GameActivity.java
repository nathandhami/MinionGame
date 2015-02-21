package com.projects.noorullah.minions;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class GameActivity extends MinionActiviy {


    private GameLogic  defaultGame;
    private int NUM_OF_ROWS;
    private int NUM_OF_COLS;
    private int numOfMinionsLeft;
    private Button buttons[][];
    private Button user;
    private int userMoves = 0;
//    private boolean occupied[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        defaultGame = new GameLogic(getApplicationContext());

        NUM_OF_ROWS = defaultGame.getRowGameBoard();
        NUM_OF_COLS = defaultGame.getColGameBoard();
        numOfMinionsLeft = defaultGame.getMinions();
//
        // array of buttons
        buttons = new Button[NUM_OF_ROWS][NUM_OF_COLS];
//
        String minionRemaining = (String)getResources().getText(R.string.minionsRemain) + " " + numOfMinionsLeft;
        TextView textview = (TextView) findViewById(R.id.txt_remain);
        textview.setText(minionRemaining);
//
//
//        // Dynamically allocate grid buttons here
        populateButtons();
//
//        // Setup User and Minions
            lockButtons();
            generateImageForMinionButton(0, 2);
            generateImageForUserButton(0,0);
//
//
//        // Store Games played
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
//
    private void populateButtons(){

        TableLayout table  = (TableLayout) findViewById(R.id.tableForButtons);

        for(int i =0; i < NUM_OF_ROWS; i++){
        TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView((tableRow));
            for(int j =0; j < NUM_OF_COLS; j++){
//                 final int x = i;
//                final int y = j;
                Button button = new Button(this);

                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        lockButtons();
//                        generateImageForButton(x,y);
                    TextView viewMoves =  (TextView)findViewById(R.id.txt_moves);
                        viewMoves.setText((String)getResources().getText(R.string.movesMade) + " "+  ++userMoves);

                    }
                });
                tableRow.addView(button);
                buttons[i][j] = button;

            }
        }
    }
//
//
    private void generateImageForMinionButton(int x, int y){
        Button button  = buttons[x][y];
        int newHeight = 98;
        int newWidth= 142;
        Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(),R.drawable.greenmonster);
        Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource,scaledBitMap));
    }
//
    private void generateImageForUserButton(int x, int y){
        Button button  = buttons[x][y];
        int newHeight = 98;
        int newWidth= 142;
        Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(),R.drawable.stickman);
        Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource,scaledBitMap));
    }
//
    private void lockButtons() {
        for(int i =0; i < NUM_OF_ROWS; i++){
            for(int j =0; j < NUM_OF_COLS; j++){
                Button button = buttons[i][j];
                int width= button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);
                int height= button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
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

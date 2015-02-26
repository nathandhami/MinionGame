package com.projects.noorullah.minions;


import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;


public class GameActivity extends MinionActiviy {


    private Cell mapTracker;
    Player player;
    private int NUM_OF_ROWS;
    Minion minions[];
    private int NUM_OF_COLS;
    private int numOfMinionsLeft;
    private Button buttons[][];
    private Button user;
    Drawable defaultBackground;
    private int userMoves = 0;
//    private boolean occupied[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);


        mapTracker = new Cell(getApplicationContext());

        NUM_OF_ROWS = mapTracker.getNumOfRows();
        NUM_OF_COLS = mapTracker.getNumOfColumns();
        numOfMinionsLeft = mapTracker.getMinions();

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
         minions = new Minion[numOfMinionsLeft];

            player = new Player(mapTracker);
            mapTracker.assignUserRandomly();
        for(int i=0; i < numOfMinionsLeft;i++){
            minions[i] = new Minion(mapTracker);
            mapTracker.assignMinions(minions[i].getLocation());
            minions[i].getCoordinates();

        }

             generateImageForUserButton(mapTracker.getMap());
            generateImageForMinionButton(mapTracker.getMap());
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
                 final int x = i;
                final int y = j;
                Button button = new Button(this);


                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                               gridButtonClicked(x,y);
//                        lockButtons();
//                        generateImageForUserButton(x,y);
                               TextView viewMoves = (TextView) findViewById(R.id.txt_moves);
                               viewMoves.setText((String) getResources().getText(R.string.movesMade) + " " + ++userMoves);


                    }
                });
                tableRow.addView(button);
                buttons[i][j] = button;
                defaultBackground = button.getBackground();


            }
        }
    }
//
//

    private void gridButtonClicked(int x, int y){
        boolean current = x== mapTracker.getUserX() && y ==mapTracker.getUserY();
        if(current) {
            Toast.makeText(this, "Button clicked: " + x + "," + y, Toast.LENGTH_SHORT).show();
        }
        boolean goLeft = x == mapTracker.getUserX() && y == (mapTracker.getUserY()-1);
        boolean goRight = x==mapTracker.getUserX() && y == (mapTracker.getUserY() + 1);
        boolean goUp  = x == (mapTracker.getUserX() - 1) && y == mapTracker.getUserY();
        boolean goDown  = x == (mapTracker.getUserX() + 1) && y == mapTracker.getUserY();


        if(goLeft){
            if(mapTracker.minionExists(x,y)){
                Toast.makeText(this,"GAME OVER", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this,"GO LEFT" +  defaultBackground.toString(), Toast.LENGTH_SHORT).show();
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());
        }
        else if(goRight){
            if(mapTracker.minionExists(x,y)){
                Toast.makeText(this,"GAME OVER", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this,"GO RIGHT", Toast.LENGTH_SHORT).show();
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());

//            generateImageForDefaultButton(x,y);
//            button.setBackground(defaultBackground);
        }
        else if(goUp){
            if(mapTracker.minionExists(x,y)){
                Toast.makeText(this,"GAME OVER", Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(this);
                dialog.setTitle("GAME OVER");
                TextView tv = new TextView(this);
                tv.setText("You lost!");
                dialog.setContentView(tv);
                dialog.show();
            }
            Toast.makeText(this,"GO UP --Minion coordinate:  " + minions[3].getMinionXCoordinate() +"," + minions[3].getMinionYCoordinate(), Toast.LENGTH_SHORT).show();
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());
//            button.setBackground(defaultBackground);
        }
        else if(goDown){
            if(mapTracker.minionExists(x,y)){
                Toast.makeText(this,"GAME OVER", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this,"GO DOWN -- Minion coordinate: " + minions[2].getMinionXCoordinate() +"," + minions[2].getMinionYCoordinate() , Toast.LENGTH_SHORT).show();
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());
//            button.setBackground(defaultBackground);
        }


        for(int i=0; i < numOfMinionsLeft; i++) {
            generateImageForDefaultButton(minions[i].getMinionXCoordinate(), minions[i].getMinionYCoordinate());
            mapTracker.moveMinion(minions[i]);
            generateImageForMinionButton(minions[i]);
        }

    }

    private void generateImageForDefaultButton(int x, int y){
        Button button = buttons[x][y];
        int newHeight = 98;
        int newWidth = 142;
        Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_default_normal);
        Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitMap));
    }

    private void generateImageForMinionButton(int map[][]){
        for(int i =0; i < NUM_OF_ROWS; i++){
            for(int j =0; j < NUM_OF_COLS; j++){
                if(map[i][j] == 0) {
                    Button button = buttons[i][j];
                    int newHeight = 98;
                    int newWidth = 142;
                    Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.greenmonster);
                    Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
                    Resources resource = getResources();
                    button.setBackground(new BitmapDrawable(resource, scaledBitMap));

                }
            }
        }

    }

    private void generateImageForMinionButton(Minion minion){
        Button button = buttons[minion.getMinionXCoordinate()][minion.getMinionYCoordinate()];
        int newHeight = 98;
        int newWidth = 142;
        Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.greenmonster);
        Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitMap));

    }
//
    private void generateImageForUserButton(int map[][]){

        Button button;
        for(int i =0; i < NUM_OF_ROWS; i++){
            for(int j =0;  j< NUM_OF_COLS;j++){

                if(map[i][j] == 1){
                     button  = buttons[i][j];
                    int newHeight = 98;
                    int newWidth= 142;
                    Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(),R.drawable.stickman);
                    Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
                    Resources resource = getResources();
                    button.setBackground(new BitmapDrawable(resource,scaledBitMap));
                    break;
                }

            }
        }
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

package com.projects.noorullah.minions;


import android.app.Dialog;
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
import android.widget.Toast;

import java.util.ArrayList;


public class GameActivity extends MinionActiviy {


    private Cell mapTracker;
    Player player;
    private int NUM_OF_ROWS;

    ArrayList<Minion> minions = new ArrayList();
    private int NUM_OF_COLS;
    private int numOfMinionsLeft;
    private Button buttons[][];
    private Button user;
    Button defaultBackButton;
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
        displayMinionsRemaining();
//
//
//        // Dynamically allocate grid buttons here
            populateButtons();
//
//        // Setup User and Minions
            lockButtons();
         for(int i =0; i < numOfMinionsLeft; i++){
             Minion minion = new Minion(mapTracker);
            minions.add(minion);
         }

            player = new Player(mapTracker);
            mapTracker.assignUserRandomly();
        for(int i=0; i < numOfMinionsLeft;i++){
            mapTracker.assignMinions(minions.get(i).getLocation());
            minions.get(i).getCoordinates();
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

    private void displayMinionsRemaining() {
        String minionRemaining = (String)getResources().getText(R.string.minionsRemain) + " " + numOfMinionsLeft;
        TextView textview = (TextView) findViewById(R.id.txt_remain);
        textview.setText(minionRemaining);
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
                defaultBackButton = button;


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
            displayDialog(x, y);
            Toast.makeText(this,"GO LEFT", Toast.LENGTH_SHORT).show();
            mapTracker.map[mapTracker.getUserX()][mapTracker.getUserY()] = -1;
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());
        }
        else if(goRight){
            displayDialog(x, y);
            Toast.makeText(this,"GO RIGHT", Toast.LENGTH_SHORT).show();
            mapTracker.map[mapTracker.getUserX()][mapTracker.getUserY()] = -1;
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());

//            generateImageForDefaultButton(x,y);
//            button.setBackground(defaultBackground);
        }
        else if(goUp){
            displayDialog(x, y);
            Toast.makeText(this,"GO UP", Toast.LENGTH_SHORT).show();
            mapTracker.map[mapTracker.getUserX()][mapTracker.getUserY()] = -1;
            generateImageForDefaultButton(mapTracker.getUserX(),mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());
//            button.setBackground(defaultBackground);
        }
        else if(goDown){
            displayDialog(x, y);
            Toast.makeText(this,"GO DOWN ", Toast.LENGTH_SHORT).show();
            mapTracker.map[mapTracker.getUserX()][mapTracker.getUserY()] = -1;
            generateImageForDefaultButton(mapTracker.getUserX(), mapTracker.getUserY());
            mapTracker.assignUser(x,y);
            generateImageForUserButton(mapTracker.getMap());
//            button.setBackground(defaultBackground);
        }



        for(int i=0; i < numOfMinionsLeft; i++) {

            generateImageForDefaultButton(minions.get(i).getMinionXCoordinate(), minions.get(i).getMinionYCoordinate());
            mapTracker.moveMinion(minions.get(i));
            generateImageForMinionButton(minions.get(i),mapTracker.getMap());

        }

        for(int i=0; i < numOfMinionsLeft; i++) {

            for(Minion m : minions){
                if(m != minions.get(i)){
                    if(minions.get(i).getMinionXCoordinate()==m.getPreviousXCoordinate()
                            && minions.get(i).getMinionYCoordinate()==m.getPreviousYCoordinate()){
                        generateImageForMinionButton(minions.get(i),mapTracker.getMap());
                        break;

                    }
                }
            }

            //generateImageForMinionButton(minions.get(i),mapTracker.getMap());
        }

        int location[][] = mapTracker.getMap();


//        for(int i =0; i< numOfMinionsLeft; i++){
//            if(location[minions.get(i).getMinionXCoordinate()][minions.get(i).getMinionYCoordinate()] == 2 &&
//                    mapTracker.minionExists(minions.get(i).getMinionXCoordinate(),minions.get(i).getMinionYCoordinate())){
//
//                generateImageForImmobilzedCell(minions.get(i).getMinionXCoordinate(), minions.get(i).getMinionYCoordinate());
//                minions.remove(i);
//                numOfMinionsLeft--;
//
//            }
//        }



        // trap
            for (int i = 0; i < numOfMinionsLeft; i++) {

                    if (location[minions.get(i).getMinionXCoordinate()][minions.get(i).getMinionYCoordinate()] ==2) {
                        Log.i("TAG", "YES MOBILIZED CELL");
                        minions.remove(i);
                        numOfMinionsLeft--;
                        displayMinionsRemaining();
                    }

            }

        for(int i =0; i< NUM_OF_ROWS;i++){
            for(int j=0; j< NUM_OF_COLS; j++){

                if(location[i][j] ==2)
                Log.i("CELL","MOBILIZED CELL EXIST!");
            }
        }


        for(int i=0; i < numOfMinionsLeft-1; i++){

            for(int j=i+1; j<numOfMinionsLeft; j++){

                if(minions.get(i).getMinionXCoordinate() == minions.get(j).getMinionXCoordinate()){
                    if(minions.get(i).getMinionYCoordinate() == minions.get(j).getMinionYCoordinate()){


                        for(int s=0; s < numOfMinionsLeft; s++){

                            if(s!=i && s!=j){

                                if(minions.get(s).getMinionXCoordinate()==minions.get(i).getMinionXCoordinate()){
                                    if(minions.get(s).getMinionYCoordinate()==minions.get(i).getMinionYCoordinate()){



                                        if(i>s) i--;
                                        if (j>s) j--;

                                        mapTracker.killingMinions(minions.get(s));
                                        minions.remove(s);
                                        s--;
                                        numOfMinionsLeft--;

                                    }
                                }
                            }

                        }
                        mapTracker.killingMinions(minions.get(i));
                        mapTracker.killingMinions(minions.get(j));
                        Toast.makeText(this,"Minions Immobilised!", Toast.LENGTH_SHORT).show();
                        generateImageForImmobilzedCell(minions.get(i).getMinionXCoordinate(), minions.get(i).getMinionYCoordinate());
                        minions.remove(i);
                        if(j>i) j--;
                        minions.remove(j);
                        numOfMinionsLeft -= 2;
                        displayMinionsRemaining();


                        break;

                    }
                }
            }
        }

        for(int i=0; i< numOfMinionsLeft; i++){
            Log.i("TAG",i+1 + ":" + minions.get(i).getMinionXCoordinate() + "," + minions.get(i).getMinionYCoordinate());
        }

    }

    private void displayDialog(int x, int y) {
        if(mapTracker.minionExists(x,y) || mapTracker.immobilzedCellExists(x, y)){
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
            Dialog dialog = new Dialog(this);
            dialog.setTitle("GAME OVER");
            TextView tv = new TextView(this);
            tv.setText("You lost!");
            dialog.setContentView(tv);
            dialog.show();

        }
    }

    private void generateImageForImmobilzedCell(int x, int y){
        Button button = buttons[x][y];
        int newHeight = 98;
        int newWidth = 142;
        Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.splat);
        Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitMap));
    }


    private void generateImageForDefaultButton(int x, int y){

            Button button = buttons[x][y];
        Button defaultButton = new Button(this);
//            int newHeight = 98;
//            int newWidth = 142;
//            Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(),R.drawable.btn_default_normal);
//            Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
//            Resources resource = getResources();
            button.setBackground(defaultButton.getBackground());


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

    private void generateImageForMinionButton(Minion minion, int map[][]){
        if(map[minion.getMinionXCoordinate()][minion.getMinionYCoordinate()] == 0){
            Button button = buttons[minion.getMinionXCoordinate()][minion.getMinionYCoordinate()];
            int newHeight = 98;
            int newWidth = 142;
            Bitmap originalBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.greenmonster);
            Bitmap scaledBitMap = Bitmap.createScaledBitmap(originalBitMap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitMap));
        }


    }

//    public void deleteMinion(int del){
//
//        Log.i("TAG","Removed:" + ":" + minions.get(del).getMinionXCoordinate() + "," + minions.get(del).getMinionYCoordinate());
//        for(int i=0; i<numOfMinionsLeft; i++){
//
//            if(i == del){
//                minions.remove(i);
//                numOfMinionsLeft--;
//                break;
//            }
//
//        }
//
//
//        displayMinionsRemaining();
//    }
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

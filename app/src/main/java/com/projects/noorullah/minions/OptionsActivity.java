package com.projects.noorullah.minions;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class OptionsActivity extends MinionActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options);

        SharedPreferences settings1 = getSharedPreferences(GAME_PREFERENCES,MODE_PRIVATE);
        RadioButton savedCheckedRadioButton;

        // For option 1
        final RadioGroup radioGroupGameBoard = (RadioGroup)findViewById(R.id.radioGroup);
         savedCheckedRadioButton = (RadioButton)radioGroupGameBoard.getChildAt(settings1.getInt(RADIO_GROUP_KEY1,0));
        savedCheckedRadioButton.setChecked(true);

        radioGroupGameBoard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences settings = getSharedPreferences(GAME_PREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();


                RadioButton checkedItem = (RadioButton)radioGroupGameBoard.findViewById(checkedId);

                int checkedRadioButtonIndex = radioGroupGameBoard.indexOfChild(checkedItem);



                prefEditor.putInt(RADIO_GROUP_KEY1,checkedRadioButtonIndex);
                prefEditor.commit();

            }
        });

        // For option 2
        final RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radioGroup2);

         savedCheckedRadioButton = (RadioButton)radiogroup.getChildAt(settings1.getInt(RADIO_GROUP_KEY2,0));
        savedCheckedRadioButton.setChecked(true);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences settings = getSharedPreferences(GAME_PREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();

                RadioButton checkedItem = (RadioButton)radiogroup.findViewById(checkedId);

                int checkedRadioButtonIndex = radiogroup.indexOfChild(checkedItem);

                if(settings.getInt(RADIO_GROUP_KEY1,0) == 0 && checkedRadioButtonIndex >= 2){
                    Dialog dialog = new Dialog(OptionsActivity.this);
                    dialog.setTitle("ERROR IN CONFIGURATION");
                    TextView tv = new TextView(OptionsActivity.this);
                    tv.setText("Select valid options");
                    dialog.setContentView(tv);
                    dialog.show();
                }
                else{
                    prefEditor.putInt(RADIO_GROUP_KEY2,checkedRadioButtonIndex);
                    prefEditor.commit();
                }


            }
        });



        // Reset Button
        Button reset = (Button) findViewById(R.id.btn_erase);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(GAME_PREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putInt("GAMES_PLAYED",0);
                prefEditor.commit();
            }
        });


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

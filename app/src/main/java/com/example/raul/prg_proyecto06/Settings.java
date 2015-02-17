package com.example.raul.prg_proyecto06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.raul.prg_proyecto06.R.layout.activity_settings);
         final SharedPreferences my_preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        Button menu = (Button)findViewById(com.example.raul.prg_proyecto06.R.id.menu_button);
        Switch music_status = (Switch) findViewById(com.example.raul.prg_proyecto06.R.id.musicStatus);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_activity = new Intent(Settings.this, MainActivity.class);
                startActivity(main_activity);
            }
        });

        music_status.setChecked(my_preferences.getBoolean("music", true));

        music_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor_preferencias = my_preferences.edit();

                if (isChecked) {
                    editor_preferencias.putBoolean("music", true);
                    editor_preferencias.commit();
                } else {
                    editor_preferencias.putBoolean("music", false);
                    editor_preferencias.commit();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.raul.prg_proyecto06.R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.example.raul.prg_proyecto06.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

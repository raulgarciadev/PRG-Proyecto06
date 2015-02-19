package com.example.raul.prg_proyecto06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {
    private SoundPool mSoundPool;
    private int mSoundId;
    private int mVolume = 6, mVolumeMax = 10;
    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton settingsButton = (ImageButton)findViewById(R.id.imageButton3);
        final SharedPreferences my_preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        final ImageButton loveButton = (ImageButton)findViewById(R.id.likeButton);
        final ImageButton hateButton = (ImageButton)findViewById(R.id.hateButton);
        final ImageButton closeButton = (ImageButton)findViewById(R.id.closeButton);

        ambientMusic(my_preferences.getBoolean("music", true));

        settingsButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View v) {
                Intent abrirSettings = new Intent(MainActivity.this, Settings.class);
                startActivity(abrirSettings);
            }
        });

        loveButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View v) {
                Intent abrirLikes = new Intent(MainActivity.this, Likes.class);
                startActivity(abrirLikes);
            }
        });

        hateButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View v) {
                Intent abrirLikes = new Intent(MainActivity.this, Hates.class);
                startActivity(abrirLikes);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    public void ambientMusic(boolean b){
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundId = mSoundPool.load(MainActivity.this,R.raw.shake, 1);

        if (b){
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                    if (0 == status){
                        mSoundPool.play(mSoundId, (float)mVolume/mVolumeMax, (float)mVolume/mVolumeMax, 0, 1, 1.0f);
                    }
                }

            });
        }else{
            mSoundPool.pause(mSoundId);
        }

    }
}

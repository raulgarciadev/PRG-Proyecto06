package com.example.raul.prg_proyecto06;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ProgressBar;

/**
 * Created by Raul on 15/12/2014.
 */
public class SplashScreen extends ActionBarActivity {
    public static final int segundos=3;
    public static final int milisegundos = 1000*segundos;
    public static final int delay = 2;
    private ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.raul.prg_proyecto06.R.layout.splash_screen);
        myProgressBar = (ProgressBar) findViewById(com.example.raul.prg_proyecto06.R.id.progressBar);
        myProgressBar.setMax(maximoProgreso());
        empezarAnimacion();
    }

    public void empezarAnimacion(){
        new CountDownTimer(milisegundos, 1000){
            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {
                myProgressBar.setProgress(establecerProgreso(millisUntilFinished));
            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {

                Intent nuevo = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(nuevo);
                finish();
            }
        }.start();

    }

    public int establecerProgreso(long miliseconds){
        return (int)((milisegundos-miliseconds)/1000);
    }
    public int maximoProgreso(){
        return segundos-delay;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p/>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p/>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link android.view.Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p/>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p/>
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}

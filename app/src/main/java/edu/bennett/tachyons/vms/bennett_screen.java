package edu.bennett.tachyons.vms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

/**
 * An bennett full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class bennett_screen extends AppCompatActivity {

    private static final int TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bennett_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(bennett_screen.this, activity_login.class);
                finish();
                startActivity(i);
                Explode explode = new Explode();
                getWindow().setExitTransition(explode);
            }
        }, TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        Explode explode = new Explode();
        super.onBackPressed();
        getWindow().setExitTransition(explode);
    }

}

package edu.bennett.tachyons.vms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * An bennett full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class bennett_screen extends AppCompatActivity {

    private static final int TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bennett_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(bennett_screen.this,activity_login.class);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
        },TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(R.anim.slidein_back,R.anim.slideout_back);
    }

}

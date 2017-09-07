package com.example.android.visitormanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class visitors_option extends AppCompatActivity {

    //Button animation
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitors_option);

    }

    public void current_visitors(View view){
        // button animation
        view.startAnimation(buttonClick);
        Intent i = new Intent(visitors_option.this,current_visitors.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    public void add_visitors(View view){
        // button animation
        view.startAnimation(buttonClick);
        Intent i = new Intent(visitors_option.this,AddVisitors.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    public void Logout(View view){
        TokenSaver.deleteToken(getApplicationContext());
        Intent i = new Intent(visitors_option.this,activity_login.class);
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slidein_back,R.anim.slideout_back);

    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}

package edu.bennett.tachyons.vms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

public class visitors_option extends AppCompatActivity {

    //Button animation
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private static final int TIME_OUT = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitors_option);

        // to check if the permission is granted by the user
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

    }

    // to open the activity of the current visitors
    public void current_visitors(View view) {
        // button animation
        view.startAnimation(buttonClick);
        Intent i = new Intent(visitors_option.this, current_visitors.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(visitors_option.this, current_visitors.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                Explode explode = new Explode();
                getWindow().setExitTransition(explode);
            }
        }, TIME_OUT);*/
    }

    // to add visitors
    public void add_visitors(View view) {
        // button animation
        view.startAnimation(buttonClick);
        Intent i = new Intent(visitors_option.this, AddVisitors.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(visitors_option.this, AddVisitors.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                Explode explode = new Explode();
                getWindow().setExitTransition(explode);
            }
        }, TIME_OUT);*/

    }

    // function check if the permission granted and then enables the button
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    // to take the user to the login screen
    public void Logout(View view) {
        TokenSaver.deleteToken(getApplicationContext());
        Intent i = new Intent(visitors_option.this, activity_login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(visitors_option.this, activity_login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                Explode explode = new Explode();
                getWindow().setExitTransition(explode);
            }
        }, TIME_OUT);*/


    }


    @Override
    public void onBackPressed() {
        //Explode explode = new Explode();
        super.onBackPressed();
        //getWindow().setExitTransition(explode);
    }
}

package edu.bennett.tachyons.vms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    // to add the activity of the add activity
    public void add_visitors(View view) {
        // button animation
        view.startAnimation(buttonClick);
        Intent i = new Intent(visitors_option.this, AddVisitors.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slidein_back, R.anim.slideout_back);

    }

    // to take the user to the login screen
    public void Logoutall() {
        TokenSaver.deleteToken(getApplicationContext());
        Intent i = new Intent(visitors_option.this, activity_login.class);
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slidein_back, R.anim.slideout_back);

    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}

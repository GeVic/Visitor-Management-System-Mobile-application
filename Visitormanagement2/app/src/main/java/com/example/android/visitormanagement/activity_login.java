package com.example.android.visitormanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class activity_login extends AppCompatActivity {

    Button loginB;
    private EditText Username, Password;
    private ProgressDialog pD;
    private static String token;
    TokenSaver tk = new TokenSaver();
    // button animation
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // checking if the token exists
        token = TokenSaver.getToken(getApplicationContext());
        if(token != ""){
            // running the visitor option part
            Intent i = new Intent(activity_login.this,visitors_option.class);
            finish();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        }
        else {
            // if token is not provided
            setContentView(R.layout.activity_login);
        }

        loginB = (Button) findViewById(R.id.Login_view);
        Username = (EditText) findViewById(R.id.username_view);
        Password = (EditText) findViewById(R.id.password_view);
        // process dialogue
        pD = new ProgressDialog(this);
        pD.setCancelable(false);

    }

    // When Button Login clicked
    public void LoginClick(View view){
        // button animation
        view.startAnimation(buttonClick);
        String user = Username.getText().toString();
        String pass = Password.getText().toString();

        //Calling method of field validation
        if(CheckFieldValidation()) {
            pD.setMessage("Logging in ...");
            showDialog();

            //making object of RestAdapter
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RestInterface.url).build();

            //Creating Rest Services
            final RestInterface restInterface = adapter.create(RestInterface.class);
            //Calling method to get check login
            restInterface.Login(user,pass, new Callback<JsonResponse>() {

                @Override
                public void success(JsonResponse model, Response response) {

                    if (model.getSuccess()) {  //login Success

                        Toast.makeText(activity_login.this, "LoginIn is Successful", Toast.LENGTH_SHORT).show();
                        TokenSaver.setToken(getApplicationContext(),model.getData().getAccessToken().toString());
                        Intent i = new Intent(activity_login.this,visitors_option.class);
                        finish();
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

                    } else // login failure
                    {
                        pD.dismiss();
                        Username.setText("");
                        Password.setText("");
                        Toast.makeText(activity_login.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void failure(RetrofitError error) {
                    pD.dismiss();
                    String merror = error.getMessage();
                    Toast.makeText(activity_login.this, merror, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //checking field are empty
    private boolean CheckFieldValidation(){


        boolean valid=true;
        if(Username.getText().toString().equals("") && Password.getText().toString().equals("")){
            Username.setError("Can't be Empty");
            Password.setError("Can't be Empty");

            valid=false;
        }else if(Password.getText().toString().equals("")){
            Password.setError("Can't be Empty");
            valid=false;
        }
        else if(Username.getText().toString().equals("")){
            Username.setError("Can't be Empty");
            valid = false;
        }

        return valid;

    }
    // show dialog function
    private void showDialog() {
        if (!pD.isShowing())
            pD.show();
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}

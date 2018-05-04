package edu.bennett.tachyons.vms;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class current_visitors extends AppCompatActivity {

    //Button animation
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private RecyclerView cardRecyclerView;
    // private RecyclerView.Adapter showAdapter;
    private MyRecyclerViewAdapter showAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String token;

    // declaring the object of the class
    TokenSaver tk = new TokenSaver();
    BaseUrl bu = new BaseUrl();

    public static List<String> names = new ArrayList<>();
    public static List<String> mbnumbers = new ArrayList<>();
    public static List<String> idnums = new ArrayList<>();
    public static List<String> visiteeid = new ArrayList<>();
    public static ArrayList<String> imageUri = new ArrayList<>();
    private ProgressDialog pD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_current_visitors);

        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        search(searchView);
        // process dialogue
        pD = new ProgressDialog(this);
        pD.setCancelable(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Retrieving the visitor details
        loadJSON();
        // displaying the waiting dialog
        pD.setMessage("Retrieving the visitors ...");
        pD.show();

    }


    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();

        for (int index = 0; index < names.size(); index++) {
            DataObject obj = new DataObject(idnums.get(index).toString(), names.get(index).toString(), mbnumbers.get(index).toString(), visiteeid.get(index).toString(), imageUri.get(index).toString());
            results.add(index, obj);
        }

        return results;
    }

    // getting the visitors data
    private void loadJSON() {

        token = tk.getToken(getApplicationContext()).toString();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Token " + token);
            }
        };

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(GetDetails.BASE_URL).setRequestInterceptor(requestInterceptor).build();
        //Creating Rest Services
        GetDetails get_details = adapter.create(GetDetails.class);

        //Calling method to get check login
        get_details.get_detail(new Callback<JsonData>() {
            @Override
            public void success(JsonData visit, Response response) {

                if (visit.getSuccess()) {  //login Success
                    // Toast.makeText(current_visitors.this, "Getting the Data", Toast.LENGTH_SHORT).show();
                    for (GetRequest list : visit.getData()) {
                        names.add(list.getName());
                        idnums.add(list.getCardNo());
                        mbnumbers.add(list.getMobile());
                        String ImageUri = bu.BASE_URL + "v1/photo/thumb/" + list.getVisitorId();
                        visiteeid.add(list.getVisitorId());
                        imageUri.add(ImageUri);
                    }

                    if (pD.isShowing()) {
                        pD.dismiss();
                    }

                    cardRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    mLayoutManager = new LinearLayoutManager(current_visitors.this);
                    cardRecyclerView.setLayoutManager(mLayoutManager);
                    showAdapter = new MyRecyclerViewAdapter(getApplicationContext(), getDataSet());
                    cardRecyclerView.setAdapter(showAdapter);


            }

                // if the success is false
                else {
                    if (visit.getErrors().getCode().equals("1403") || visit.getErrors().getCode().equals("1190")) {
                        TokenSaver.deleteToken(getApplicationContext());
                        Toast.makeText(current_visitors.this, "You have been logged out, Please login again", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(current_visitors.this, activity_login.class);
                        pD.dismiss();
                        finish();
                        startActivity(i);
                        //Explode explode = new Explode();
                        //getWindow().setExitTransition(explode);
                    } else {
                        Toast.makeText(current_visitors.this, "Network is unreachable", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String V_error = error.getMessage();
                Toast.makeText(current_visitors.this, V_error, Toast.LENGTH_LONG).show();
                if (pD.isShowing()) {
                    pD.dismiss();
                }
            }
        });
    }

    void clearme() {
        names.clear();
        mbnumbers.clear();
        idnums.clear();
        imageUri.clear();
        visiteeid.clear();
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (names.size() != 0) {
                    showAdapter.getFilter().filter(newText);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    public void onBackPressed() {
        //Explode explode = new Explode();
        clearme();
        super.onBackPressed();
        //getWindow().setExitTransition(explode);
    }
}


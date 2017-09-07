package com.example.android.visitormanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
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
    private RecyclerView.Adapter showAdapter;
    MyRecyclerViewAdapter newAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String token;
    // declaring the object of the class
    TokenSaver tk = new TokenSaver();
    public static List<String> names = new ArrayList<>();
    public static List<String> mbnumbers = new ArrayList<>();
    public static List<String> idnums = new ArrayList<>();
    public static List<String> visiteeid = new ArrayList<>();
    private ProgressDialog pD;
    public static ArrayList<DataObject> eDataset;
    Button exit=(Button)findViewById(R.id.exit);

    exit.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
            exitClick(View ,position);
        }
    });

    visitors_option vo = new visitors_option();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_visitors);
//        SearchView searchView = (SearchView)  findViewById(R.id.search_view);
//        search(searchView);

        // process dialogue
        pD = new ProgressDialog(this);
        pD.setCancelable(false);

        // Retriving the visiter details
        loadJSON();
        // dispalying the waiting dialog
        pD.setMessage("Retriving the visitors ...");
        pD.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < names.size(); index++) {
            DataObject obj = new DataObject(idnums.get(index).toString(),names.get(index).toString(),
                    mbnumbers.get(index).toString(),visiteeid.get(index).toString());
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
                request.addHeader("Authorization", "Token "+ token);
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
                    Toast.makeText(current_visitors.this, "Getting the Data", Toast.LENGTH_SHORT).show();
                    for (GetRequest list : visit.getData()) {
                        names.add(list.getName());
                        idnums.add(list.getCardNo());
                        mbnumbers.add(list.getMobile());
                        visiteeid.add(list.getVisitorId());
                    }

                    cardRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    mLayoutManager = new LinearLayoutManager(current_visitors.this);
                    cardRecyclerView.setLayoutManager(mLayoutManager);

                    if(pD.isShowing()){
                        pD.dismiss();
                    }

                    showAdapter = new MyRecyclerViewAdapter(getDataSet());
                    cardRecyclerView.setAdapter(showAdapter);

                }
                else // login failure
                {
                    Toast.makeText(current_visitors.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String V_error = error.getMessage();
                Toast.makeText(current_visitors.this, V_error, Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void search(SearchView searchView) {
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                final List<String> filteredModelList = filter(idnums, newText);
//
//                newAdapter.setFilter(filteredModelList);
//                return true;
//            }
//        });
//    }

    private List<String> filter(List<String> idnums, String query) {
        query = query.toLowerCase();
        final List<String> filteredModelList = new ArrayList<>();
        for (int i=0; i < idnums.size(); i++ ) {
            final String text = idnums.get(i);
            if (text.contains(query)) {
                filteredModelList.add(text);
            }
        }
        return filteredModelList;
    }

    public void add_back(View view){
        // button animation
        view.startAnimation(buttonClick);
        Intent i = new Intent(current_visitors.this,AddVisitors.class);
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slidein_back,R.anim.slideout_back);
    }

   // @Override
     public void onBackPressed() {
         // finish() is called in super: we only override this method to be able to override the transition
         super.onBackPressed();
         overridePendingTransition(R.anim.slidein_back, R.anim.slideout_back);
     }


     public void exitClick(int position){

        token = tk.getToken(getApplicationContext()).toString();

         RequestInterceptor requestInterceptor = new RequestInterceptor() {
             @Override
             public void intercept(RequestFacade request) {
                 request.addHeader("Authorization", "Token "+ token);
            }
        };

         final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(GetDetails.BASE_URL).setRequestInterceptor(requestInterceptor).build();
         GetDetails exit_user = adapter.create(GetDetails.class);
         String visitorid = eDataset.get(position).getmText4();
         exit_user.exit_user(visitorid);

     }
}


package com.example.android.visitormanagement;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class AddVisitors extends AppCompatActivity implements OnItemSelectedListener  {

    //Button animation
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private Button take_pic;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    private static File image;
    private static String token;
    private ProgressDialog pD;
    private String visitee_name;
    private int visitorno;
    private String visitor_ID;
    // declaring parameters of the layout file
    private static EditText Name,Cardno, Mobile;
    private static TextView change;
    // Image view
    private static ImageView UImage;
    // declaring the passing parameter
    private String name,temp,cardno,mobile;
    private String Error_message;
    private AutoCompleteTextView SuggestNames;
    List<String> visitee_names = new ArrayList<>();
    List<String> visitee_id = new ArrayList<>();


    // declaring the object of the class
    TokenSaver tk = new TokenSaver();
    visitors_option vo = new visitors_option();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        visitee_data();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitors);
        // getting the parameters value form the layout
        Name = (EditText) findViewById(R.id.Visitorname_view);
        Spinner Purpose = (Spinner) findViewById(R.id.Purpose_view);

        // click listener
        Purpose.setOnItemSelectedListener(this);
        Cardno = (EditText) findViewById(R.id.Card_view);
        UImage = (ImageView)  findViewById(R.id.userimage);
        change = (TextView) findViewById(R.id.change_view);
        change.setClickable(false);

        // AUTO COMPLETE TEXT VIEW
        SuggestNames = (AutoCompleteTextView) findViewById(R.id.mail_view);
        Mobile = (EditText) findViewById(R.id.Contact_view);
     //   take_pic = (Button) findViewById(R.id.camera_view);

        // getting the token stored in shared preferences
        token = TokenSaver.getToken(getApplicationContext());

        // EditText array
        EditText[] Ed = {Name,Mobile,Cardno};

        // Create an ArrayAdapter using the string array and a default spinner layout
    /*    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Purpose, R.layout.spinner_view);

       // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.dropdown_item);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_view) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.spinnerTarget)).setText("");
                    ((TextView)v.findViewById(R.id.spinnerTarget)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(R.layout.dropdown_item);
        adapter.add("Delivery");
        adapter.add("Campous Tour");
        adapter.add("Admission");
        adapter.add("Meeting");
        adapter.add("Other");
        adapter.add("Purpose"); //This is the text that will be displayed as hint.


        Purpose.setAdapter(adapter);
        Purpose.setSelection(adapter.getCount()); //set the hint the default selection so it appears on launch.
        Purpose.setOnItemSelectedListener(this);

       // Apply the adapter to the spinner
     //   Purpose.setAdapter(adapter);

        // setting the title to the spinner
     //   Purpose.setSelection(5);

        // process dialogue
        pD = new ProgressDialog(this);
        pD.setCancelable(false);

        // to check if the permission is granted by the user
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            UImage.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        // loop to hide the keypad
        for(int i = 0 ; i < 3; i++) {
            Ed[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard(v);
                    }
                }
            });
        }

            SuggestNames.setThreshold(1);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, visitee_names);
            SuggestNames.setAdapter(arrayAdapter);
            arrayAdapter.setNotifyOnChange(true);

            // Focus listener on Autocomplete Edit text view
            SuggestNames.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        SuggestNames.showDropDown();
                    }
                    else if(!hasFocus){
                        hideKeyboard(v);
                    }
                }
            });




        // set tag to null when an item is tapped
        SuggestNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                // visitee name
                visitee_name = SuggestNames.getText().toString();
                visitorno = visitee_names.indexOf(visitee_name);
                visitor_ID = visitee_id.get(visitorno);
            }
        });

       Purpose.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        }) ;

    }




    // function check if the permission granted and then enables the button
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                UImage.setEnabled(true);
            }
        }
    }

    //  invokes the intent to capture a photo
    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            /*File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(AddVisitors.this, "Error occurred while creating the File", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);*/
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    // handling the result of takepicture method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
              //  take_pic.setText("Change Picture");
                String udata="Edit Picture";
                SpannableString content = new SpannableString(udata);
                content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
                change.setText(content);
                change.setClickable(true);
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                UImage.setScaleType(ImageView.ScaleType.FIT_XY);
                UImage.setImageBitmap(imageBitmap);
                UImage.setBackgroundResource(R.drawable.imageborder);
                UImage.setPadding(2, 2, 2, 2);
                UImage.setScaleType(ImageView.ScaleType.CENTER_CROP); // <- set the scale
                UImage.setCropToPadding(true); // <- requires API 16 or more
                SaveImage(imageBitmap);
            }
        }

     // function for creating the directory used to store the images
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // method to save the compressed bitmap image
    private void SaveImage(Bitmap bitmapImage) {


        File file = null;
        try {
            file = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(AddVisitors.this, "Error occurred while creating the File", Toast.LENGTH_SHORT).show();
        }
        if (file != null);
        try {
            FileOutputStream out = new FileOutputStream(file);
            int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
            scaled.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // when the ADD button is clicked
    public void AddClick(View view)
    {
        view.startAnimation(buttonClick);


        // getting their value
        name = Name.getText().toString();
        cardno = Cardno.getText().toString();
        mobile = Mobile.getText().toString();


        // /Calling method of field validation
        if(CheckFieldValidation()) {

            pD.setMessage("Adding the visitor ...");
            showDialog();
            // image file to e passed
            final TypedFile image = new TypedFile("multipart/form-data", new File(mCurrentPhotoPath));
            //making object of RestAdapter
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(UserDetails.BASE_URL).build();
            //Creating Rest Services
            final UserDetails user = adapter.create(UserDetails.class);
            //Calling method to get check login

            user.user_details(cardno,name,mobile,temp,image,token,visitor_ID, new Callback<Add_Response>() {

                @Override
                public void success(Add_Response model, Response response) {

                    if (model.getSuccess()) {  //login Success

                        Intent i = new Intent(AddVisitors.this,visitors_option.class);
                        Toast.makeText(AddVisitors.this, "Visitor is added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(i);
                        overridePendingTransition(R.anim.slidein_back,R.anim.slideout_back);

                    } else // login failure
                    {
                        Error_message = errorHandling(model.getErrors().getCode());
                        Toast.makeText(AddVisitors.this, Error_message, Toast.LENGTH_LONG).show();
                        if(model.getErrors().getCode().equals("1403")){
                            Intent i = new Intent(AddVisitors.this,activity_login.class);
                            finish();
                            startActivity(i);
                            overridePendingTransition(R.anim.slidein_back,R.anim.slideout_back);
                        }
                        pD.dismiss();
                    }
                }


                @Override
                public void failure(RetrofitError error) {
                    pD.dismiss();
                    Toast.makeText(AddVisitors.this,"Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    //checking field are empty
    private boolean CheckFieldValidation(){

        boolean valid=true;
        if(name.equals("")){
            valid = false;
            Name.setError("Required field");
        }
        if(cardno.equals("")){
            valid = false;
            Cardno.setError("Required field");
        }
        if(mobile.equals("")){
            valid = false;
            Mobile.setError("Required field");
        }

        if(!(mobile.length() >= 6 && mobile.length() <=15) ){
            valid = false;
            Mobile.setError("Invalid mobile number");
        }
        if(cardno == "0" || cardno.length() > 4){
            valid = false;
            Cardno.setError("Invalid number");
        }
        if(!change.isClickable()){
            valid = false;
            Toast.makeText(AddVisitors.this,"Visitor photo has not been taken", Toast.LENGTH_LONG).show();
        }

        if(!(visitee_names.contains(SuggestNames.getText().toString()))) {
            valid = false;
            SuggestNames.setError("Select name from list");
        }

        return valid;

    }

    // show dialog function
    private void showDialog() {
        if (!pD.isShowing())
            pD.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
       // To get the position of the item selected
        temp = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {

   }


   // to map errors
    public String errorHandling(String errorCode){
        switch(errorCode){
            case "1403": return "This means that the login status or access token has expired, been revoked, or is otherwise invalid";

            case "1190": return "Access Token not provided as Authorization header or access token not provided in correct format in header.";

            case "1142": return "Credentials provided are wrong";

            case "1157": return "Required authentication data not provided";

            case "2200": return "Required POST data was not provided";

            case "2400": return "Incorrect data format/Data is too large";

            case "2415": return "Unsupported media type";

            case "5501": return "Temporary issue due to downtime or throttling. Wait and retry the operation.";

            default: return "Some internal issue";

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    // function to get the visitee name and email ID's
    public void visitee_data() {
        // auth token
        token = TokenSaver.getToken(getApplicationContext());
        // created request intercepeter for better request handling
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Token "+ token);
            }
        };

        //making object of RestAdapter
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(GetVisitee.Visitee_url).setRequestInterceptor(requestInterceptor).build();


        //Creating Rest Services
        final GetVisitee get_details = adapter.create(GetVisitee.class);

        //Calling method to get check login
        get_details.getDetails(new Callback<json_visitee>() {
            @Override
            public void success(json_visitee visit, Response response) {

                if (visit.getSuccess()) {  //login Success

                    Toast.makeText(AddVisitors.this, "Got the Visitee names", Toast.LENGTH_SHORT).show();
                    for (visitee_list vlist : visit.getData()) {
                        visitee_names.add(vlist.getName());
                        visitee_id.add(vlist.getVisiteeNo());
                    }

                } else // login failure
                {
                    Toast.makeText(AddVisitors.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String V_error = error.getMessage();
                Toast.makeText(AddVisitors.this, V_error, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(R.anim.slidein_back,R.anim.slideout_back);
    }

}

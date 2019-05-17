package com.example.cookingapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class add_activity extends AppCompatActivity {


    private Button btn_choose;

    ImageView imageview;

    private Button btn_submiter;
    private EditText name;
    private EditText description;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 99;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!InternetConnectivity(add_activity.this)) buildDialog(add_activity.this).show(); //check for internet connectivity if no internet display internet permission
        setContentView(R.layout.activity_add); //setting layout

        btn_choose = findViewById(R.id.btn_choosedesign); //assigning it according to layout
        name = findViewById(R.id.title);
        description = findViewById(R.id.recipedetails);

        imageview = findViewById(R.id.displayimage);

        btn_submiter = findViewById(R.id.btn_submit);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatetitle(name); //validate title on the go
                }
            }
        });
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatedescription(description); //validate description of vthe recipe on the go
                }
            }
        });

        btn_submiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validtitle = validatetitle(btn_submiter); // on click check if title is valid
                boolean validdesc = validatedescription(btn_submiter); //on click check if description is valid
                if (validtitle && validdesc) {
                    Submit(); // call this method if title and description of recipe are valid
                }
            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(); // on click show call filechooser method
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) { //checks for permission if permission is already granted
                // Permission granted already or not? // if it return true do nothing if false request permission
            } else {
                requestPermission(); // if permission is not already granted display permission
            }
        }

    }

    private void requestPermission() {
        //request permission to access the storage for images
        if (ActivityCompat.shouldShowRequestPermissionRationale(add_activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(add_activity.this, " Please allow this permission in Application Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(add_activity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            // PERMISSIONS_REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(add_activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;// if permission is given return true
        } else {
            return false; //no permission given return false
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: //request code is passed which means user allowed permission
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(add_activity.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show(); //Permission is succesfully given
                } else {
                    Toast.makeText(add_activity.this, "Permission Denied", Toast.LENGTH_LONG).show(); //Permission failed
                }
                break;
        }
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*"); //to show an image
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);//hence pass image request
        // open intent which displays images stored in device so user chooses an image
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // once the intent where the user has to choose an image is opened
        //if reuquestcode == to image request which is passed from showfileChooser method
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            //if user selected an image therefore data not null
            try {
                //Getting the Bitmap from Gallery

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                // Toast.makeText(this, "" + bitmap, Toast.LENGTH_SHORT).show();// for testing

                //Setting the Bitmap to ImageView
                imageview.setImageBitmap(bitmap); //to be displayed in add_activity once the user has chosen an image.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validatedescription(View view) {
        if (description.getText().toString().trim().length() > 19) { // description must be greater than or equal to 20 if it is greater
            description.setError(null);                                 // do not display any errors

            return true;

        } else if (TextUtils.isEmpty(description.getText())) {
            description.setError("Recipe Description is required"); //when description field is empty

        } else {
            description.setError("Minimum of 20 characters"); //when characters do not add up to 20 characters in textview
        }

        return false;
    }

    private boolean validatetitle(View view) {
        if (name.getText().toString().trim().length() > 2) { // title must be greater or equal to 3
            name.setError(null); //if it is greater or equal to 3 do not display error

            return true;

        } else if (TextUtils.isEmpty(name.getText())) {
            name.setError("Recipe Title is required");// if empty display this error

        } else {
            name.setError("Minimum of 3 characters");//when characters do not add up to 3 characters in textview display this error
        }
        return false;
    }

    private void Submit() { //adding recipre to database according to type
        final String name_recipe = this.name.getText().toString().trim(); //final name of recipe to insert in db
        final String name_details = this.description.getText().toString().trim(); // final description to insert in db


        String URL_LOC = "http://192.168.1.175:81/CookingApp/insert_recipe.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success"); //if query succeeded

                            if (success.equals("1")) {
                                Toast.makeText(add_activity.this, "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                                //once recipe is uploaded user is taken back to main activity
                                Intent intent = new Intent(add_activity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//user wont be able to return to this activity once on back is pressed once in main activity
                                startActivity(intent);// start intent from add activity go to main activity
                                finish(); // add_activity is destroyed


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(add_activity.this, "Failed", Toast.LENGTH_SHORT).show(); //failuer
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(add_activity.this, "Please make sure an image is chosen", Toast.LENGTH_SHORT).show();// connection error and no image is selected
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                String images = getStringImage(bitmap); //converting image by calling method

                params.put("food_type", getIntent().getStringExtra("food_type").trim()); //getting food_type which was passed from putextra
                                                                                                // intent default method from previous activity
                params.put("name", name_recipe); //passing data to php script to run successfull
                params.put("details", name_details);
                params.put("image", images);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap) { //converting image to bitmap which is saved on folder as png
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //data written in byte array which is needed since we use bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray(); //putting stream data in byteArray
        String temp = Base64.encodeToString(b, Base64.DEFAULT); //encoding representation of binary data to string


        return temp;
    }


    private boolean InternetConnectivity(Context context) { //check internet conenction
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting()); //if there is network and is connected or still connecting to a network it does not display error
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder message = new AlertDialog.Builder(c);
        message.setCancelable(false); //cannot cancel message
        message.setTitle("No Internet Connection");//title of message
        message.setMessage("Please ensure your device has internet connection"); //error message

        message.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);//proceed is clicked hence itnernet settings are opened
                startActivity(myIntent);
            }

        });
        message.setNegativeButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); //exit pressed activity destroyed
            }
        });

        return message;
    }

    protected void onResume() {
        super.onResume();
        if (!InternetConnectivity(add_activity.this)) buildDialog(add_activity.this).show();//check for internet


    }


}



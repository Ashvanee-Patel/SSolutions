package com.example.testapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText MobileNo;
    private EditText Email;
    private Button Login;

    private boolean MobileValid = false;
    private boolean EmailValid = false;

    private int GPS_PERMISSION_CODE = 1;

    private String keyUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // This is only for >= 23 API
        if( ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(MainActivity.this,"You have already granted this permission!", Toast.LENGTH_SHORT).show();
        }
        else{
            requestGPSPermission();
        }

        // This code check Network
        if(haveNetwork()){
            EnableGps();// With the help of Intent you go on GPS setting  Alert Dialog are used

        }
        else if(! haveNetwork()){
            Toast.makeText(MainActivity.this,"Please ON:\n     Mobile Data Connection \n               or   \n              WIFI",Toast.LENGTH_LONG).show();
        }

        setContentView(R.layout.activity_main);

        MobileNo = findViewById(R.id.ETno);
        Email = findViewById(R.id.ETmail);
        Login = findViewById(R.id.BTlogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(haveNetwork()){
                    EnableGps();// With the help of Intent you go on GPS setting  Alert Dialog are used
// ----------------------------------------------------------------------------------------------------------------------
                    if( Email.getText().length() != 0 ){
                        if(isEmailValid(Email.getText().toString()) ){
                            EmailValid = true;
                            keyUser = Email.getText().toString();
                            //Toast.makeText(MainActivity.this,"Good Email",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Email.setError("E-mail is Not Valid Make sure it is valid!");
                            //Toast.makeText(MainActivity.this,"Email Not valid",Toast.LENGTH_LONG).show();
                        }
                    }

                    if( MobileNo.getText().length() != 0){
                        isPhoneValid(MobileNo.getText().toString());
                    }

                    if( MobileNo.getText().length() == 0 && Email.getText().length() == 0){
                        Toast.makeText(MainActivity.this, "Please Fill The Phone Number or E-mail", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Loggedin(MobileValid,EmailValid);
                    }

//---------------------------------------------------------------------------------------------------------------------------

                }
                else if(! haveNetwork()){
                    Toast.makeText(MainActivity.this,"Please ON:\n     Mobile Data Connection \n               or   \n              WIFI",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    //-----------------------------------------------Validations--------------------------------------------------------------------

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void isPhoneValid( String Number){
        if(Number.length()>9 && Number.length()<13){
            MobileValid = true;
            keyUser = Number;
            //Toast.makeText(MainActivity.this,"Good Number",Toast.LENGTH_LONG).show();

        }
        else{
            MobileNo.setError("Number is Not Valid Make sure it is valid!");
            //Toast.makeText(MainActivity.this,"Number Not Valid",Toast.LENGTH_LONG).show();
        }
    }

    //----------------------------------------------------------LoggedIn-----------------------------------------------------

    private void Loggedin(boolean M, boolean E){
        if(M || E){
            Toast.makeText(MainActivity.this, "Good You have login", Toast.LENGTH_SHORT).show();
            if(haveNetwork()){
                EnableGps();// With the help of Intent you go on GPS setting  Alert Dialog are used
                LocationAccess();// Location check that location are enabled are not
            }
            else if(! haveNetwork()){
                Toast.makeText(MainActivity.this,"Please ON:\n     Mobile Data Connection \n               or   \n              WIFI",Toast.LENGTH_LONG).show();
            }


        }
    }

    private void LocationAccess(){

            GPStracker g = new GPStracker(getApplicationContext());
            Location l = g.getLocation();

            if(l != null){

                double lat = l.getLatitude();
                double lon = l.getLongitude();
                ArrayList<String> LatLon = new ArrayList<>();
                LatLon.add(String.valueOf(lat));
                LatLon.add(String.valueOf(lon));
                LatLon.add(String.valueOf(keyUser));
                Intent intent = new Intent(MainActivity.this, Authorised.class);
                intent.putExtra("LTL", LatLon);
                startActivity(intent);
                finish();
                Toast.makeText(this,String.valueOf(lat)+"\n"+String.valueOf(lon)+"\n"+LatLon.toString(),Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Due to network Problem GPS are not establish Please try again after few minute", Toast.LENGTH_LONG).show();
            }

    }

    //_________________________________ function for network connection check, it will check that WIFI or Mobile data are Enable or not ?____________________________
    private boolean haveNetwork(){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos){
            if(info.getTypeName().equalsIgnoreCase("WIFI")){
                if(info.isConnected()){
                    have_WIFI = true;
                }
            }

            if(info.getTypeName().equalsIgnoreCase("MOBILE")){
                if(info.isConnected()){
                    have_MobileData = true;
                }
            }
        }
        return have_MobileData || have_WIFI;
    }



    // ____________________________________With the help of Intent you go on GPS setting  Alert Dialog are used______________________
    private void EnableGps(){

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(String.valueOf(statusOfGPS) == "false"){

            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
            a_builder.setMessage("Please enable GPS/Location and Internet....")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //-----Do something work.....
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);

                        }
                    });
            a_builder.create();
            a_builder.setTitle("GPS & Internet Must be Enable");
            a_builder.show();
        }

    }

    //--------------------------------------requestGPSPermissions-------------------------------------------------

    private void requestGPSPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This Permission is needed Because without GPS Permission We didn't do")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},GPS_PERMISSION_CODE);
                        }
                    })
                    .create().show();

        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},GPS_PERMISSION_CODE);
        }

    }
}

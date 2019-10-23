package com.example.testapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Authorised extends AppCompatActivity {

    private TextView UserID;
    private  TextView Information;

    private  String KeyUser;
    private double lat,lon;
    private String city,state,country,postalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_authorised);

        UserID = findViewById(R.id.Tvuser);
        Information = findViewById(R.id.Tvinfo);

        // Get the data from MainActivity to AuthorisedActivity

        ArrayList<String> LatLon = (ArrayList<String>) getIntent().getSerializableExtra("LTL");
        lat = Double.parseDouble(LatLon.get(0));
        lon = Double.parseDouble(LatLon.get(1));
        KeyUser = LatLon.get(2) ;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List< Address > addresses = geocoder.getFromLocation(lat,lon,1);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            UserID.setText(KeyUser+"\n");
            UserID.append(city+" ");
            UserID.append(state+" ");
            UserID.append(country+" ");
            UserID.append(postalCode);


       } catch (Exception e) {
            //Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            //Log.v("Ashvanee",e.toString());

        }

        NoOfServices(Integer.parseInt(postalCode));


    }

    private void NoOfServices(int code){

        if(code >= 212601 && code <= 212699){
            Information.setText("Fatehpur");

            final ListView listView =  findViewById(R.id.Item_List);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Education");
            arrayList.add("Water");
            arrayList.add("Electricity");
            arrayList.add("LPG");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem=(String) listView.getItemAtPosition(position);
                    Toast.makeText(Authorised.this,clickedItem,Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(code >= 208001 && code <= 208099){
            Information.setText("Kanpur");

            final ListView listView =  findViewById(R.id.Item_List);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Education");
            arrayList.add("Water");
            arrayList.add("Electricity");
            arrayList.add("LPG");
            arrayList.add("Police");
            arrayList.add("PetrolPump");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem=(String) listView.getItemAtPosition(position);
                    Toast.makeText(Authorised.this,clickedItem,Toast.LENGTH_LONG).show();
                }
            });

        }
        else if(code >= 281201 && code <= 281299){
            Information.setText("Agra");

            final ListView listView =  findViewById(R.id.Item_List);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Education");
            arrayList.add("Water");
            arrayList.add("Electricity");
            arrayList.add("TAJ MAHAL");
            arrayList.add("Police");
            arrayList.add("PetrolPump");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem=(String) listView.getItemAtPosition(position);
                    Toast.makeText(Authorised.this,clickedItem,Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(code >= 110001 && code <= 110097){
            Information.setText("Delhi");

            final ListView listView =  findViewById(R.id.Item_List);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Water");
            arrayList.add("Electricity");
            arrayList.add("Delhi Metro");
            arrayList.add("Police");
            arrayList.add("PetrolPump");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem=(String) listView.getItemAtPosition(position);
                    Toast.makeText(Authorised.this,clickedItem,Toast.LENGTH_LONG).show();
                }
            });

        }else {
            Information.setText("India");

            final ListView listView =  findViewById(R.id.Item_List);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Water");
            arrayList.add("Electricity");
            arrayList.add("Delhi Metro");
            arrayList.add("Police");
            arrayList.add("PetrolPump");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem=(String) listView.getItemAtPosition(position);
                    Toast.makeText(Authorised.this,clickedItem,Toast.LENGTH_LONG).show();
                }
            });
        }

    }

}

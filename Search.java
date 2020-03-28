package com.example.map_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Search extends AppCompatActivity {

    Button home;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        LatLng searchedAddress = getIntent().getExtras().getParcelable("latlng");
        LatLng savedAddress = null;

        home = findViewById(R.id.button4);
        output = findViewById(R.id.textView7);

        ArrayList<String[]> data = new ArrayList<>();

        String line;
        String filename = "data.txt";


        try {
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Iterator i = data.iterator();
        while (i.hasNext()) {
            String[] point= (String[]) i.next();
            savedAddress = new LatLng(Double.parseDouble(point[3]), Double.parseDouble(point[4]));
        }

        String answer = "There is a fire approximately " + distanceAway(searchedAddress,savedAddress) + " from your input address \n";
        output.setText(answer);
        
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
    }

    public double distanceAway (LatLng searchedAddress, LatLng savedAddress){
        float distance;

        Location searched = null;
        searched.setLatitude(searchedAddress.latitude);
        searched.setLongitude(searchedAddress.longitude);

        Location saved = null;
        saved.setLatitude(savedAddress.latitude);
        saved.setLongitude(savedAddress.longitude);

       return  distance = searched.distanceTo(saved);
    }
}

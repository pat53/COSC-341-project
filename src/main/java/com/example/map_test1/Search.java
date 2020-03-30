package com.example.map_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Search extends AppCompatActivity {

    Button home;
    Button next;
    Button back;
    List<Double> distance;
    int pageNum;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        distance = new ArrayList<>();
        pageNum = 0;
        Intent intentOld = getIntent();
        Bundle bundle = intentOld.getBundleExtra("extra");
        LatLng searchedAddress = bundle.getParcelable("latlng");

        LatLng savedAddress = null;

        home = findViewById(R.id.button4);
        next = findViewById(R.id.nextButton);
        back = findViewById(R.id.backButton);

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
            isr.close();
            fis.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Iterator i = data.iterator();
        while (i.hasNext()) {
            String[] point= (String[]) i.next();
            savedAddress = new LatLng(Double.parseDouble(point[3]), Double.parseDouble(point[4]));
            distance.add(distanceAway(searchedAddress,savedAddress));
        }

        Collections.sort(distance);


        String answer = String.format("There is a fire approximately %.2f km from your input address \n",distance.get(pageNum)/1000);
        output.setText(answer);
        
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();          }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(distance.size()==(pageNum+1)){
                    pageNum=0;
                }
                else{
                    pageNum++;
                }

                String answer = String.format("There is a fire approximately %.2f km from your input address \n",distance.get(pageNum)/1000);
                output.setText(answer);            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pageNum==0){
                    pageNum=distance.size()-1;
                }
                else{
                    pageNum--;
                }

                String answer = String.format("There is a fire approximately %.2f km from your input address \n",distance.get(pageNum)/1000);
                output.setText(answer);
            }
        });
        
    }

    public double distanceAway (LatLng searchedAddress, LatLng savedAddress){
        Location searched = new Location("");
        searched.setLatitude(searchedAddress.latitude);
        searched.setLongitude(searchedAddress.longitude);

        Location saved = new Location("");;
        saved.setLatitude(savedAddress.latitude);
        saved.setLongitude(savedAddress.longitude);

       return searched.distanceTo(saved);
    }
}

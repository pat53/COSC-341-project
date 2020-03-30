package com.example.map_test1;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button button = findViewById(R.id.button3);

        //Create listener
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Create objects for all form entries
                TextView search = findViewById(R.id.editText);

                String query = search.getText().toString();

                Geocoder coder = new Geocoder(v.getContext(), Locale.getDefault());
                List<Address> address;

                LatLng searchPoint = null;

                try {
                    address = coder.getFromLocationName(query, 1);
                    if (!(address == null)) {
                        Address location = address.get(0);
                        searchPoint = new LatLng(location.getLatitude(),location.getLongitude());
                    }

                }
                catch (Exception e) {
                    searchPoint = null;
                }

                if(searchPoint==null){
                    Toast.makeText(getApplicationContext(), "Location Not Found", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(v.getContext(), Search.class);

                    Bundle extras = new Bundle();
                    extras.putParcelable("latlng",searchPoint);
                    intent.putExtra("extra", extras);

                    startActivity(intent);
                }
            }
        });

        final Button call = findViewById(R.id.button);
        final Button report = findViewById(R.id.button1);
        final Button help = findViewById(R.id.button2);
        final Button edit = findViewById(R.id.edit);

        //Create listener
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit.class);
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Call.class);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), help.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Report.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

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

        mMap = googleMap;

        // Add a marker in UBCO and move the camera
        LatLng ubco = new LatLng(49.9422639, -119.3981913);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubco, 10f));


        Iterator i = data.iterator();
        while (i.hasNext()) {
            String[] point= (String[]) i.next();

            LatLng latlong = new LatLng(Double.parseDouble(point[3]), Double.parseDouble(point[4]));
            double radius=Math.sqrt(1000*Double.parseDouble(point[2])/Math.PI);

            String newDate = point[5];

            int fire=0;

            if(point[1].equals("Under Control")){
                fire=R.drawable.fire1;
            }
            else if(point[1].equals("Being Held")){
                fire=R.drawable.fire2;

            }
            else if(point[1].equals("Out of Control")){
                fire=R.drawable.fire3;
            }


            mMap.addMarker(new MarkerOptions()
                    .position(latlong)
                    .title("Fire " + point[0])
                    .icon(BitmapDescriptorFactory.fromResource(fire)))
                    .setSnippet("Size: "+Double.parseDouble(point[2])+" Hectors, Date: "+newDate+", Status: "+point[1]);

            CircleOptions circle = new CircleOptions()
                    .center(latlong)
                    .radius(radius)
                    .strokeWidth(3f)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(70,150, 50, 50));

            mMap.addCircle(circle);
        }
    }
}

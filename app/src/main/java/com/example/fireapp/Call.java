package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Call extends AppCompatActivity {

    public static final int REQUEST_CALL = 1;
    Button emergency;
    Button report;
    CheckBox emergencyConfirm;
    CheckBox reportConfirm;

    //test
    Button page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        emergency = findViewById(R.id.emergency);
        report = findViewById(R.id.report);
        emergencyConfirm = findViewById(R.id.confirmEmergency);
        reportConfirm = findViewById(R.id.reportConfirm);

        //test
        page = findViewById(R.id.page);

        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Call.this, Report.class);
                startActivity(intent);
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emergencyConfirm.isChecked()) {
                    makeEmergencyCall();
                }else{
                    Toast.makeText(getBaseContext(), "You must confirm your call request", Toast.LENGTH_SHORT).show();
                }
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportConfirm.isChecked()) {
                    makeReportCall();
                }
                else{
                    Toast.makeText(getBaseContext(), "You must confirm your call request", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeEmergencyCall() {
        String number = "123456789";
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(Call.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Call.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    private void makeReportCall() {
        String number = "123456789";
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(Call.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Call.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeEmergencyCall();
            } else {
                Toast.makeText(this, "Call request failed", Toast.LENGTH_SHORT).show();
            }
        }

    }



}


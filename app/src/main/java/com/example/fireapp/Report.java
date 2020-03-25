package com.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Calendar;

public class Report extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView date;
    String s = "";
    Button submit;
    TextView size;
    static int id = 1;
    TextView latitude;
    TextView longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        date = findViewById(R.id.date);
        size = findViewById(R.id.size);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        submit = findViewById(R.id.submit);

        Spinner div = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        div.setAdapter(a1);

        div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                s = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isValid(Double.parseDouble(latitude.getText().toString()),Double.parseDouble(longitude.getText().toString()))){
                    String filename = "test.txt";
                    String fileContents = id + "," + s + "," + size.getText().toString() + "," + date.getText().toString() + "," + latitude.getText().toString() + "," + longitude.getText().toString() + "\n";
                    id++;
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(filename, Context.MODE_APPEND);
                        outputStream.write(fileContents.getBytes());
                        outputStream.close();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getBaseContext(), "Wrong input for latitude or longitude", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.selectDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }
    public boolean isValid(double lat, double lng){
        if(lat < -90 || lat > 90) {
            return false;
        } else if(lng < -180 || lng > 180) {
            return false;
        }
        return true;
    }
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String d = (month+1) + "/" + dayOfMonth + "/" + year;
        date.setText(d);
}

}

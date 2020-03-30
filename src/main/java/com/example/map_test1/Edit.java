package com.example.map_test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Iterator;

public class Edit extends AppCompatActivity {
    Button home;

    Button back;
    Button next;

    TextView name1;
    TextView name2;
    TextView name3;

    TextView loc1;
    TextView loc2;
    TextView loc3;
    ArrayList<String[]> data;
    int pageNum;

    Button edit1;
    Button edit2;
    Button edit3;

    Button delete1;
    Button delete2;
    Button delete3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        home = findViewById(R.id.home);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name4);

        loc1 = findViewById(R.id.location1);
        loc2 = findViewById(R.id.location2);
        loc3 = findViewById(R.id.location4);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit4);

        delete1 = findViewById(R.id.delete1);
        delete2 = findViewById(R.id.delete2);
        delete3 = findViewById(R.id.delete4);

        pageNum=0;

        data = new ArrayList<>();

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

        editContent();

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(pageNum==0){
                    pageNum= ((int) Math.ceil(((double) data.size())/3.0))-1;
                }
                else{
                    pageNum--;
                }

                editContent();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(pageNum==(((int) Math.ceil(((double) data.size())/3.0))-1)){
                    pageNum= 0;
                }
                else{
                    pageNum++;
                }

                editContent();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        delete1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContent(pageNum*3);
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContent(pageNum*3+1);
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        delete3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContent(pageNum*3+2);
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        edit1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), editField.class);

                Bundle extras = new Bundle();
                extras.putInt("line",pageNum*3);
                intent.putExtra("extra", extras);

                startActivity(intent);
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), editField.class);

                Bundle extras = new Bundle();
                extras.putInt("line",pageNum*3+1);
                intent.putExtra("extra", extras);

                startActivity(intent);
            }
        });

        edit3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), editField.class);

                Bundle extras = new Bundle();
                extras.putInt("line",pageNum*3+2);
                intent.putExtra("extra", extras);

                startActivity(intent);
            }
        });
    }

    public void editContent (){
        if(data.size()>(pageNum*3)){
            name1.setVisibility(View.VISIBLE);
            loc1.setVisibility(View.VISIBLE);
            edit1.setVisibility(View.VISIBLE);
            delete1.setVisibility(View.VISIBLE);

            name1.setText("Name: "+data.get(pageNum*3)[0]);
            loc1.setText("Location: "+data.get(pageNum*3)[3] + ", " +data.get(pageNum*3)[4]);
        }
        else{
            name1.setVisibility(View.INVISIBLE);
            loc1.setVisibility(View.INVISIBLE);
            edit1.setVisibility(View.INVISIBLE);
            delete1.setVisibility(View.INVISIBLE);
        }

        if(data.size()>(pageNum*3+1)){
            name2.setVisibility(View.VISIBLE);
            loc2.setVisibility(View.VISIBLE);
            edit2.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);

            name2.setText("Name: "+data.get(pageNum*3+1)[0]);
            loc2.setText("Location: "+data.get(pageNum*3+1)[3] + ", " +data.get(pageNum*3+1)[4]);
        }
        else{
            name2.setVisibility(View.INVISIBLE);
            loc2.setVisibility(View.INVISIBLE);
            edit2.setVisibility(View.INVISIBLE);
            delete2.setVisibility(View.INVISIBLE);
        }

        if(data.size()>(pageNum*3+2)){
            name3.setVisibility(View.VISIBLE);
            loc3.setVisibility(View.VISIBLE);
            edit3.setVisibility(View.VISIBLE);
            delete3.setVisibility(View.VISIBLE);

            name3.setText("Name: "+data.get(pageNum*3+2)[0]);
            loc3.setText("Location: "+data.get(pageNum*3+2)[3] + ", " +data.get(pageNum*3+2)[4]);
        }
        else{
            name3.setVisibility(View.INVISIBLE);
            loc3.setVisibility(View.INVISIBLE);
            edit3.setVisibility(View.INVISIBLE);
            delete3.setVisibility(View.INVISIBLE);
        }
    }

    public void deleteContent (int lineNum){
        data.remove(lineNum);
        String filename = "data.txt";
        File dir = getFilesDir();
        File file = new File(dir, filename);
        boolean deleted = file.delete();

        if(deleted){
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(filename, Context.MODE_APPEND);

                Iterator i = data.iterator();
                while (i.hasNext()) {
                    String[] point= (String[]) i.next();
                    String fileContents=point[0]+","+point[1]+","+point[2]+","+point[3]+","+point[4]+","+point[5]+"\n";
                    outputStream.write(fileContents.getBytes());
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

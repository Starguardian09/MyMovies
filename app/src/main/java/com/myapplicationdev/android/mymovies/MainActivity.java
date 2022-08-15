package com.myapplicationdev.android.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnRetrieve;
    EditText etTitle;
    EditText etGenre;
    EditText etYear;
    ArrayList<Movies> al;
    Spinner spinGenre;
    ListView lv;
    ArrayAdapter<Movies> aa;
    Movies data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize the variables with UI here
        btnAdd = findViewById(R.id.btnAdd);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        lv = findViewById(R.id.lv);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinGenre = findViewById(R.id.spinnerGenre);
        //rgContent = findViewById(R.id.groupradio);
        //star1 = findViewById(R.id.radioButton1);
        //star2 = findViewById(R.id.radioButton2);
        //star3 = findViewById(R.id.radioButton3);
        //star4 = findViewById(R.id.radioButton4);
        //star5 = findViewById(R.id.radioButton5);
        Intent i = getIntent();
        data = (Movies) i.getSerializableExtra("data");


        al = new ArrayList<Movies>();
        aa = new ArrayAdapter<Movies>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

//        String movieRatingS = "";





        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataTitle  = etTitle.getText().toString();
                String dataGenre = etGenre.getText().toString();
                int dataYear = Integer.parseInt(etYear.getText().toString());
                String movieRating = spinGenre.getSelectedItem().toString();

                Log.d("result",movieRating+"");
//
//                Log.d("result",data3+"")

                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(dataTitle,dataGenre,dataYear,movieRating);

                if (inserted_id != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, List.class);
                startActivity(i);

            }
        });

    }

}
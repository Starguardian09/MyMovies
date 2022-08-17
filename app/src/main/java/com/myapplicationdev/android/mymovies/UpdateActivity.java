package com.myapplicationdev.android.mymovies;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    TextView tvTitle, tvMovieID, tvYear, tvGenre, tvrating, tvID;
    EditText Title, Genre, Year;
    Spinner spinRating;
    Button btnUpdate, btnDelete, btnCancel;
    Movie data;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        tvID = findViewById(R.id.tvID);
        tvMovieID = findViewById(R.id.tvMovieID);
        tvrating = findViewById(R.id.tvrating);
        tvTitle = findViewById(R.id.tvMovie);
        tvYear = findViewById(R.id.tvyear);
        tvGenre = findViewById(R.id.tvGenre);
        Title = findViewById(R.id.etMovie);
        Genre = findViewById(R.id.etGenre);
        Year = findViewById(R.id.etyear);
        spinRating = findViewById(R.id.spinRating);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");
        tvID.setText("Movie ID : " + '\n' + data.getId() + "");

        Title.setText(data.getTitle());
        Genre.setText(data.getGenre());
        Year.setText(data.getYear() + "");

        if (data.getRating().equals("G")) {
            int position = 0;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("PG")) {
            int position = 1;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("PG13")) {
            int position = 2;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("NC16")) {
            int position = 3;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("M18")) {
            int position = 4;
            spinRating.setSelection(position);

        } else if (data.getRating().equals("R21")) {
            int position = 5;
            spinRating.setSelection(position);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        DBHelper dbh = new DBHelper(UpdateActivity.this);

                        if ((!Title.getText().toString().equals("")) && (!Genre.getText().toString().equals("")) && (!Year.getText().toString().equals(""))) {
                            data.setTitle(Title.getText().toString());
                            data.setGenre(Genre.getText().toString());
                            data.setYear(Integer.parseInt(Year.getText().toString()));
                            data.setRating(spinRating.getSelectedItem().toString());

                            int year = Integer.parseInt(Year.getText().toString());
                            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                            if (year >= 1888 && year <= currentYear) {
                                dbh.updateMovie(data);
                                dbh.close();
                                finish();

                                Toast.makeText(UpdateActivity.this, data.getTitle() + " has been updated!", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(UpdateActivity.this, "Please enter a year after 1888 and before the current year", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(UpdateActivity.this, "Please fill in all fields in the form correctly", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder myBuilder = new AlertDialog.Builder(UpdateActivity.this);

                //set the dialog details
                myBuilder.setTitle("DANGER");
                myBuilder.setMessage("Are you sure you want to delete the movie " + data.getTitle());
                myBuilder.setCancelable(false);  //close button

                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        DBHelper dbh = new DBHelper(UpdateActivity.this);
                        int id = data.getId();
                        Log.d("Movie id: ", id + "");

                        dbh.deleteMovie(data.getId());
                        finish();

                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        finish();   //end the activity
                    }
                });
    }
}
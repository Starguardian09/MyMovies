package com.myapplicationdev.android.mymovies;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

//import android.graphics.Movie;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    TextView tvID;
    EditText etNewTitle, etNewGenre, etNewYear;
    Button btnUpdate, btnDelete, btnCancel;
    RadioButton rbEdit,starEdit1,starEdit2,starEdit3,starEdit4,starEdit5;
    RadioGroup rgEditContent;
    Movie data;
    Spinner spinGenreEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //initialize the variables with UI here

        tvID = findViewById(R.id.tvID);
        etNewTitle = findViewById(R.id.etMovieTitle);
        etNewGenre = findViewById(R.id.etGenreName);
        etNewYear = findViewById(R.id.etNewYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        spinGenreEdit = findViewById(R.id.spinnerGenreEdit);



        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        tvID.setText(data.getId() +"");
        etNewTitle.setText(data.getTitle());
        etNewGenre.setText(data.getGenre());
        etNewYear.setText(data.getYear()+"");
        Log.d("data.getRating().equals",data.getRating());

        spinGenreEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //new
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if (data.getRating().equals("G")) {
                    int position = 0;
                    spinGenreEdit.setSelection(position);

                } else if (data.getRating().equals("PG")) {
                    int position = 1;
                    spinGenreEdit.setSelection(position);

                } else if (data.getRating().equals("PG13")) {
                    int position = 2;
                    spinGenreEdit.setSelection(position);

                } else if (data.getRating().equals("NC16")) {
                    int position = 3;
                    spinGenreEdit.setSelection(position);

                } else if (data.getRating().equals("M18")) {
                    int position = 4;
                    spinGenreEdit.setSelection(position);

                } else if (data.getRating().equals("R21")) {
                    int position = 5;
                    spinGenreEdit.setSelection(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(UpdateActivity.this);
                data.setTitle(etNewTitle.getText().toString());
                data.setGenre(etNewGenre.getText().toString());
                data.setYear(Integer.parseInt(etNewYear.getText().toString()));
                String movieRatingE = spinGenreEdit.getSelectedItem().toString();

                data.setRating(movieRatingE);
                Log.d("rating",movieRatingE);

                dbh.updateMovie(data);
                dbh.close();
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(UpdateActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie " + data.getTitle());
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    }
                });

                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DBHelper dbh = new DBHelper(UpdateActivity.this);
                        int result = dbh.deleteMovie(data.getId());
                        Log.d("Result", result + "");
                        finish();
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
            //test

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(UpdateActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes");
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });

                myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        finish();
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}
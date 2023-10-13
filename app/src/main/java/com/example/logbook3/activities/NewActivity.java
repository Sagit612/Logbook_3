package com.example.logbook3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logbook3.R;
import com.example.logbook3.database.AppDatabase;
import com.example.logbook3.models.Person;
import com.google.android.material.button.MaterialButton;

public class NewActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "example")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        MaterialButton saveDetailsButton = findViewById(R.id.saveDetailsButton);
        MaterialButton goBackButton = findViewById(R.id.goBackButton);

        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void saveDetails() {
        // Creates an object of our helper class
//        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get references to the EditText views and read their content
        EditText nameTxt = findViewById(R.id.nameText);
        EditText dobTxt = findViewById(R.id.dobText);
        EditText emailTxt = findViewById(R.id.emailText);

        String name = nameTxt.getText().toString();
        String dob = dobTxt.getText().toString();
        String email = emailTxt.getText().toString();

        Person person = new Person();
        person.name = name;
        person.dob = dob;
        person.email = email;

        // Calls the insertDetails method we created
        long personId = appDatabase.personDao().insertPerson(person);

//        // Shows a toast with the automatically generated id
        Toast.makeText(this, "Person has been created with id: " + personId,
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
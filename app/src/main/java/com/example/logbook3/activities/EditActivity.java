package com.example.logbook3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logbook3.R;
import com.example.logbook3.database.AppDatabase;
import com.example.logbook3.models.Person;
import com.google.android.material.button.MaterialButton;

public class EditActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    EditText nameTxt, dobTxt, emailTxt;
    MaterialButton saveDetailsButton, goBackButton;

//    String prevName, prevDob, prevEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "example")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra("person_data");
        nameTxt = findViewById(R.id.nameEditText);
        dobTxt = findViewById(R.id.dobEditText);
        emailTxt =findViewById(R.id.emailEditText);
        nameTxt.setText(person.name);
        dobTxt.setText(person.dob);
        emailTxt.setText(person.email);
        saveDetailsButton = findViewById(R.id.saveDetailsButton);
        goBackButton = findViewById(R.id.goBackButton);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isTrue = nameTxt.getText().toString().equals(person.name)
                        && dobTxt.getText().toString().equals(person.dob)
                        && emailTxt.getText().toString().equals(person.email);
                if (isTrue) {
                    finish();
                } else {
                    saveDetails(person);
                }
            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void saveDetails(Person person) {
        String name = nameTxt.getText().toString();
        String dob = dobTxt.getText().toString();
        String email = emailTxt.getText().toString();

        // Check if data is actually updated
        if (!name.equals(person.name) || !dob.equals(person.dob) || !email.equals(person.email)) {
            person.name = name;
            person.dob = dob;
            person.email = email;

            long personId = appDatabase.personDao().updatePerson(person);

            Toast.makeText(this, "Person has been updated with id: " + personId, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("person_data", person);
            startActivity(intent);
        } else {
            // No changes, finish the activity
            finish();
        }
    }
}
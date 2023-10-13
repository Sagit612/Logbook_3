package com.example.logbook3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.logbook3.R;
import com.example.logbook3.database.AppDatabase;
import com.example.logbook3.models.Person;
import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    TextView nameTxt, dobTxt, emailTxt;
    MaterialButton editButton, goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "example")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra("person_data");
        nameTxt = findViewById(R.id.nameText);
        dobTxt = findViewById(R.id.dobText);
        emailTxt =findViewById(R.id.emailText);
        nameTxt.setText(person.name);
        dobTxt.setText(person.dob);
        emailTxt.setText(person.email);
        goBackButton = findViewById(R.id.goBackButton);
        editButton = findViewById(R.id.editButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMain();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDetail(person);
            }
        });

    }

    private void navigateToDetail(Person person) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("person_data", person);
        startActivity(intent);
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
package com.example.logbook3.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logbook3.ContactAdapter;
import com.example.logbook3.R;
import com.example.logbook3.database.AppDatabase;
import com.example.logbook3.models.Person;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnDeleteClickListener{

    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    TextView detailsTxt;
    List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "example")
                .allowMainThreadQueries()
                .build();

//        detailsTxt = findViewById(R.id.detailsText);
//        List<Person> persons = appDatabase.personDao().getAllPerson();
//        StringBuilder detailsBuilder = new StringBuilder();
//        for (Person person: persons) {
//            detailsBuilder.append(person.person_id).append(" ")
//                    .append(person.name).append(" ")
//                    .append(person.dob).append(" ")
//                    .append(person.email).append("\n");
//        }
//        // Display persons
//        detailsTxt.setText(detailsBuilder.toString());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Person> persons = appDatabase.personDao().getAllPerson();
        adapter = new ContactAdapter(persons, this);
        recyclerView.setAdapter(adapter);

        MaterialButton addPersonButton = (MaterialButton) findViewById(R.id.addPersonButton);
        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigates user to NewActivity
                navigateToNew();
            }
        });
    }

    private void navigateToPersonalDetail () {

    }

    private void navigateToNew () {
        Intent intent = new Intent(MainActivity.this, NewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Person person) {
//        new AlertDialog.Builder(MainActivity.this)
//                .setTitle("Delete Contact")
//                .setMessage("Are you sure you want to delete this contact?")
//                .setPositiveButton("Delete", (dialog, which) -> {
//                    // Remove from the database
//                    appDatabase.personDao().deletePerson(person);
//
//                    // Update the list
//                    persons.remove(person);
//                    adapter.notifyDataSetChanged();
//                })
//                .setNegativeButton("Cancel", null)
//                .show();
        long personId = person.person_id;
        appDatabase.personDao().deletePerson(person);
        Toast.makeText(this, "Person has been deleted with id: " + personId,
                Toast.LENGTH_LONG
        ).show();

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
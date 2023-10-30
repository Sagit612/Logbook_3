package com.example.logbook3.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.logbook3.ImageAdapter;
import com.example.logbook3.R;
import com.example.logbook3.database.AppDatabase;
import com.example.logbook3.models.Person;
import com.google.android.material.button.MaterialButton;

public class EditActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    EditText nameTxt, dobTxt, emailTxt;
    ImageView avatar;
    MaterialButton saveDetailsButton, goBackButton, editImageButton;
    private int selectedAvatarResourceId = -1;

//    String prevName, prevDob, prevEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "examples")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra("person_data");
        nameTxt = findViewById(R.id.nameEditText);
        dobTxt = findViewById(R.id.dobEditText);
        emailTxt =findViewById(R.id.emailEditText);
        avatar = findViewById(R.id.avatar);
        nameTxt.setText(person.name);
        dobTxt.setText(person.dob);
        emailTxt.setText(person.email);
        avatar.setImageResource(Integer.parseInt(person.image));
        saveDetailsButton = findViewById(R.id.saveDetailsButton);
        goBackButton = findViewById(R.id.goBackButton);
        editImageButton = findViewById(R.id.editImageButton);

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSelectionDialog();
            }
        });
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

    public void setSelectedAvatarResourceId(int resourceId) {
        selectedAvatarResourceId = resourceId;
    }


    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_image_grid, null);
        builder.setView(view);

        TypedArray images = getResources().obtainTypedArray(R.array.image_array);

        GridView gridView = view.findViewById(R.id.imageGridView);
        gridView.setAdapter(new ImageAdapter(this, images));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle image selection
                Drawable selectedImage = images.getDrawable(position);
                // Do something with the selected image

                // Dismiss the dialog
//                alertDialog.dismiss();
            }
        });
        builder.setTitle("Choose an image")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedResourceId = ((ImageAdapter) gridView.getAdapter()).getSelectedResourceId();

                        if (selectedResourceId != -1) {
                            avatar = findViewById(R.id.avatar);
                            avatar.setImageResource(selectedResourceId);
                            setSelectedAvatarResourceId(selectedResourceId);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when the user clicks Cancel
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
            person.image = String.valueOf(selectedAvatarResourceId);

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
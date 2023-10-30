package com.example.logbook3.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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

public class NewActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    ImageView avatar;
    private int selectedAvatarResourceId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "examples")
                .allowMainThreadQueries() // For simplicity, don't use this in production
                .build();
        MaterialButton saveDetailsButton = findViewById(R.id.saveDetailsButton);
        MaterialButton goBackButton = findViewById(R.id.goBackButton);
        MaterialButton addImageButton = findViewById(R.id.addImageButton);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSelectionDialog();
            }
        });

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
        person.image = String.valueOf(selectedAvatarResourceId);

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
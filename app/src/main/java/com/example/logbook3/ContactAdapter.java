package com.example.logbook3;

import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logbook3.activities.DetailActivity;
import com.example.logbook3.activities.EditActivity;
import com.example.logbook3.models.Person;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Person> persons;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Person person);
    }
    public ContactAdapter(List<Person> persons) {
        this.persons = persons;
    }

    public ContactAdapter(List<Person> persons, OnDeleteClickListener onDeleteClickListener) {
        this.persons = persons;
        this.onDeleteClickListener = onDeleteClickListener;
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_card, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Person person = persons.get(position);
        holder.personName.setText(person.name);
        holder.imageView.setImageResource(Integer.parseInt(person.image));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("person_data", person);
            view.getContext().startActivity(intent);
        });
        holder.deleteButton.setOnClickListener(view -> {
            if(onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(persons.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        MaterialButton deleteButton;
        ImageView imageView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            imageView = itemView.findViewById(R.id.avatar);
        }
    }
}



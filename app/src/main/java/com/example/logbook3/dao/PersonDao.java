package com.example.logbook3.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.logbook3.models.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    long insertPerson(Person person);

    @Update
    int updatePerson(Person person);

    @Delete
    void deletePerson(Person person);


    @Query("SELECT * FROM persons ORDER BY name")
    List<Person> getAllPerson();

}

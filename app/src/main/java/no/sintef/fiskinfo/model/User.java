package no.sintef.fiskinfo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    private int id;
    private String name;
    private String lastName;

}

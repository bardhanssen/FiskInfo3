package no.sintef.fiskinfo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SnapUser {
    @PrimaryKey
    private int id;
    private String phoneNumber;
    private String name;

}

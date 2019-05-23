package no.sintef.fiskinfo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SnapUser {
    @PrimaryKey
    public long id;
    public String email;
    public String name;

    public SnapUser() {
    }

    public SnapUser(long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}

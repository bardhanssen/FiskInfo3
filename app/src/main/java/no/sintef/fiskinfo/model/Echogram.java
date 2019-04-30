package no.sintef.fiskinfo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URL;
import java.util.Date;

@Entity
public class Echogram {
    @PrimaryKey
    public int uid;

    Date timestamp;
    String location;
    String biomass;

    URL echogramURL;
}

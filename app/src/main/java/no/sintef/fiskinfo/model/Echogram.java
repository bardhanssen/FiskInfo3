package no.sintef.fiskinfo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URL;
import java.util.Date;

@Entity
public class Echogram {
    @PrimaryKey
    public long uid;

    public Date timestamp;
    public String location;
    public String biomass;
    public String source;

    public URL echogramURL;
}

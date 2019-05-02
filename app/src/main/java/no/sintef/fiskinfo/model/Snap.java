package no.sintef.fiskinfo.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Snap {
    @PrimaryKey
    public long uid;

    //public int echogramUid;

    public String sender;
    public String[] receivers;

    public String title;
    public String comment;

    public Date sendTimestamp;

    public Echogram echogram;

    public Echogram getEchogram() {
        return echogram;
    }
}

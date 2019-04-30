package no.sintef.fiskinfo.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Snap {
    @PrimaryKey
    public int uid;

    public int echogramUid;

    String sender;
    String[] receivers;

    String title;
    String comment;

    Date sendTimestamp;
}

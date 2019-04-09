package no.sintef.fiskinfo.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Snap {
    @PrimaryKey
    public int uid;

    String sender;
    String[] receivers;

    Date timestamp;
    String location;
    String biomass;

    byte[] thumbnail;
    String echogram;
    String localEchogram;


}

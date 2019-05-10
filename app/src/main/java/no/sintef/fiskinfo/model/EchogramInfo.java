package no.sintef.fiskinfo.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class EchogramInfo {
    @PrimaryKey
    public long uid;

    public Date timestamp;
    public String latitude;
    public String longitude;
    public String biomass;
    public String source;

    public Uri echogramURL;
}

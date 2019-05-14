package no.sintef.fiskinfo.model;

import java.util.ArrayList;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SnapMessage {
    @PrimaryKey
    public long uid;

    //public int echogramUid;

    public String sender;
    public String receivers;
    //public ArrayList<String> receivers;

    public String title;
    public String comment;

    public Date sendTimestamp;

    public EchogramInfo echogramInfo;
    public boolean sharePublicly;

    public EchogramInfo getEchogramInfo() {
        return echogramInfo;
    }
}

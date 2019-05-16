package no.sintef.fiskinfo.model;

import java.util.ArrayList;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SnapMessage {
    @PrimaryKey
    public long id;

    public long echogramInfoID;

    public long senderID;
    public long receiverID;
    //public ArrayList<String> receiverID;

    public String title;
    public String comment;

    public Date sendTimestamp;

    public EchogramInfo echogramInfo;
    public boolean sharePublicly;

    public EchogramInfo getEchogramInfo() {
        return echogramInfo;
    }
}

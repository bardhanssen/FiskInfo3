package no.sintef.fiskinfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SnapMessage {
    @PrimaryKey
    public long id;
    public long senderID;

    public String title;
    public String comment;
    public Date sendTimestamp;
    public boolean sharePublicly;

    public long echogramInfoID;

    //public ArrayList<String> receiverID;

    public EchogramInfo echogramInfo;
    public SnapUser sender;
    public List<SnapReceiver> receivers;

    public EchogramInfo getEchogramInfo() {
        return echogramInfo;
    }
}

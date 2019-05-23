package no.sintef.fiskinfo.repository.dummy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.sintef.fiskinfo.model.SnapMessage;
import no.sintef.fiskinfo.model.SnapReceiver;
import no.sintef.fiskinfo.model.SnapUser;

public class DummySnap {

    public static SnapMessage createSnap(long minutesAgo, String title, String comment, SnapUser sender, String receiver) {
        SnapMessage snap = new SnapMessage();
        snap.id = Math.round(Math.random()*100000000);

        snap.echogramInfo = DummyEchogram.createEchogram(minutesAgo);
        snap.echogramInfoID = snap.echogramInfo.id;
        snap.title = title;
        snap.comment = comment;
        snap.sender = sender;
        snap.receivers = new ArrayList<SnapReceiver>();
        snap.receivers.add(new SnapReceiver(receiver));
        snap.sendTimestamp = new Date();
        return snap;
    }

    public static ArrayList<SnapUser> USERS;

    public static SnapUser getUser(int id) {
        if (USERS == null) {
            USERS = new ArrayList<>();
            USERS.add(new SnapUser(0, "per@fiskinfo.no", "Per"));
            USERS.add(new SnapUser(1, ME, "Ola"));
            USERS.add(new SnapUser(2, "jens@fiskinfo.no", "Jens"));
        }
        return USERS.get(id);
    }

//    public static String[] me() {
//        return new String[]{"Me"};
//    }
    public static String ME = "ola@fiskinfo.no";

    public static LiveData<List<SnapMessage>> getDummyInboxSnaps() {
        MutableLiveData<List<SnapMessage>> listHolder = new MutableLiveData<List<SnapMessage>>();
        List<SnapMessage> list = new ArrayList<>();
        list.add(createSnap(0, "Mer her", "Det er mer en nok å ta av, men kvota er full. Kanskje du rekker frem?", getUser(0), ME)); //"Ola", me()));
        list.add(createSnap(12, "Torsk?", "", getUser(0), ME)); //"Ola", me()));
        list.add(createSnap(41, "Ser her da!", "",  getUser(2), ME )); //"Peder", new String[]{"Me", "Hans"}));
//        list.add(createSnap(60*24 + 3, "Mer enn nok å ta av", "Har du husket du å tippe?", "Per", me()));
//        list.add(createSnap(60*24 + 97, "Ubåt?", "", "Ola", me()));
        listHolder.setValue(list);
        return listHolder;
    }
}

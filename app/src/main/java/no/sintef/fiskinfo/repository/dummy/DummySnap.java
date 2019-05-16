package no.sintef.fiskinfo.repository.dummy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.sintef.fiskinfo.model.SnapMessage;

public class DummySnap {

    public static SnapMessage createSnap(long minutesAgo, String title, String comment, long sender, long receiver) {
        SnapMessage snap = new SnapMessage();
        snap.id = Math.round(Math.random()*100000000);

        snap.echogramInfo = DummyEchogram.createEchogram(minutesAgo);
        snap.echogramInfoID = snap.echogramInfo.id;
        snap.title = title;
        snap.comment = comment;
        snap.senderID = sender;
        snap.receiverID = receiver; //receivers[0]; ///ArrayList<String>(Arrays.asList(receiverID));
        snap.sendTimestamp = new Date();
        return snap;
    }


    public static String[] me() {
        return new String[]{"Me"};
    }

    public static LiveData<List<SnapMessage>> getDummyInboxSnaps() {
        MutableLiveData<List<SnapMessage>> listHolder = new MutableLiveData<List<SnapMessage>>();
        List<SnapMessage> list = new ArrayList<>();
        list.add(createSnap(0, "Mer her", "Det er mer en nok å ta av, men kvota er full. Kanskje du rekker frem?", 0, 1)); //"Ola", me()));
        list.add(createSnap(12, "Torsk?", "", 0, 1)); //"Ola", me()));
        list.add(createSnap(41, "Ser her da!", "",  2, 1 )); //"Peder", new String[]{"Me", "Hans"}));
//        list.add(createSnap(60*24 + 3, "Mer enn nok å ta av", "Har du husket du å tippe?", "Per", me()));
//        list.add(createSnap(60*24 + 97, "Ubåt?", "", "Ola", me()));
        listHolder.setValue(list);
        return listHolder;
    }
}

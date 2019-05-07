package no.sintef.fiskinfo.repository.dummy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.model.Snap;

public class DummySnap {

    public static Snap createSnap(long minutesAgo, String title, String comment, String sender, String[] receivers) {
        Snap snap = new Snap();
        snap.uid = Math.round(Math.random()*100000000);

        snap.echogram = DummyEchogram.createEchogram(minutesAgo);
        snap.title = title;
        snap.comment = comment;
        snap.sender = sender;
        snap.receivers = receivers;
        snap.sendTimestamp = new Date();
        return snap;
    }


    public static String[] me() {
        return new String[]{"Me"};
    }

    public static LiveData<List<Snap>> getDummyInboxSnaps() {
        MutableLiveData<List<Snap>> listHolder = new MutableLiveData<List<Snap>>();
        List<Snap> list = new ArrayList<>();
        list.add(createSnap(0, "Mer her", "", "Ola", me()));
        list.add(createSnap(12, "Torsk?", "", "Ola", me()));
        list.add(createSnap(41, "Ser her da!", "", "Peder", new String[]{"Me", "Hans"}));
        list.add(createSnap(60*24 + 3, "Mer enn nok å ta av", "Har du husket du å tippe?", "Per", me()));
        list.add(createSnap(60*24 + 97, "Ubåt?", "", "Ola", me()));
        listHolder.setValue(list);
        return listHolder;
    }
}

package no.sintef.fiskinfo.repository.dummy;

import androidx.lifecycle.LiveData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.sintef.fiskinfo.model.Echogram;

public class DummyEchogram {
    static long MILLIS_IN_MIN = 60000;

    public static Echogram createEchogram(long minutesOld) {
        Echogram echogram = new Echogram();
        echogram.uid = Math.round(Math.random()*100000000);
        echogram.timestamp = new Date(System.currentTimeMillis() - minutesOld*MILLIS_IN_MIN);
        echogram.biomass = "400.0";
        try {
            echogram.echogramURL = new URL("https://www.sintef.no");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        echogram.location = "N63" + "\u2103" + "24\'48\" E10" + "\u2103" + "24\'33\"";
        echogram.source = "EK80";
        return echogram;
    }

//    public static LiveData<List<Echogram>> getDummyEchogram() {
    public static List<Echogram> getDummyEchograms() {
        List<Echogram> list = new ArrayList<>();
        list.add(createEchogram(0));
        list.add(createEchogram(23));
        list.add(createEchogram(84));
        list.add(createEchogram(60*24 - 45));
        list.add(createEchogram(60*24 + 33));
        return list;
    }
}

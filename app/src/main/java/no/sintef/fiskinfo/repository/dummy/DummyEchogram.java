package no.sintef.fiskinfo.repository.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.sintef.fiskinfo.model.EchogramInfo;

public class DummyEchogram {
    static long MILLIS_IN_MIN = 60000;

    public static EchogramInfo createEchogram(long minutesOld) {
        EchogramInfo echogram = new EchogramInfo();
        echogram.id = Math.round(Math.random()*100000000);
        echogram.timestamp = new Date(System.currentTimeMillis() - minutesOld*MILLIS_IN_MIN);
        echogram.biomass = "400.0";
        echogram.echogramUrl = "https://www.sintef.no";
        echogram.latitude = "N63" + (char) 0x00B0 + "24\'48\"";
        echogram.longitude = "E10" + (char) 0x00B0 + "24\'33\" ";
        echogram.userID = 1;
        echogram.source = "EK80";
        return echogram;
    }

//    public static LiveData<List<EchogramInfo>> getDummyEchogram() {
    public static List<EchogramInfo> getDummyEchograms() {
        List<EchogramInfo> list = new ArrayList<>();
        list.add(createEchogram(0));
        list.add(createEchogram(23));
        list.add(createEchogram(84));
        list.add(createEchogram(60*24 - 45));
        list.add(createEchogram(60*24 + 33));
        return list;
    }
}

package no.sintef.fiskinfo.repository;


import java.util.List;

import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.repository.dummy.DummyEchogram;

public class EchogramRepository {
//    public LiveData<List<EchogramInfo>> getEchograms() {
    public List<EchogramInfo> getEchograms() {
        return DummyEchogram.getDummyEchograms();
    }
}

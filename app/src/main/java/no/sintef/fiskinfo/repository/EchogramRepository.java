package no.sintef.fiskinfo.repository;


import androidx.lifecycle.LiveData;

import java.util.List;

import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.repository.dummy.DummyEchogram;

public class EchogramRepository {
//    public LiveData<List<Echogram>> getEchograms() {
    public List<Echogram> getEchograms() {
        return DummyEchogram.getDummyEchograms();
    }
}

package no.sintef.fiskinfo.repository;

import java.util.List;

import no.sintef.fiskinfo.model.Snap;
import no.sintef.fiskinfo.repository.dummy.DummySnap;

public class SnapRepository {
    public List<Snap> getInboxSnaps() {
        return DummySnap.getDummyInboxSnaps();
    }

    public List<Snap> getOutboxSnaps() {
        return null; //DummyEchogram.getDummyEchograms();
    }
}

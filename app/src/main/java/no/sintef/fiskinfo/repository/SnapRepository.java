package no.sintef.fiskinfo.repository;

import java.util.ArrayList;
import java.util.List;

import no.sintef.fiskinfo.model.Snap;
import no.sintef.fiskinfo.repository.dummy.DummySnap;

public class SnapRepository {
    static SnapRepository instance;

    public static SnapRepository getInstance() {
        if (instance == null)
            instance = new SnapRepository();
        return instance;
    }

    protected List<Snap> outboxSnaps = new ArrayList<>();

    public List<Snap> getInboxSnaps() {
        return DummySnap.getDummyInboxSnaps();
    }

    public void storeSnap(Snap newSnap) {
        outboxSnaps.add(newSnap);
    }
    public List<Snap> getOutboxSnaps() {
        return outboxSnaps;
    }
}

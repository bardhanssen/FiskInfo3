package no.sintef.fiskinfo.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    protected MutableLiveData<List<Snap>> outboxSnaps;

    public LiveData<List<Snap>> getInboxSnaps() {
        return DummySnap.getDummyInboxSnaps();
    }


    private void initOutbox() {
        outboxSnaps = new MutableLiveData<>();
        outboxSnaps.setValue(new ArrayList<Snap>());
    }

    public void storeSnap(Snap newSnap) {
        if (outboxSnaps == null)
            initOutbox();
        outboxSnaps.getValue().add(newSnap);
    }

    public LiveData<List<Snap>> getOutboxSnaps() {
        if (outboxSnaps == null) {
            outboxSnaps = new MutableLiveData<>();
            outboxSnaps.setValue(new ArrayList<Snap>());
        }
        return outboxSnaps;
    }
}

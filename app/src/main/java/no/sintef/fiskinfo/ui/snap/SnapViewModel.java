package no.sintef.fiskinfo.ui.snap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.model.Snap;
import no.sintef.fiskinfo.repository.SnapRepository;

public class SnapViewModel extends ViewModel {

    //MutableLiveData<Snap> snap;
    //LiveData<Echogram> echogram;
    private MutableLiveData<Snap> selectedSnap = new MutableLiveData<Snap>();
    private LiveData<List<Snap>> inboxSnaps;
    private MutableLiveData<Snap> snapDraft = new MutableLiveData<Snap>();

    public void selectSnap(Snap snap) {
        selectedSnap.setValue(snap);
    }

    public LiveData<Snap> getSelectedSnap() {
        return selectedSnap;
    }

    public void createDraftFrom(Echogram echogram) {
        Snap snap = new Snap();
        snap.echogram = echogram;
        snapDraft.setValue(snap);
    }

    public LiveData<Snap> getDraft() {
        return snapDraft;
    }

    public void sendSnapAndClear() {
        SnapRepository.getInstance().storeSnap(snapDraft.getValue());
        snapDraft.setValue(null);
    }

    public LiveData<List<Snap>> getInboxSnaps() {
        if (inboxSnaps == null) {
            inboxSnaps = SnapRepository.getInstance().getInboxSnaps();
        }
        return inboxSnaps;
    }

}

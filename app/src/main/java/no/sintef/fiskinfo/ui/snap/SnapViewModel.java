package no.sintef.fiskinfo.ui.snap;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.model.SnapMessage;
import no.sintef.fiskinfo.repository.SnapRepository;

public class SnapViewModel extends AndroidViewModel {

    private MutableLiveData<SnapMessage> selectedSnap = new MutableLiveData<SnapMessage>();
    private LiveData<List<SnapMessage>> inboxSnaps;
    private MutableLiveData<SnapMessage> snapDraft = new MutableLiveData<SnapMessage>();

    public SnapViewModel(@NonNull Application application) {
        super(application);
    }

    public void selectSnap(SnapMessage snap) {
        selectedSnap.setValue(snap);
    }

    public LiveData<SnapMessage> getSelectedSnap() {
        return selectedSnap;
    }

    public void createDraftFrom(EchogramInfo echogram) {
        SnapMessage snap = new SnapMessage();
        snap.echogramInfo = echogram;
        snap.echogramInfoID = echogram.id;
        snapDraft.setValue(snap);
    }

    public LiveData<SnapMessage> getDraft() {
        return snapDraft;
    }

    public void sendSnapAndClear() {
        SnapRepository.getInstance(getApplication()).storeSnap(snapDraft.getValue());
        snapDraft.setValue(null);
    }

    public LiveData<List<SnapMessage>> getInboxSnaps() {
        if (inboxSnaps == null) {
            inboxSnaps = SnapRepository.getInstance(getApplication()).getInboxSnaps();
        }
        return inboxSnaps;
    }

    public void refreshInboxContent() {
        SnapRepository.getInstance(getApplication()).refreshInboxContent();
    }
}

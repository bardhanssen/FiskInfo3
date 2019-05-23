package no.sintef.fiskinfo.ui.snap;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.model.SnapMessage;
import no.sintef.fiskinfo.model.SnapReceiver;
import no.sintef.fiskinfo.repository.SnapRepository;

public class SnapViewModel extends AndroidViewModel {

    private MutableLiveData<SnapMessage> selectedSnap = new MutableLiveData<SnapMessage>();
    private LiveData<List<SnapMessage>> inboxSnaps;
    private MutableLiveData<SnapMessage> snapDraft = new MutableLiveData<SnapMessage>();

    public final ObservableField<String> draftSnapReceivers = new ObservableField<String>();

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
        draftSnapReceivers.set("");
    }

    public LiveData<SnapMessage> getDraft() {
        return snapDraft;
    }

    public void sendSnapAndClear() {
        SnapMessage draft = snapDraft.getValue();

        if (draftSnapReceivers.get() != null) {
            List<String> receiverList = Arrays.asList(draftSnapReceivers.get().split(","));
            draft.receivers = new ArrayList<>();
            for (String receiver : receiverList) {
                draft.receivers.add(new SnapReceiver(receiver));
            }
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        draft.senderEmail = prefs.getString(getApplication().getString(R.string.user_identity), "default@fiskinfo.no");

        SnapRepository.getInstance(getApplication()).storeSnap(draft);
        snapDraft.setValue(null);
        draftSnapReceivers.set("");
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

package no.sintef.fiskinfo.ui.snap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.model.Snap;
import no.sintef.fiskinfo.repository.SnapRepository;

public class NewSnapViewModel extends ViewModel {

    //MutableLiveData<Snap> snap;
    //LiveData<Echogram> echogram;
    Snap snap;
    Echogram echogram;

    public void setSelectedEchogram(Echogram echogram) { //LiveData<Echogram> echogram) {
        this.echogram = echogram;
    }

//    public MutableLiveData<Snap> getSnap() {
    public Snap getSnap() {
        if (snap == null) {
            snap = new Snap();
            snap.echogram = echogram;
        }
        return snap;
    }

    public void sendSnapAndClear() {
        SnapRepository.getInstance().storeSnap(snap);
        snap = null;
    }

}

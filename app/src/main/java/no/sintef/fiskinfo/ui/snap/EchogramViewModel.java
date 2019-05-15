package no.sintef.fiskinfo.ui.snap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.model.SnapMessage;
import no.sintef.fiskinfo.repository.SnapRepository;

public class EchogramViewModel extends ViewModel {

    //private MutableLiveData<EchogramInfo> selectedEchogram = new MutableLiveData<EchogramInfo>();
    private LiveData<List<EchogramInfo>> echogramInfos;


    public LiveData<List<EchogramInfo>> getEchogramInfos() {
        if (echogramInfos == null) {
            echogramInfos = SnapRepository.getInstance().getEchogramInfos();
        }
        return echogramInfos;
    }

}

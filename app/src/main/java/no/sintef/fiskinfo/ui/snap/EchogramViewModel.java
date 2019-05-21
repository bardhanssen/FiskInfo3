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

public class EchogramViewModel extends AndroidViewModel {

    //private MutableLiveData<EchogramInfo> selectedEchogram = new MutableLiveData<EchogramInfo>();
    private LiveData<List<EchogramInfo>> echogramInfos;

    public EchogramViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<EchogramInfo>> getEchogramInfos() {
        if (echogramInfos == null) {
            echogramInfos = SnapRepository.getInstance(getApplication()).getEchogramInfos();
        }
        return echogramInfos;
    }

}

package no.sintef.fiskinfo.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import no.sintef.fiskinfo.api.SnapMessageService;
import no.sintef.fiskinfo.model.SnapMessage;
import no.sintef.fiskinfo.repository.dummy.DummySnap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SnapRepository {
    static SnapRepository instance;
    SnapMessageService snapMessageService = null;


    public static SnapRepository getInstance() {
        if (instance == null)
            instance = new SnapRepository();
        return instance;
    }

    protected MutableLiveData<List<SnapMessage>> outboxSnaps;

    public LiveData<List<SnapMessage>> getInboxSnaps() {
        if (snapMessageService == null)
            initService();


        final MutableLiveData<List<SnapMessage>> data = new MutableLiveData<>();

        // This implementation is still suboptimal but better than before.
        // A complete implementation also handles error cases.
        snapMessageService.getSnapMessages().enqueue(new Callback<List<SnapMessage>>() {
            @Override
            public void onResponse(Call<List<SnapMessage>> call, Response<List<SnapMessage>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SnapMessage>> call, Throwable t) {
                data.setValue(DummySnap.getDummyInboxSnaps().getValue());
            }
        });
        return data;
//        return DummySnap.getDummyInboxSnaps();
    }

//    final static String SNAP_FISH_SERVER_URL = "https://10.218.86.229:44387/";
//    final static String SNAP_FISH_SERVER_URL = "http://10.218.69.173:58196/";
    final static String SNAP_FISH_SERVER_URL = "http://10.218.86.229:5002/";


    protected void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SNAP_FISH_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        snapMessageService = retrofit.create(SnapMessageService.class);

    }


    private void initOutbox() {
        outboxSnaps = new MutableLiveData<>();
        outboxSnaps.setValue(new ArrayList<SnapMessage>());
    }

    public void storeSnap(SnapMessage newSnap) {
        if (outboxSnaps == null)
            initOutbox();
        outboxSnaps.getValue().add(newSnap);
    }

    public LiveData<List<SnapMessage>> getOutboxSnaps() {
        if (outboxSnaps == null) {
            outboxSnaps = new MutableLiveData<>();
            outboxSnaps.setValue(new ArrayList<SnapMessage>());
        }
        return outboxSnaps;
    }
}

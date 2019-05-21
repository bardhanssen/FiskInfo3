package no.sintef.fiskinfo.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import no.sintef.fiskinfo.api.SnapMessageService;
import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.model.SnapMessage;
import no.sintef.fiskinfo.repository.dummy.DummyEchogram;
import no.sintef.fiskinfo.repository.dummy.DummySnap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SnapRepository {
    static SnapRepository instance;
    SnapMessageService snapMessageService = null;

    //    final static String SNAP_FISH_SERVER_URL = "https://10.218.86.229:44387/";
//    final static String SNAP_FISH_SERVER_URL = "http://10.218.69.173:58196/";
    final static String DEFAULT_SNAP_FISH_SERVER_URL = "http://10.218.86.229:5002/";

    String snapFishServerUrl = DEFAULT_SNAP_FISH_SERVER_URL;

    public SnapRepository(Context context) {
        updateFromPreferences(context);
    }

    public static SnapRepository getInstance(Context context) {
        if (instance == null)
            instance = new SnapRepository(context);
        return instance;
    }

    protected MutableLiveData<List<SnapMessage>> outboxSnaps;

    final MutableLiveData<List<SnapMessage>> inboxSnaps = new MutableLiveData<>();

    public LiveData<List<SnapMessage>> getInboxSnaps() {
        refreshInboxContent();
        return inboxSnaps;
    }



    protected void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(snapFishServerUrl)
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

        if (snapMessageService == null)
            initService();
        snapMessageService.sendSnapMessage(newSnap).enqueue(new Callback<SnapMessage>() {
            @Override
            public void onResponse(Call<SnapMessage> call, Response<SnapMessage> response) {

            }

            @Override
            public void onFailure(Call<SnapMessage> call, Throwable t) {

            }
        });
        outboxSnaps.getValue().add(newSnap);
    }

    public LiveData<List<SnapMessage>> getOutboxSnaps() {
        if (outboxSnaps == null) {
            outboxSnaps = new MutableLiveData<>();
            outboxSnaps.setValue(new ArrayList<SnapMessage>());
        }
        return outboxSnaps;
    }

    public LiveData<List<EchogramInfo>> getEchogramInfos() {
        if (snapMessageService == null)
            initService();


        final MutableLiveData<List<EchogramInfo>> data = new MutableLiveData<>();

        // This implementation is still suboptimal but better than before.
        // A complete implementation also handles error cases.
        snapMessageService.getEchogramInfos().enqueue(new Callback<List<EchogramInfo>>() {
            @Override
            public void onResponse(Call<List<EchogramInfo>> call, Response<List<EchogramInfo>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<EchogramInfo>> call, Throwable t) {
                data.setValue(DummyEchogram.getDummyEchograms());
            }
        });
        return data;
    }

    public void refreshInboxContent() {
        if (snapMessageService == null)
            initService();

        snapMessageService.getSnapMessages(true).enqueue(new Callback<List<SnapMessage>>() {
            @Override
            public void onResponse(Call<List<SnapMessage>> call, Response<List<SnapMessage>> response) {
                inboxSnaps.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SnapMessage>> call, Throwable t) {
                inboxSnaps.setValue(DummySnap.getDummyInboxSnaps().getValue());
            }
        });
    }


    public void updateFromPreferences(Context context) {
        if (context != null) {
            // Find preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            snapFishServerUrl = prefs.getString("server_address", DEFAULT_SNAP_FISH_SERVER_URL);
            snapMessageService = null;
        }
        refreshInboxContent();
    }

}

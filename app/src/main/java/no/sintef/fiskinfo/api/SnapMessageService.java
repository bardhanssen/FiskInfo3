package no.sintef.fiskinfo.api;

import java.util.List;

import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.model.SnapMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SnapMessageService {
//    @GET("users/{user}/repos")
//    Call<List<SnapMessage>> listRepos(@Path("user") String user);

    @GET("api/snapmessages")
    Call<List<SnapMessage>> getSnapMessages(@Query("withechogram") boolean withEchogram);

    @GET("api/echograminfos")
    Call<List<EchogramInfo>> getEchogramInfos();

    @POST("api/snapmessages")
    Call<SnapMessage> sendSnapMessage(@Body SnapMessage message);

}

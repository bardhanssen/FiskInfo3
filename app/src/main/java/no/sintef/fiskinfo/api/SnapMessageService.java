package no.sintef.fiskinfo.api;

import java.util.List;

import no.sintef.fiskinfo.model.SnapMessage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SnapMessageService {
//    @GET("users/{user}/repos")
//    Call<List<SnapMessage>> listRepos(@Path("user") String user);

    @GET("api/snapmessage")
    Call<List<SnapMessage>> getSnapMessages(@Query("withechogram") boolean withEchogram);

}

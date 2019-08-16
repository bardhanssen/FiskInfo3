package no.sintef.fiskinfo.api

import no.sintef.fiskinfo.model.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BarentswatchTokenService {
    @FormUrlEncoded
    @POST("api/token")
    fun requestToken(
        @Field("grant_type") grant_type: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Token>
}
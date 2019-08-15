package no.sintef.fiskinfo.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
// import com.squareup.okhttp.FormEncodingBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OAuthInterceptor(private val tokenType: String, private val accessToken: String, private val userAgent : String = ""): Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "$tokenType $accessToken")
            .header("User-Agent", "$userAgent")
            .build()

        return chain.proceed(request)
    }
}

class BasicAuthClient<T> (val accessToken : String) {
    private val barentsWatchProdAddress = "https://www.barentswatch.no/"
    private val client =  OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", accessToken,  "FiskInfo/2.0 (Android)"))
        .build()

    val gson = GsonBuilder()
        .setLenient() // consider to remove
        // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") Consider to add
        // .excludeFieldsWithoutExposeAnnotation() Consider to add
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(barentsWatchProdAddress)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}

class UnauthClient<T> () {
    private val barentsWatchPilotAddress = "https://pilot.barentswatch.net/"
    private val barentsWatchProdAddress = "https://www.barentswatch.no/"

    private val client =  OkHttpClient.Builder()
//        .addInterceptor(OAuthInterceptor("Bearer", accessToken,  "FiskInfo/2.0 (Android)"))
        .build()

    val gson = GsonBuilder()
        .setLenient() // consider to remove
        // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") Consider to add
        // .excludeFieldsWithoutExposeAnnotation() Consider to add
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(barentsWatchProdAddress)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}

/*
class Authenticator {
    fun getRequestForAuthentication(mEmail: String, mPassword: String): Request {
        val MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8")
        val client = OkHttpClient()
        val formBody = FormEncodingBuilder()
            .add("grant_type", "password")
            .add("username", mEmail)
            .add("password", mPassword)
            .build()
        return Request.Builder()
            .url(currentPath + "/api/token")
            .header("content-type", "application/x-www-form-urlencoded")
            .post(formBody)
            .build()
    }

    fun getRequestForAuthenticationClientCredentialsFlow(mEmail: String, mPassword: String): Request {
        val MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8")
        val client = OkHttpClient()
        val formBody = FormEncodingBuilder()
            .add("grant_type", "client_credentials")
            .add("client_id", mEmail)
            .add("client_secret", mPassword)
            .build()
        return Request.Builder()
            .url(currentPath + "/api/token")
            .header("content-type", "application/x-www-form-urlencoded")
            .post(formBody)
            .build()
    }
*/

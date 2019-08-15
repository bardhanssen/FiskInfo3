package no.sintef.fiskinfo.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
// import com.squareup.okhttp.FormEncodingBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OAuthInterceptor(private val tokenType: String, private val acceessToken: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

        return chain.proceed(request)
    }
}

class BasicAuthClient<T> (val accessToken : String) {
    private val client =  OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", accessToken))
        .build()

    val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}

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

}
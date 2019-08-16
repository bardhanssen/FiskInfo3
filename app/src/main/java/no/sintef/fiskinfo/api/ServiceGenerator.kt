package no.sintef.fiskinfo.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create a REST service without authentication
 */
fun <S> createService(serviceClass : Class<S>, baseUrl : String) : S {
    val client =  OkHttpClient.Builder()
        .build()

    return createService(serviceClass, baseUrl, client)
}

/**
 * Create a REST service with authentication
 */
fun <S> createService(serviceClass: Class<S>, baseUrl : String, authToken : String) : S {
    val client =  OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", authToken,  "FiskInfo/2.0 (Android)"))
        .build()

    return createService(serviceClass, baseUrl, client)
}

private fun <S> createService(serviceClass: Class<S>, baseUrl : String, client : OkHttpClient) : S {
    val gson = GsonBuilder()
        .setLenient() // consider to remove
        // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") Consider to add
        // .excludeFieldsWithoutExposeAnnotation() Consider to add
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(serviceClass)
}

private class OAuthInterceptor(private val tokenType: String, private val accessToken: String, private val userAgent : String = ""):
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "$tokenType $accessToken")
            .header("User-Agent", "$userAgent")
            .build()

        return chain.proceed(request)
    }
}

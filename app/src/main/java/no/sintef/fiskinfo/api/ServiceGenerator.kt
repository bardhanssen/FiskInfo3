package no.sintef.fiskinfo.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializer
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat


/**
 * Create a REST service without authentication
 */
fun <S> createService(serviceClass : Class<S>, baseUrl : String) : S {
    val client =  OkHttpClient.Builder()
        .addInterceptor(JSONHeaderInterceptor("FiskInfo/2.0 (Android)"))
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
        // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") //Consider to add
        // .excludeFieldsWithoutExposeAnnotation() Consider to add
        .registerTypeAdapter(
            Date::class.java,  DateTypeDeserializer())
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(serviceClass)
}


class DateTypeDeserializer : JsonDeserializer<Date> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement,
        typeOF: Type,
        context: JsonDeserializationContext
    ): Date {
        for (format in DATE_FORMATS) {
            try {
                return SimpleDateFormat(format).parse(jsonElement.asString) //SimpleDateFormat(format, Locale.US).parse(jsonElement.asString)
            } catch (e: ParseException) {
            }
        }
        throw JsonParseException(
            "Unparseable date: \"" + jsonElement.asString
                    + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS)
        )
    }

    companion object {
        private val DATE_FORMATS = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ssz",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ssX",
            "yyyy-MM-dd'T'HH:mm:ss"
        )
    }
}


private class JSONHeaderInterceptor(private val userAgent : String = ""):
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("User-Agent", "$userAgent")
            .header("Accept", "application/json")
            .build()

        return chain.proceed(request)
    }
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


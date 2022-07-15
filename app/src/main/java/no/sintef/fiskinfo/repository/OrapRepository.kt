package no.sintef.fiskinfo.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import no.sintef.fiskinfo.BuildConfig
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.api.createService
import no.sintef.fiskinfo.api.orap.OrapService
import no.sintef.fiskinfo.model.fishingfacility.*
import no.sintef.fiskinfo.model.orap.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrapRepository(context: Context) {
    internal var orapService: OrapService? = null
    internal var orapServerUrl: String = BuildConfig.SPRICE_ORAP_SERVER_URL
    lateinit var orapUsername: String
    lateinit var orapPassword: String

    internal var icingReports = MutableLiveData<List<ToolViewModel>>()

    private var mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    init {
        updateFromPreferences(context)
    }

    fun updateFromPreferences(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        orapUsername = prefs.getString(context.getString(R.string.pref_sprice_username_key), "") ?: ""
        orapPassword = prefs.getString(context.getString(R.string.pref_sprice_password_key), "") ?: ""
    }

    fun getEarlierReports(info: GetReportsRequestBody): LiveData<SendResult> {
        var result = MutableLiveData<SendResult>()

        orapService?.getReports(info)?.enqueue(object : Callback<okhttp3.Response> {
            override fun onResponse(call: Call<okhttp3.Response>, response: Response<okhttp3.Response>) {
                Log.d("ORAP", "Fetching icing reports ok!")

                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<okhttp3.Response>, t: Throwable) {
                Log.d("ORAP", "Fetching icing reports failed!")
                t.printStackTrace()
                TODO("Not yet implemented")
            }
        }
        )

        return result
    }

    fun checkIcingReportValues(info: ReportIcingRequestBody): LiveData<SendResult> {
        var result = MutableLiveData<SendResult>()

        orapService?.checkReport(info.getRequestBodyForReportCheckAsString(), info.Username, info.Password)?.enqueue(object : Callback<okhttp3.Response> {
            override fun onResponse(call: Call<okhttp3.Response>, response: Response<okhttp3.Response>) {
                Log.d("ORAP", "Checking icing report ok!")

                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<okhttp3.Response>, t: Throwable) {
                Log.d("ORAP", "Checking icing report values failed!")
                t.printStackTrace()
                TODO("Not yet implemented")
            }
        }
        )

        return result
    }

    fun SendIcingReport(info: ReportIcingRequestBody): LiveData<SendResult> {
        var result = MutableLiveData<SendResult>()

        orapService?.submitReport(info.getRequestBodyForReportSubmissionAsString(), info.Username, info.Password)?.enqueue(object : Callback<okhttp3.Response> {
            override fun onResponse(call: Call<okhttp3.Response>, response: Response<okhttp3.Response>) {
                Log.d("ORAP", "Checking icing report ok!")

                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<okhttp3.Response>, t: Throwable) {
                Log.d("ORAP", "Checking icing report values failed!")
                t.printStackTrace()
                TODO("Not yet implemented")
            }
        }
        )

        return result
    }

    fun toIcingReportModel(report: Report): ReportViewModel {
        with(report) {
            return ReportViewModel(
            )
        }
    }

    fun initService() {
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.VALUE, "Init Sprice service")
        }

        orapService =
            createService(OrapService::class.java, orapServerUrl)
    }

    data class SendResult(val success: Boolean, val responseCode: Int, val errorMsg: String)

    companion object {
        var instance: OrapRepository? = null

        fun getInstance(context: Context): OrapRepository {
            if (instance == null)
                instance = OrapRepository(context)
            return instance!!
        }
    }
}
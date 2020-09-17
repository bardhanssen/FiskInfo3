package no.sintef.fiskinfo.ui.snap

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.repository.SnapRepository
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.annotation.TargetApi
import android.widget.Toast
import android.net.http.SslError
import android.webkit.SslErrorHandler



const val ARG_SNAP_ID = "snap_id"

class EchogramViewerFragment : Fragment() {

    companion object {
        fun newInstance() = EchogramViewerFragment()
    }


    private lateinit var webView: WebView

    private var snapId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            snapId = it.getString(ARG_SNAP_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.echogram_viewer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureWebView()
        loadContent()
    }

    fun configureWebView() {
        if (getView() == null) return
        webView = requireView().findViewById(R.id.echogramviewer_fragment_web_view)
        with (webView.settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            setGeolocationEnabled(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        }
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                // TODO: Remove this when we have valid certificates
                handler.proceed() // Ignore SSL certificate errors
            }
        }

    }

    val DEFAULT_SNAP_FISH_WEB_SERVER_ADDRESS = "https://129.242.16.123:37457/"

    fun loadContent() {
        try {
            if (snapId == null)
                return

            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val snapFishServerUrl = prefs.getString(getString(R.string.pref_snap_web_server_address), DEFAULT_SNAP_FISH_WEB_SERVER_ADDRESS)
            if (snapFishServerUrl != null) {
                //val snapFishWebServerUrl = snapFishServerUrl.replace("5002", "5006").replace("http:", "https:")
                //val url = snapFishWebServerUrl + "snap/" + snapId
                val url = snapFishServerUrl + "snap/" + snapId
                webView.loadUrl(url)
            }

        } catch (ex: Exception) {
        }

    }
}

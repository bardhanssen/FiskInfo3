package no.sintef.fiskinfo.ui.map

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView

import no.sintef.fiskinfo.R
import org.json.JSONArray
import org.json.JSONException

class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        configureWebView()
        // TODO: Use the ViewModel
    }

    fun configureWebView() {
        if (getView() == null) return
        webView = getView()!!.findViewById(R.id.map_fragment_web_view)
        with (webView.settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            setGeolocationEnabled(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        }
        //webView.webViewClient =

        webView.addJavascriptInterface(WebAppInterface(context!!),"Android" )
        webView.loadUrl("file:///android_asset/sintium_app/index.html")
    }


    class WebAppInterface(private val mContext: Context) {

        @android.webkit.JavascriptInterface
        fun getToken(): String? {
            return null //user.getToken()
        }


        @android.webkit.JavascriptInterface
        fun dismissKeyboard() {
            /*
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val act = getActivity()
            var view = act.getCurrentFocus()
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(act)
            }
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
            */
        }

        @android.webkit.JavascriptInterface
        fun setAutoCompleteData(vesselObjectsString: String) {
            try {
                //val wrappers = createVesselWrappers(vesselObjectsString) // vesselObject.names()); //vesselObjects);
                //searchAutoCompleteAdapter.clear()
                //searchAutoCompleteAdapter.addAll(wrappers)
            } catch (e: Exception) {
                e.printStackTrace()
                //TODO
            }

        }

        @android.webkit.JavascriptInterface
        fun aisFinishedLoading() {
            //if (waitingForAIS) {
            //    waitingForAIS = false
            //    refreshMapLayersIfReady()
            //}
        }

        @android.webkit.JavascriptInterface
        fun toolsFinishedLoading() {
            //if (waitingForTools) {
            //    waitingForTools = false
            //    refreshMapLayersIfReady()
            //}
        }

        @android.webkit.JavascriptInterface
        fun setToolColors(colors: String) {
/*            try {
                val colorsJSONArray = JSONArray(colors)
                colorsFromSintium.clear()
                for (i in 0 until colorsJSONArray.length()) {
                    val color = colorsJSONArray.get(i).toString()
                    val colorInt = Color.parseColor(color)
                    colorsFromSintium.add(colorInt)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                //TODO : Not my job! xoxo, torbjørn!
            }
*/
        }

        @android.webkit.JavascriptInterface
        fun setLayers(layers: String) {
/*            try {
                val layersJSONArray = JSONArray(layers)
                layersFromSintium.clear()
                for (i in 0 until layersJSONArray.length()) {
                    layersFromSintium.add(layersJSONArray.get(i).toString())
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                //TODO : Not my job! xoxo, torbjørn!
            }
*/
        }


    }


}

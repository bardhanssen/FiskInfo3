package no.sintef.fiskinfo.ui.map

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.ui.login.LoginViewModel
import org.json.JSONArray
import org.json.JSONException

class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    private lateinit var webView: WebView
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        fragmentIsActive = true

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
        webView.addJavascriptInterface(WebAppInterface(context!!, loginViewModel),"Android" )
        webView.setWebViewClient(BarentswatchFiskInfoWebClient())
        webView.loadUrl("file:///android_asset/sintium_app/index.html")
    }


    private inner class WebAppInterface(private val mContext: Context, private val loginViewModel: LoginViewModel) {

        @android.webkit.JavascriptInterface
        fun getToken(): String? {
           return loginViewModel.token?.access_token
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
            try {
                val layersJSONArray = JSONArray(layers)
                layersFromSintium.clear()
                for (i in 0 until layersJSONArray.length()) {
                    layersFromSintium.add(layersJSONArray.get(i).toString())
                }
                refreshMapLayersIfReady()
            } catch (e: JSONException) {
                e.printStackTrace()
                //TODO : Not my job! xoxo, torbjørn!
            }

        }


    }
    var layersFromSintium = mutableListOf<String>()

    private var fragmentIsActive = false
    var pageLoaded = false
    var waitingForTools = false //user.getIsFishingFacilityAuthenticated() // Wait for tools only if user is allowed to see them
    var waitingForAIS = false //user.getIsAuthenticated()  // Wait for AIS only if user is allowd to see it

    private inner class BarentswatchFiskInfoWebClient : WebViewClient() {
        override fun onLoadResource(view: WebView, url: String) {
            // Added just for debug purposes
            super.onLoadResource(view, url)
            Log.d("barentswatchFiskInfoWC", url)
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            // Added just for debug purposes
            super.onReceivedError(view, request, error)
            Log.d("barentswatchFiskInfoErr", "$request $error")
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            Log.d("URL TEST", url)
            if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                view.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                )
                return true
            } else {
                return false
            }
        }

//        @RequiresApi(api = Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
            val a = ""

            if (request != null && (request.url.toString().startsWith("http://") || request.url.toString().startsWith("https://"))) {
                view.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(request.url.toString()))
                )
                return true
            } else {
                return false
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            if (!fragmentIsActive)
                return

            //List<String> layers = user.getActiveLayers(); //.getActiveLayers();
            //if (!layers.contains(getString(R.string.primary_background_map)))
            //    layers.add(getString(R.string.primary_background_map));
            //JSONArray json = new JSONArray(layers);

            //view.loadUrl("javascript:populateMap();");
            //view.loadUrl("javascript:toggleLayers(" + json + ");");

            //if(toolsFeatureCollection != null && (getActivity() != null && (new FiskInfoUtility().isNetworkAvailable(getActivity())) && !user.getOfflineMode())) {
            //TODO: Check with Bård if this is needed now;   view.loadUrl("javascript:getToolDataFromAndroid();");
            //}

            pageLoaded = true
            refreshMapLayersIfReady()
            webView.loadUrl("javascript:getLayers()")
            webView.loadUrl("javascript:getColors()")

            //loadProgressSpinner.setVisibility(View.GONE);

            /*            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    getLayersAndVisibility();
                }
            }, 200);*/
        }
    }

    fun refreshMapLayersIfReady() {
        if (pageLoaded && !waitingForAIS && !waitingForTools) {
            activity?.runOnUiThread(Runnable {
                val json = JSONArray(layersFromSintium)
                webView.loadUrl("javascript:toggleLayers($json);")
                //loadProgressSpinner.setVisibility(View.GONE)

            })
            /*activity.runOnUiThread(Runnable {
                val layers = user.getActiveLayers()
                val json = JSONArray(layers)
                webView.loadUrl("javascript:toggleLayers($json);")
                loadProgressSpinner.setVisibility(View.GONE)
            })*/
        }
    }


    inner class MapLayerDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val selectedItems = viewModel.activeLayerNames.value!!.toMutableList() // Where we track the selected items
                val allItems = viewModel.allLayerNames.value!!.toTypedArray()
                val builder = AlertDialog.Builder(it)
                // Set the dialog title
                builder.setTitle("Show map layers")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(allItems, null) { dialog, which, isChecked ->
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                selectedItems.add(allItems[which])
                            } else if (selectedItems.contains(allItems[which])) {
                                // Else, if the item is already in the array, remove it
                                selectedItems.remove(allItems[which])
                            }
                        }
                    // Set the action buttons
                    .setPositiveButton( "OK", //R.string.ok,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User clicked OK, so save the selectedItems results somewhere
                            // or return them to the component that opened the dialog

                            viewModel.setSelectedLayers(selectedItems);
                            // TODO: update
                        })
                    .setNegativeButton( "Cancel", //R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->

                            // This can be left empty?
                        })

                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }



}

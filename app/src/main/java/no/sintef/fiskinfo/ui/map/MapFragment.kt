/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.ui.map

//import no.sintef.fiskinfo.ui.login.LoginViewModel
import android.Manifest.permission
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TableLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import net.openid.appauth.AuthorizationService
import no.sintef.fiskinfo.MainActivity
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.util.AuthStateManager
import no.sintef.fiskinfo.utilities.ui.ToolLegendRow
import no.sintef.fiskinfo.utilities.ui.UtilityDialogs
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.StringReader
import java.util.*


class MapFragment : Fragment() {
    val FRAGMENT_TAG = "MapFragment"

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var dialogInterface : UtilityDialogs
    private lateinit var viewModel: MapViewModel
    private lateinit var webView: WebView
    private lateinit var authStateManager : AuthStateManager
    private var mAccessToken : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        authStateManager = AuthStateManager.getInstance(this.requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)


        // Get the search menu.
        val searchMenu = menu.findItem(R.id.app_bar_menu_search)
        // Get SearchView object.
        val searchView: SearchView = searchMenu.getActionView() as SearchView
        // Get SearchView autocomplete object.
        val searchAutoComplete: SearchView.SearchAutoComplete =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.colorBarentsDarkBlue, //.barentswatch_blue,
                null
            )
        )
        searchAutoComplete.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.colorBarentsLightBlue,   // .text_white,
                null
            )
        )
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_light)

        //TODO add again: searchAutoComplete.setHint(getString(R.string.vessel_search_hint))

        searchAutoCompleteAdapter = ArrayAdapter<VesselWrapper?>(
            context,
            android.R.layout.simple_dropdown_item_1line,
            ArrayList<VesselWrapper?>()
        )
        searchAutoComplete.setAdapter(searchAutoCompleteAdapter)

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(OnItemClickListener { adapterView, view, itemIndex, id ->
            val selected = adapterView.getItemAtPosition(itemIndex)
            if (selected != null && selected is VesselWrapper) {
                val vesselWrapper = adapterView.getItemAtPosition(itemIndex) as VesselWrapper
                searchAutoComplete.setText(vesselWrapper.toString())
                searchAutoComplete.clearFocus()
                hideKeyboard()
                webView.loadUrl("javascript:showVesselAndBottomsheet('" + vesselWrapper.callSignal + "');")
                //browser.loadUrl("javascript:locateVessel('" + vesselWrapper.toString() + "');");
            }
        })

        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //hideKeyboard();
                //browser.loadUrl("javascript:showVesselAndBottomsheet('" + query + "');");
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.update_map -> {
                //updateMap()
                return true
            }

            R.id.zoom_to_user_position -> {
                zoomToUserPosition()
                return true
            }

            R.id.symbol_explanation -> {
                createToolSymbolExplanationDialog()
                return true
            }
            R.id.setProximityAlert -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogInterface = UtilityDialogs()

        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        fragmentIsActive = true

        configureWebView()

/*        val authService = AuthorizationService(requireContext())
        authStateManager.current.performActionWithFreshTokens(authService, { accessToken, _, ex ->
            if (ex == null) {
                mAccessToken = accessToken
                requireActivity().runOnUiThread(java.lang.Runnable {
                    webView.loadUrl("javascript:setToken(" + mAccessToken + ")")
                })
            }
        })*/


    }

    fun configureWebView() {
        if (getView() == null) return
        webView = requireView().findViewById(R.id.map_fragment_web_view)
        with(webView.settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            setGeolocationEnabled(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        }
        //webView.webViewClient =
        webView.addJavascriptInterface(WebAppInterface(requireContext()),"App")  //, loginViewModel),"Android" )

        webView.setWebViewClient(BarentswatchFiskInfoWebClient())

        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                Log.d("geolocation permission", "permission >>>$origin")
                callback.invoke(origin, true, false)
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d("WebView", consoleMessage.message())
                return true
            }

            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                Log.d(FRAGMENT_TAG, message)
                return super.onJsAlert(view, url, message, result)
            }
        })

        webView.settings.allowUniversalAccessFromFileURLs = true;
        webView.loadUrl("file:///android_asset/sintium_app/index.html")
    }

    var colorsFromSintium = ArrayList<Int>()




    private inner class WebAppInterface(private val mContext: Context) { //, private val loginViewModel: LoginViewModel) {
        // TODO: Check which parts of this should be implemented again
/*
        @android.webkit.JavascriptInterface
        fun getToken(): String? {
            val manTok = authStateManager.current.accessToken
            if (mAccessToken != null) {
                if (manTok.equals(mAccessToken))
                    return manTok
                else
                    return mAccessToken
            }
            else
                return manTok
            //return mAccessToken
            //return authStateManager.current.accessToken

            //return loginViewModel.token?.access_token
        }
*/

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
                val wrappers = createVesselWrappers(vesselObjectsString) // vesselObject.names()); //vesselObjects);
                searchAutoCompleteAdapter?.clear()
                searchAutoCompleteAdapter?.addAll(wrappers)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        @android.webkit.JavascriptInterface
        fun ready() {

            //TODO: Implement
            val authService = AuthorizationService(requireContext())
            authStateManager.current.performActionWithFreshTokens(authService, { accessToken, _, ex ->
                if (ex == null) {
                    mAccessToken = accessToken
                    requireActivity().runOnUiThread(java.lang.Runnable {
                        webView.loadUrl("javascript:setToken('" + mAccessToken + "')")
                    })

//                    webView.loadUrl("javascript:setToken(" + mAccessToken + ")")
                }
            })

            //if (waitingForAIS) {
            //    waitingForAIS = false
            //    refreshMapLayersIfReady()
            //}
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
            try {
                val colorsJSONArray = JSONArray(colors)
                colorsFromSintium.clear()
                for (i in 0 until colorsJSONArray.length()) {
                    val color = colorsJSONArray.get(i).toString()
                    val colorInt = Color.parseColor(color)
                    colorsFromSintium.add(colorInt)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
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

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
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

            if (request != null && (request.url.toString().startsWith("http://") || request.url.toString().startsWith(
                    "https://"
                ))) {
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
            //webView.loadUrl("javascript:getColors()")

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

    private fun zoomToUserPosition() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                arrayOf(permission.ACCESS_FINE_LOCATION),
                MainActivity.MY_PERMISSIONS_REQUEST_FINE_LOCATION
            )
        } else {
            webView.loadUrl("javascript:zoomToUserPosition()")
        }
    }

    private fun createToolSymbolExplanationDialog() {
        val dialog: Dialog = dialogInterface.getDialog(
            activity,
            R.layout.tool_legend_dialog,
            R.string.tool_legend
        )!!
        val tableLayout =
            dialog.findViewById<View>(R.id.tool_legend_table_layout) as TableLayout
        val dismissButton =
            dialog.findViewById<View>(R.id.tool_legend_dismiss_button) as Button
        val toolTypes = arrayOf(
            getString(R.string.tool_type_code_crabpot),
            getString(R.string.tool_type_code_danpurseine),
            getString(R.string.tool_type_code_longline),
            getString(R.string.tool_type_code_mooring),
            getString(R.string.tool_type_code_nets),
            getString(R.string.tool_type_code_sensor_cable),
            getString(R.string.tool_type_code_unknown)
        )
        if (colorsFromSintium.size > 0) {
            var i = 0
            for (toolType in toolTypes) {
                val color = colorsFromSintium[i]
                val toolLegendRow: View =
                    ToolLegendRow(activity, color, toolType).view
                tableLayout.addView(toolLegendRow)
                i += 1
            }
        }
        dismissButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }


    /****
     * Search functionality
     */
    protected var searchAutoCompleteAdapter: ArrayAdapter<VesselWrapper?>? = null


    class VesselWrapper( var name: String, var callSignal: String) {

        override fun toString(): String {
            return callSignal + " - " + name
        }

        init {
            callSignal = callSignal
        }
    }


    fun createVesselWrappers(jsonStr: String?): ArrayList<VesselWrapper>? {
        val vesselWrappers = ArrayList<VesselWrapper>()
        val reader = JsonReader(StringReader(jsonStr))
        try {
            reader.beginArray()
            while (reader.hasNext()) {
                vesselWrappers.add(readVessel(reader))
            }
            reader.endArray()
        } catch (ex: IOException) {
        } finally {
            try {
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return vesselWrappers
    }

    @Throws(IOException::class)
    fun readVessel(reader: JsonReader): VesselWrapper {
        var callSign = ""
        var name = ""
        reader.beginObject()
        while (reader.hasNext()) {
            val propName = reader.nextName()
            if (propName.equals("Name", true) && reader.peek() != JsonToken.NULL) {
                name = reader.nextString()
            } else if (propName.equals("Callsign", true) && reader.peek() != JsonToken.NULL) {
                callSign = reader.nextString()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
        return VesselWrapper(name, callSign)
    }

    fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val act: Activity? = activity
        var view = act!!.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(act)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

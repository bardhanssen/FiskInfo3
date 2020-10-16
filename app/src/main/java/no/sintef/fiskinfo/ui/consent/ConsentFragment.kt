package no.sintef.fiskinfo.ui.consent

import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.switchmaterial.SwitchMaterial
import no.sintef.fiskinfo.R

class ConsentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.consent_fragment, container, false)
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val consent = prefs.getBoolean("user_consent_to_terms", false)

        var consentSwitch = v.findViewById<SwitchMaterial>(R.id.consent_switch)
        consentSwitch.isChecked = consent
        consentSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            prefs.edit().putBoolean("user_consent_to_terms", isChecked).apply()
            Navigation.findNavController(v).popBackStack()
            // TODO add check on removing consent
            // Responds to switch being checked/unchecked
        }
        var textView = v.findViewById<TextView>(R.id.consent_text);
//        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml( getString(R.string.user_consent_description), Html.FROM_HTML_MODE_LEGACY))
        } else {
            textView.setText(Html.fromHtml(getString(R.string.user_consent_description)))
        }
        return v
    }
}
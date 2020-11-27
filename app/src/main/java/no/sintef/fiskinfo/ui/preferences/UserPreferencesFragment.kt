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
package no.sintef.fiskinfo.ui.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import androidx.navigation.Navigation
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.repository.SnapRepository

//import no.sintef.fiskinfo.repository.SnapRepository

class UserPreferencesFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey)

        configureEditTextInputType(
            "contact_person_email",
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        )
        configureEditTextInputType("contact_person_name", InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        configureEditTextInputType("contact_person_phone", InputType.TYPE_CLASS_PHONE)
        configureEditTextInputType("snap_api_server_address", InputType.TYPE_TEXT_VARIATION_URI)
        configureEditTextInputType("snap_web_server_address", InputType.TYPE_TEXT_VARIATION_URI)
        configureEditTextInputType("user_identity", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        //configureEditTextInputType("pref_user_id", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        //configureEditTextInputType("pref_tool_days_before_old", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)

        val consentPreference : Preference? = findPreference("consent")
        consentPreference?.setOnPreferenceClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.consentFragment)
            true
        }

    }

    private fun configureEditTextInputType(key: String, inputType: Int) {
        val editTextPreference = preferenceManager.findPreference<EditTextPreference>(key)
        editTextPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = inputType
        }
    }


    override fun onResume() {
        super.onResume()
        getPreferenceScreen().getSharedPreferences()
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        getPreferenceScreen().getSharedPreferences()
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if ((key == "server_address") && (context != null))
            SnapRepository.getInstance(requireContext()).updateFromPreferences(context)
    }
}

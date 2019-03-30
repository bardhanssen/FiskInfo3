package no.sintef.fiskinfo.ui.preferences;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import no.sintef.fiskinfo.R;

public class UserPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);
    }

}

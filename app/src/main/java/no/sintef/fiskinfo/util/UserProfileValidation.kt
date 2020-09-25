package no.sintef.fiskinfo.util

import android.content.Context
import android.preference.PreferenceManager
import no.sintef.fiskinfo.R


    // TODO: Add better validations than only on empty string

    private fun isEmailValid(email : String) :Boolean {
        return email.trim() != ""
    }

    private fun isPhoneValid(phone : String) :Boolean {
        return phone.trim() != ""
    }

    private fun isNameValid(name : String) :Boolean {
        return name.trim() != ""
    }

    fun isUserProfileValid(context: Context):Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var email = prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        var phone = prefs.getString(context.getString(R.string.pref_contact_person_phone), "")
        var name = prefs.getString(context.getString(R.string.pref_contact_person_name), "")
        return isEmailValid(email) && isNameValid(name) && isPhoneValid(phone)
    }



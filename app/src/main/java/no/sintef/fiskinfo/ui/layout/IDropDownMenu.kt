package no.sintef.fiskinfo.ui.layout

import android.content.Context

interface IDropDownMenu {
    fun getLocalizedName(context : Context):String
    fun getFormValue(): String
    fun getFormIndex(): String
}
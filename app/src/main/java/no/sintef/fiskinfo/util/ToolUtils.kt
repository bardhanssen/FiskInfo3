package no.sintef.fiskinfo.util

import android.content.Context
import android.preference.PreferenceManager
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.ToolViewModel
import java.util.*
import java.util.concurrent.TimeUnit


fun isToolOld(tool : ToolViewModel, context : Context):Boolean {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val daysBeforeOld = prefs.getString(context.getString(R.string.pref_tool_days_before_old), ToolViewModel.DEFAULT_DAYS_BEFORE_OLD.toString())!!.toInt()
    return isToolOld(tool, daysBeforeOld)
}

fun isToolOld(tool : ToolViewModel, daysBeforeOld : Int):Boolean {
    if (tool.setupDateTime == null)
        return false

    val diffMillis = Date().time - tool.setupDateTime!!.time
    val diffInDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS)
    return (diffInDays > daysBeforeOld)
}

//@file:JvmName("DoubleConverter")
package no.sintef.fiskinfo.util

import android.R
import android.content.res.Resources
import android.view.View
import android.widget.EditText

import android.widget.TextView

import androidx.databinding.InverseMethod
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

object DoubleConverter {
@InverseMethod("stringToDouble")
@JvmStatic fun doubleToString(
    oldValue: Double,
    value: Double
): String {
    return value.toString()
/*
    val numberFormat: NumberFormat = getNumberFormat(view)
    try {
        // Don't return a different value if the parsed value
        // doesn't change
        val inView = view.text.toString()
        val parsed: Double = numberFormat.parse(inView).toDouble()
        if (parsed == value) {
            return view.text.toString()
        }
    } catch (e: ParseException) {
        // Old number was broken
    }
    return numberFormat.format(value)*/
}

@JvmStatic fun  stringToDouble(
    oldValue: Double,
    value: String
): Double {
    val numberFormat: NumberFormat = NumberFormat.getNumberInstance()
    return try {
        numberFormat.parse(value).toDouble()
    } catch (e: ParseException) {
        //val resources: Resources = view.resources
        //val errStr: String = "not a valid number" //resources.getString(R.string.badNumber)
        //view.error = errStr
        oldValue
    }
}


fun getNumberFormat(view: View): NumberFormat {
    val resources: Resources = view.getResources()
    val locale: Locale = resources.getConfiguration().locale
    val format: NumberFormat = NumberFormat.getNumberInstance(locale)
    if (format is DecimalFormat) {
        val decimalFormat: DecimalFormat = format as DecimalFormat
        decimalFormat.setGroupingUsed(false)
    }
    return format
}

/*
    object DoubleConverter {
        @InverseMethod("stringToDouble")
        @JvmStatic fun doubleToString(
            view: EditText, oldValue: Double,
            value: Double
        ): String {
            val numberFormat: NumberFormat = getNumberFormat(view)
            try {
                // Don't return a different value if the parsed value
                // doesn't change
                val inView = view.text.toString()
                val parsed: Double = numberFormat.parse(inView).toDouble()
                if (parsed == value) {
                    return view.text.toString()
                }
            } catch (e: ParseException) {
                // Old number was broken
            }
            return numberFormat.format(value)
        }

        @InverseMethod("doubleToString")
        @JvmStatic fun  stringToDouble(
            view: EditText, oldValue: Double,
            value: String
        ): Double {
            val numberFormat: NumberFormat = getNumberFormat(view)
            return try {
                numberFormat.parse(value).toDouble()
            } catch (e: ParseException) {
                val resources: Resources = view.resources
                val errStr: String = "not a valid number" //resources.getString(R.string.badNumber)
                view.error = errStr
                oldValue.toDouble()
            }
        }


        fun getNumberFormat(view: View): NumberFormat {
            val resources: Resources = view.getResources()
            val locale: Locale = resources.getConfiguration().locale
            val format: NumberFormat = NumberFormat.getNumberInstance(locale)
            if (format is DecimalFormat) {
                val decimalFormat: DecimalFormat = format as DecimalFormat
                decimalFormat.setGroupingUsed(false)
            }
            return format
        }




 */








}


/*
object DoubleConverter {
    @InverseMethod("stringToDouble")
    @JvmStatic fun doubleToString(
        view: TextView, oldValue: Double,
        value: Double
    ): String {
        val numberFormat: NumberFormat = getNumberFormat(view)
        try {
            // Don't return a different value if the parsed value
            // doesn't change
            val inView = view.text.toString()
            val parsed: Double = numberFormat.parse(inView).toDouble()
            if (parsed == value) {
                return view.text.toString()
            }
        } catch (e: ParseException) {
            // Old number was broken
        }
        return numberFormat.format(value)
    }

    @JvmStatic fun stringToDouble(
        view: TextView, oldValue: Double,
        value: String?
    ): Double {
        val numberFormat: NumberFormat = getNumberFormat(view)
        return try {
            numberFormat.parse(value).toDouble()
        } catch (e: ParseException) {
            val resources: Resources = view.resources
            val errStr: String = "not a valid number" //resources.getString(R.string.badNumber)
            view.error = errStr
            oldValue
        }
    }


    private fun getNumberFormat(view: View): NumberFormat {
        val resources: Resources = view.getResources()
        val locale: Locale = resources.getConfiguration().locale
        val format: NumberFormat = NumberFormat.getNumberInstance(locale)
        if (format is DecimalFormat) {
            val decimalFormat: DecimalFormat = format as DecimalFormat
            decimalFormat.setGroupingUsed(false)
        }
        return format
    }
}

*/
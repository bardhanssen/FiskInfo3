package no.sintef.fiskinfo.utilities.ui

import android.widget.TextView

class IntRangeValidator(
    private val textView: TextView,
    private val low: Int,
    private val includeLow: Boolean,
    private val high: Int,
    private val includeHigh: Boolean,
    private val updateAction: (Double) -> (Unit)
) : TextValidator(textView) {
    override fun validateAndUpdate(textView: TextView?, text: String?):Boolean {
        var num = 0.0;

        try {
            // TODO: Consider locale adapted parsing
            num = text.toString().toDouble()
            val withinLow = if (includeLow) num >= low else num > low
            val withinHigh = if (includeHigh) num <= high else num < high

            if (! (withinHigh && withinLow))
                textView?.error = "Value must be in range $low to $high"
            else {
                textView?.error = null
                if (updateAction != null)
                    updateAction(num)
            }
        } catch (nfe: NumberFormatException) {
            textView?.error = "Value must be in range $low to $high"
            //println("Could not parse $nfe")
        }
        return (textView?.error == null)
    }
}

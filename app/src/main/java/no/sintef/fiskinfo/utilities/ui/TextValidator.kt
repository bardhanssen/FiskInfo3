package no.sintef.fiskinfo.utilities.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

abstract class TextValidator(private val textView: TextView) : TextWatcher {
    abstract fun validateAndUpdate(textView: TextView?, text: String?):Boolean

    override fun afterTextChanged(s: Editable) {
        val text = textView.text.toString()
        validateAndUpdate(textView, text)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
    {
        // Ignore
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
    {
        // Ignore
    }
}
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
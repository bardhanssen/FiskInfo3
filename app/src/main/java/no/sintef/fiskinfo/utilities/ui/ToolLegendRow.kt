/**
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

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import no.sintef.fiskinfo.R

class ToolLegendRow(
    context: Context?,
    color: Int,
    legendName: String?
) : BaseTableRow(context, R.layout.tool_legend_row) {
    private val mImageView: ImageView
    private val mTextView: TextView
    var color: Int
        get() {
            val drawable = mImageView.background as ColorDrawable
            try {
                return drawable.color
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
            return -1
        }
        set(color) {
            mImageView.setBackgroundColor(color)
        }

    var text: String?
        get() = mTextView.text.toString()
        set(text) {
            mTextView.text = text
        }

    override fun setEnabled(enabled: Boolean) {}

    init {
        mImageView =
            view.findViewById<View>(R.id.tool_legend_row_image_view) as ImageView
        mTextView = view.findViewById<View>(R.id.tool_legend_row_text_view) as TextView
        mImageView.setBackgroundColor(color)
        mTextView.text = legendName
    }
}
/**
 * Copyright (C) 2019 SINTEF
 *
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
package no.sintef.fiskinfo.ui.tools

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode

class ToolTypeCodeArrayAdapter(context: Context, resource: Int, objects: Array<ToolTypeCode>) :
ArrayAdapter<ToolTypeCode>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val theView : TextView = super.getView(position, convertView, parent) as TextView
        theView.text = getItem(position)?.getLocalizedName(context)
        return theView
    }

    private val allToolTypeCodesFilter = object: Filter() {
        override fun convertResultToString(resultValue: Any?): CharSequence {
            resultValue?.let {
                var code = it as ToolTypeCode
                return code.getLocalizedName(context!!)
            }
            return ""
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = objects
            results.count = objects.size
            return results
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return allToolTypeCodesFilter
    }
}
package no.sintef.fiskinfo.ui.layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import no.sintef.fiskinfo.R

class TextInputLayoutGridViewAdapter(context: Context, textInputLayoutArrayList: ArrayList<TextInputLayoutGridViewModel>) :
    ArrayAdapter<TextInputLayoutGridViewModel>(context, 0, textInputLayoutArrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listItemView = convertView
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(context).inflate(R.layout.text_input_layout_grid_view_model, parent, false)
        }

        val model: TextInputLayoutGridViewModel? = getItem(position)
        val textInputLayout = listItemView!!.findViewById<TextInputLayout>(R.id.layout_grid_view_model_text_input_layout)
        val textInputEditText = listItemView.findViewById<TextInputEditText>(R.id.layout_grid_view_model_edit_text)

        textInputLayout.hint = model!!.hint
        textInputLayout.textAlignment = model.textAlignment
        textInputLayout.suffixText = model.suffixText

        textInputEditText.textAlignment = model.textAlignment
        textInputEditText.setSelectAllOnFocus(model.selectAllOnFocus)
        textInputEditText.maxLines = model.maxLines
        textInputEditText.inputType = model.inputType


        return listItemView
    }
}
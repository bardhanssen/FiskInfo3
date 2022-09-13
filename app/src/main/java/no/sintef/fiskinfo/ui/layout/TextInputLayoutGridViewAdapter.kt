package no.sintef.fiskinfo.ui.layout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.ui.sprice.IDropDownMenu

class TextInputLayoutGridViewAdapter<T : IDropDownMenu>(context: Context, textInputLayoutArrayList: ArrayList<TextInputLayoutGridViewModel<T>>) :
    ArrayAdapter<TextInputLayoutGridViewModel<T>>(context, 0, textInputLayoutArrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model: TextInputLayoutGridViewModel<T>? = getItem(position)

        var listItemView = convertView
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = if(model?.onclickListener == null) LayoutInflater.from(context).inflate(R.layout.grid_view_edit_text_item, parent, false) else
                LayoutInflater.from(context).inflate(R.layout.grid_view_drop_down_menu_item, parent, false)
        }

        val textInputLayout = listItemView!!.findViewById<TextInputLayout>(R.id.grid_view_item_text_input_layout)
        val input = if(model?.onclickListener == null) listItemView.findViewById<View>(R.id.layout_grid_view_model_edit_text)
            else listItemView.findViewById<AutoCompleteTextView>(R.id.layout_grid_view_drop_down_item)

        textInputLayout.hint = model!!.hint
        textInputLayout.textAlignment = model.textAlignment
        textInputLayout.suffixText = model.suffixText

        input.textAlignment = model.textAlignment
        (input as EditText).setSelectAllOnFocus(model.selectAllOnFocus)
        input.maxLines = model.maxLines
        input.inputType = model.inputType

        if(model.onclickListener != null) {
            (input as MaterialAutoCompleteTextView).onItemClickListener = model.onclickListener
            input.setAdapter(model.dropDownAdapter)
        }

        return listItemView
    }
}
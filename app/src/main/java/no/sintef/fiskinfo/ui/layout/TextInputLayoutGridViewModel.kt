package no.sintef.fiskinfo.ui.layout

import android.text.InputType
import android.view.View.TEXT_ALIGNMENT_VIEW_END
import android.widget.AdapterView
import no.sintef.fiskinfo.ui.sprice.DropDownMenuArrayAdapter
import no.sintef.fiskinfo.ui.sprice.IDropDownMenu

class TextInputLayoutGridViewModel<T : IDropDownMenu>() {
    lateinit var fieldName: String
    lateinit var hint: String
    var textAlignment: Int = TEXT_ALIGNMENT_VIEW_END
    var suffixText: String? = null
    var selectAllOnFocus: Boolean = true
    var maxLines: Int = 1
    var inputType: Int = InputType.TYPE_CLASS_NUMBER
    var onclickListener: AdapterView.OnItemClickListener? = null
    var dropDownAdapter: DropDownMenuArrayAdapter<T>? = null

    constructor(fieldName: String, hint: String) : this() {
        this.fieldName = fieldName
        this.hint = hint
    }

    constructor(
        fieldName: String,
        hint: String,
        textAlignment: Int = TEXT_ALIGNMENT_VIEW_END,
        suffixText: String? = null,
        selectAllOnFocus: Boolean = true,
        maxLines: Int = 1,
        inputType: Int = InputType.TYPE_CLASS_NUMBER,
        onClickListener: AdapterView.OnItemClickListener? = null,
        dropDownAdapter: DropDownMenuArrayAdapter<T>? = null
    )
            : this(fieldName, hint) {
        this.fieldName = fieldName
        this.hint = hint
        this.textAlignment = textAlignment
        this.suffixText = suffixText
        this.selectAllOnFocus = selectAllOnFocus
        this.maxLines = maxLines
        this.inputType = inputType
        this.onclickListener = onClickListener
        this.dropDownAdapter = dropDownAdapter
    }
}
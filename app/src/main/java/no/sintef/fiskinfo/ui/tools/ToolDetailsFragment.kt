package no.sintef.fiskinfo.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.ToolDetailsFragmentBinding


class ToolDetailsFragment : Fragment() {
    // PRI: Get basic working editor
    // MAYBE: use material design to improve l&f

    companion object {
        fun newInstance() = ToolDetailsFragment()
    }

    private lateinit var mViewModel: ToolsViewModel
    private var mBinding: ToolDetailsFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.tool_details_fragment, container, false)

        val TOOLS =
            arrayOf("NETS", "LONGLINE", "CRABPOT", "DANPURSEINE")

        val adapter = ArrayAdapter(
            context,
            R.layout.exposed_dropdown_menu_item,
            //R.array.tool_type_codes
            TOOLS
        )

        val editTextFilledExposedDropdown: AutoCompleteTextView =
            mBinding!!.root.findViewById(R.id.toolDetailsTypeField)
        editTextFilledExposedDropdown.setAdapter(adapter)



        return mBinding!!.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!).get(ToolsViewModel::class.java)

        mViewModel.getSelectedTool().observe(this, Observer { tool ->
            if (tool != null) {
                mBinding!!.setTool(tool)
                //mBinding!!.setEchogram(mViewModel?.draftMetadata)
                //mBinding!!.setHandlers(this@SnapEditorFragment)
                //mBinding!!.setSnapviewmodel(mViewModel)
            }
        })


        // TODO: Use the ViewModel
    }

}

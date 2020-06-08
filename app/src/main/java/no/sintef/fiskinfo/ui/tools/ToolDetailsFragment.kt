package no.sintef.fiskinfo.ui.tools

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.ToolDetailsFragmentBinding
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode


class ToolDetailsFragment : Fragment() {
    // PRI: Get basic working editor
    // MAYBE: use material design to improve l&f

    companion object {
        fun newInstance() = ToolDetailsFragment()
    }

    private lateinit var mViewModel: ToolsViewModel
    private lateinit var mBinding: ToolDetailsFragmentBinding //? = null

    class MyArrayAdapter(context: Context?, resource: Int, objects: Array<ToolTypeCode>) :
        ArrayAdapter<ToolTypeCode>(context, resource, objects) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val theView : TextView = super.getView(position, convertView, parent) as TextView
            theView.text = getItem(position).getLocalizedName(context)
            return theView
        }

        private val filter_that_does_nothing = object: Filter() {
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
            return filter_that_does_nothing
        }
/*        override fun getFilter(): Filter {
            return filter_that_does_nothing
        }*/

    }

    private lateinit var mToolCodeAdapter : MyArrayAdapter
    private lateinit var mEditTextFilledExposedDropdown: AutoCompleteTextView
    //private lateinit val mBinding: ToolDetailsFragmentBinding = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.tool_details_fragment, container, false)

        //ToolTypeCode.values()

        //val TOOLS =

        /*    arrayOf("NETS", "LONGLINE", "CRABPOT", "DANPURSEINE")

        val adapter = ArrayAdapter(
            context,
            R.layout.exposed_dropdown_menu_item,
            //R.array.tool_type_codes
            TOOLS
        )
        */
        mToolCodeAdapter = MyArrayAdapter(context, R.layout.exposed_dropdown_menu_item, ToolTypeCode.values())
        mEditTextFilledExposedDropdown = mBinding.toolDetailsTypeField

        mEditTextFilledExposedDropdown.setOnItemClickListener { parent, view, position, id -> mViewModel.setSelectedToolCode(parent.getItemAtPosition(position) as ToolTypeCode) }

        /*
        mEditTextFilledExposedDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val newCode = parent?.getItemAtPosition(position) as ToolTypeCode
                mViewModel.setSelectedToolCode(newCode)
            }
        }
*/
        mEditTextFilledExposedDropdown =
            mBinding!!.root.findViewById(R.id.toolDetailsTypeField)
        mEditTextFilledExposedDropdown.setAdapter(mToolCodeAdapter)

        return mBinding!!.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!).get(ToolsViewModel::class.java)

        mViewModel.getSelectedTool().observe(this, Observer { tool ->
            if (tool != null) {
                mBinding.tool = tool
                mBinding.toolviewmodel = mViewModel
                //if (context != null)
                //    mEditTextFilledExposedDropdown.setText(tool.toolTypeCode?.getLocalizedName(context!!), false)
                //mBinding!!.setEchogram(mViewModel?.draftMetadata)
                //mBinding!!.setHandlers(this@SnapEditorFragment)
                //mBinding!!.setSnapviewmodel(mViewModel)
            }
        })

        mViewModel.selectedToolCodeName.observe(this, Observer { toolCodeName ->
            if (toolCodeName != null) {
                mBinding.toolcodename = toolCodeName
                //if (context != null)
                //    mEditTextFilledExposedDropdown.setText(tool.toolTypeCode?.getLocalizedName(context!!), false)
                //mBinding!!.setEchogram(mViewModel?.draftMetadata)
                //mBinding!!.setHandlers(this@SnapEditorFragment)
                //mBinding!!.setSnapviewmodel(mViewModel)
            }
        })

        // TODO: Use the ViewModel
    }

}

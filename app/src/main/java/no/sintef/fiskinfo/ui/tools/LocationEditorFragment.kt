package no.sintef.fiskinfo.ui.tools

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.sintef.fiskinfo.R

class LocationEditorFragment : Fragment() {

    companion object {
        fun newInstance() = LocationEditorFragment()
    }

    private lateinit var viewModel: LocationEditorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_editor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationEditorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
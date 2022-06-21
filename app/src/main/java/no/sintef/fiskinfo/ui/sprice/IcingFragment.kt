package no.sintef.fiskinfo.ui.sprice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.sintef.fiskinfo.R

class IcingFragment : Fragment() {

    companion object {
        fun newInstance() = IcingFragment()
    }

    private lateinit var viewModel: IcingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_icing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IcingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
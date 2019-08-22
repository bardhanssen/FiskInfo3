package no.sintef.fiskinfo.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import no.sintef.fiskinfo.model.SnapMessage

class MapViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var allLayerNames = MutableLiveData<ArrayList<String>>()
    var activeLayerNames = MutableLiveData<ArrayList<String>>()



    fun setSelectedLayers(selectedLayers : MutableList<String> ) {
        activeLayerNames.value?.clear()
        activeLayerNames.value?.addAll(selectedLayers)
    }

}

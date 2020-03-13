package no.sintef.fiskinfo.ui.overview

import android.view.View

class OverviewCardItem(val title: String, val subTitle: String = "", val imageResource : Int,
                       val description : String, val action1Text : String, val action2Text : String = "",
                       var action1Listener : View.OnClickListener? = null, var action2Listener : View.OnClickListener? = null) {

}
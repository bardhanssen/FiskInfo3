package no.sintef.fiskinfo.ui.tools

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale
import java.util.TimeZone

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

// TODO: Add attribution
// Icons made by Freepik from www.flaticon.com

/**
 * [RecyclerView.Adapter] that can display a [FishingFacilityChange] and makes call to the
 * specified {@OnToolInteractionListener} callbacks on interaction
 * TODO: Replace the implementation with code for your data type.
 */
class ToolsRecyclerViewAdapter(private val mListener: OnToolInteractionListener?, private val mConfirmed : Boolean) :
    RecyclerView.Adapter<ToolsRecyclerViewAdapter.ViewHolder>() {

    private var tools: List<FishingFacility>? = null

    init {
        tools = ArrayList()
    }

    fun setTools(tools: List<FishingFacility>) {
        this.tools = tools
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tool_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (tools == null)
            return
        val tool = tools!![position]

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        holder.titleView.text = sdf.format(tool.lastChangedDateTime) // TODO: Check if this should be setupDateTime and check for null

        val textStyle =  Typeface.BOLD//if (holder.mItem!!.seen) Typeface.NORMAL else Typeface.BOLD
        holder.titleView.setTypeface(null, textStyle)
        //holder.detail1View.setTypeface(null, textStyle)

        when (tool.toolTypeCode) {
            ToolTypeCode.CRABPOT -> holder.iconView.setBackgroundResource(R.drawable.ic_crabpot)
            ToolTypeCode.DANPURSEINE -> holder.iconView.setBackgroundResource(R.drawable.ic_purseseine)
            ToolTypeCode.LONGLINE -> holder.iconView.setBackgroundResource(R.drawable.ic_hook)
            ToolTypeCode.NETS -> holder.iconView.setBackgroundResource(R.drawable.ic_net)
        }

        holder.detail1View.text = tool.toolTypeCode?.code
        holder.detail2View.text = tool.geometryWKT
//            sdf.format(holder.mItem!!.snapMetadata!!.timestamp) //holder.mItem.getEchogramInfo().latitude);
        //holder.shareButton.setOnClickListener({mListener?.onViewSnapInMapClicked(it, holder.mItem)})

        holder.mView.setOnClickListener { v ->
            //holder.mItem?.seen = true
            mListener?.onViewToolClicked(v, tools!![holder.adapterPosition] )
        }
    }

    override fun getItemCount(): Int {
        return tools!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val iconView: ImageView
        val titleView: TextView
        val detail1View: TextView
        val detail2View: TextView
        val statusButton: ImageButton

        init {
            iconView = mView.findViewById<View>(R.id.tool_item_image_view) as ImageView
            titleView = mView.findViewById<View>(R.id.tool_item_title_view) as TextView
            detail1View = mView.findViewById<View>(R.id.tool_item_detail_1_view) as TextView
            detail2View = mView.findViewById<View>(R.id.tool_item_detail_2_view) as TextView
            statusButton = mView.findViewById<View>(R.id.tool_status_button) as ImageButton
        }

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }

    interface OnToolInteractionListener {
        fun onViewToolClicked(v: View, tool: FishingFacility?)
        //fun onViewSnapInMapClicked(v: View, snap: SnapMessage?)
    }
}

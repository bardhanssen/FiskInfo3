/**
 * Copyright (C) 2019 SINTEF
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
package no.sintef.fiskinfo.ui.snap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.EchogramInfo

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale
import java.util.TimeZone

/**
 * [RecyclerView.Adapter] that can display a [EchogramInfo] and makes calls to the
 * specified [OnEchogramInteractionListener].
 */
class EchogramRecyclerViewAdapter(listener: EchogramRecyclerViewAdapter.OnEchogramInteractionListener) :
    RecyclerView.Adapter<EchogramRecyclerViewAdapter.ViewHolder>() {

    private var echograms: List<EchogramInfo>? = null
    private val mListener: OnEchogramInteractionListener?

    init {
        echograms = ArrayList()
        mListener = listener
    }

    fun setEchograms(echograms: List<EchogramInfo>) {
        this.echograms = echograms
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.echogram_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = echograms!![position]

        holder.titleView.text = holder.mItem!!.source

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        //        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.datetime_format_yyyy_mm_dd_t_hh_mm_ss), Locale.getDefault());
        //        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.timeZone = TimeZone.getDefault()
        holder.detail1View.text = sdf.format(holder.mItem!!.timestamp)
        holder.detail2View.text = holder.mItem!!.latitude

        holder.viewButton.setOnClickListener {mListener?.onViewEchogramClicked(it, holder.mItem)}
        holder.shareButton.setOnClickListener {mListener?.onShareEchogramClicked(it, holder.mItem)}
        /*            ( object : Button.OnClickListener {
                override fun onClick(v: View) {
                    mListener?.onViewEchogramClicked(v, holder.mItem)
                }
            })
            holder.shareButton.setOnClickListener(object : Button.OnClickListener {
                override fun onClick(v: View) {
                    mListener?.onShareEchogramClicked(v, holder.mItem)
                }
            })
        }*/
    }

    override fun getItemCount(): Int {
        return echograms!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: EchogramInfo? = null
        val titleView: TextView
        val detail1View: TextView
        val detail2View: TextView
        val viewButton: ImageButton
        val shareButton: ImageButton

        init {
            titleView = mView.findViewById(R.id.snap_item_title_view) as TextView
            detail1View = mView.findViewById(R.id.snap_item_detail_1_view) as TextView
            detail2View = mView.findViewById(R.id.snap_item_detail_2_view) as TextView
            shareButton = mView.findViewById(R.id.share_echogram_button) as ImageButton
            viewButton = mView.findViewById(R.id.open_echogram_button) as ImageButton
        }

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }

    interface OnEchogramInteractionListener {
        fun onViewEchogramClicked(v: View, echogram: EchogramInfo?)
        fun onShareEchogramClicked(v: View, echogram: EchogramInfo?)
    }
}

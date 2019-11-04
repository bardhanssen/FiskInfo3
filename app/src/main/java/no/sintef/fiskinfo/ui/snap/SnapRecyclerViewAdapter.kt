package no.sintef.fiskinfo.ui.snap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.SnapMessage

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

/**
 * [RecyclerView.Adapter] that can display a [SnapMessage] and makes call to the
 * specified {@OnSnapInteractionListener} callbacks on interaction
 * TODO: Replace the implementation with code for your data type.
 */
class SnapRecyclerViewAdapter(private val mListener: OnSnapInteractionListener?, private val mIncomming : Boolean) :
    RecyclerView.Adapter<SnapRecyclerViewAdapter.ViewHolder>() {

    private var snaps: List<SnapMessage>? = null

    init {
        snaps = ArrayList()
    }

    fun setSnaps(snaps: List<SnapMessage>) {
        this.snaps = snaps
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.snap_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = snaps!![position]

        if (mIncomming)
            holder.titleView.text = holder.mItem!!.sender!!.email
        else
            holder.titleView.text = holder.mItem?.receiverEmails ?: ""

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        //        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.datetime_format_yyyy_mm_dd_t_hh_mm_ss), Locale.getDefault());
        //        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.timeZone = TimeZone.getDefault()
        //holder.detail1View.setText(sdf.format(holder.mItem.getEchogramInfo().timestamp));
        holder.detail1View.text = holder.mItem!!.message
        holder.detail2View.text =
            sdf.format(holder.mItem!!.snapMetadata!!.timestamp) //holder.mItem.getEchogramInfo().latitude);
        holder.shareButton.setOnClickListener({mListener?.onViewSnapInMapClicked(it, holder.mItem)})

        holder.mView.setOnClickListener { v ->
            mListener?.onViewSnapClicked(v, holder.mItem)
        }
    }

    override fun getItemCount(): Int {
        return snaps!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: SnapMessage? = null
        val titleView: TextView
        val detail1View: TextView
        val detail2View: TextView
        val shareButton: ImageButton

        init {
            titleView = mView.findViewById<View>(R.id.snap_item_title_view) as TextView
            detail1View = mView.findViewById<View>(R.id.snap_item_detail_1_view) as TextView
            detail2View = mView.findViewById<View>(R.id.snap_item_detail_2_view) as TextView
            shareButton = mView.findViewById<View>(R.id.show_snap_in_map_button) as ImageButton
        }

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }

    interface OnSnapInteractionListener {
        fun onViewSnapClicked(v: View, snap: SnapMessage?)
        fun onViewSnapInMapClicked(v: View, snap: SnapMessage?)
    }
}

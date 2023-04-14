/**
 * Copyright (C) 2020 SINTEF
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
package no.sintef.fiskinfo.ui.sprice

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload
import no.sintef.fiskinfo.util.formatLocation
import java.text.SimpleDateFormat
import java.util.*





/**
 * [RecyclerView.Adapter] that can display a icing report and makes call to the
 * specified {@OnReportInteractionListener} callbacks on interaction
 */
class SpriceReportRecyclerViewAdapter(private val mListener: OnReportInteractionListener?) :
    RecyclerView.Adapter<SpriceReportRecyclerViewAdapter.ViewHolder>() {

    private var reports: List<ReportIcingRequestPayload> = listOf()

    init {
        reports = ArrayList()
    }

    fun setReports(reports: List<ReportIcingRequestPayload>) {
        this.reports = reports
        this.notifyItemRangeChanged(0, this.reports.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sprice_icing_report_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = reports[position]
        val context = holder.mView.context

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        val title = report.ReportingTime.toString()
        holder.titleView.text = title

        val textStyle =  Typeface.BOLD//if (holder.mItem!!.seen) Typeface.NORMAL else Typeface.BOLD
        holder.titleView.setTypeface(null, textStyle)
        //holder.detail1View.setTypeface(null, textStyle)

//        if(report.attachedImages.value.isNotEmpty()) {
//            holder.iconView.setImageURI(report.attachedImages.value[0].toUri())
//        }

        holder.detail1View.text = report.ReportingTime.toString()

        var locationString = ""
//        if (report.location.value != null) {
//            locationString = formatLocation(report.location.value!!, context)
//        }

        holder.detail2View.text = locationString
//        holder.statusButton.visibility = if (mConfirmed) View.GONE else View.VISIBLE
//        holder.removeButton.visibility = if (mConfirmed) View.VISIBLE else View.GONE


//        holder.mView.setOnClickListener { v ->
//            mListener?.onReportViewClicked(v, reports[holder.adapterPosition] )
//        }
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val iconView: ImageView = mView.findViewById<View>(R.id.tool_item_image_view) as ImageView
        val titleView: TextView = mView.findViewById<View>(R.id.tool_item_title_view) as TextView
        val detail1View: TextView = mView.findViewById<View>(R.id.tool_item_detail_1_view) as TextView
        val detail2View: TextView = mView.findViewById<View>(R.id.tool_item_detail_2_view) as TextView
        val statusButton: ImageView = mView.findViewById<View>(R.id.tool_status_button) as ImageView
        val removeButton: ImageButton = mView.findViewById<View>(R.id.tool_remove_button) as ImageButton

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }

    interface OnReportInteractionListener {
        fun onReportViewClicked(v: View, report: ReportIcingViewModel)
        fun onSendReportClicked(v: View, report: ReportIcingViewModel)
        fun onDeleteReportClicked(v: View, report: ReportIcingViewModel)
    }
}

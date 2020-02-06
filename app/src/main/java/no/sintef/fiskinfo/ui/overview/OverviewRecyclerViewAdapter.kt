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
package no.sintef.fiskinfo.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import no.sintef.fiskinfo.R
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [OverviewCardItem] and makes calls to the
 * listeners associated with the items when action buttons are pressed.
 */
class OverviewRecyclerViewAdapter(listener: OverviewRecyclerViewAdapter.OnOverviewCardInteractionListener) :
    RecyclerView.Adapter<OverviewRecyclerViewAdapter.ViewHolder>() {

    private var overviewItems: List<OverviewCardItem>? = null
    private val mListener: OnOverviewCardInteractionListener?

    init {
        overviewItems = ArrayList()
        mListener = listener
    }

    fun setOverviewItems(overviewItemList: List<OverviewCardItem>) {
        this.overviewItems = overviewItemList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.overview_card_with_actions_layout, parent, false)
        return ViewHolder(view)
    }

    private fun goneIfEmpty(text : String?) : Int {
        return if (text != null && text != "") View.VISIBLE else View.GONE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = overviewItems!![position]

        holder.titleView.text = holder.mItem!!.title
        holder.subTitleView.text = holder.mItem!!.subTitle
        holder.icon.setImageResource(holder.mItem!!.imageResource)

        holder.descriptionView.text = holder.mItem!!.description
        holder.descriptionView.visibility = goneIfEmpty(holder.mItem!!.description)
//        holder.descriptionView.visibility = if (holder.descriptionView.text != "") View.GONE else View.VISIBLE
        holder.action1Button.text = holder.mItem!!.action1Text
        holder.action2Button.text = holder.mItem!!.action2Text
        holder.action2Button.visibility = goneIfEmpty(holder.mItem!!.action2Text)

        holder.action1Button.setOnClickListener {mListener?.onAction1Clicked(it, holder.mItem)}
        if (holder.action2Button.text != "")
            holder.action2Button.setOnClickListener {mListener?.onAction2Clicked(it, holder.mItem)}
    }

    override fun getItemCount(): Int {
        return overviewItems!!.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: OverviewCardItem? = null
        val titleView: TextView
        val subTitleView: TextView
        val icon : ImageView
        val descriptionView: TextView
        val action1Button: MaterialButton
        val action2Button: MaterialButton

        init {
            titleView = mView.findViewById(R.id.card_title_text_view) as TextView
            subTitleView = mView.findViewById(R.id.card_top_text_view) as TextView
            icon = mView.findViewById(R.id.card_image_view) as ImageView
            descriptionView = mView.findViewById(R.id.card_main_text_view) as TextView
            action1Button = mView.findViewById(R.id.card_button_1) as MaterialButton
            action2Button = mView.findViewById(R.id.card_button_2) as MaterialButton
        }

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }

    interface OnOverviewCardInteractionListener {
        fun onAction1Clicked(v: View, item: OverviewCardItem?)
        fun onAction2Clicked(v: View, item: OverviewCardItem?)
    }
}

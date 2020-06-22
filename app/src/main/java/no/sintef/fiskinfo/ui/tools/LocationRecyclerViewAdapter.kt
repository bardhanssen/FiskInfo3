package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import no.sintef.fiskinfo.R

class LocationRecyclerViewAdapter : RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder>() {

    var locations =  listOf<Location>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationRecyclerViewAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = locations[position]
        holder.bind(item)
    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
/*
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
*/


        fun bind(item: Location) {
//            binding.sleep = item
//            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)


                //val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                //return ViewHolder(binding)


                val view = layoutInflater
                    .inflate(R.layout.location_list_item, parent, false)

                return ViewHolder(view)
            }
        }
    }


}
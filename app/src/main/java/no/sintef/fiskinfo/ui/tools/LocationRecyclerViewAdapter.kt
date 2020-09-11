package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import no.sintef.fiskinfo.databinding.LocationListItemBinding
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.util.formatLocation

class LocationRecyclerViewAdapter(private val mListener: LocationRecyclerViewAdapter.OnLocationInteractionListener) : RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder>() {

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
        holder.binding.root
            .setOnClickListener { v ->
            mListener.onEditLocationClicked(v, holder.adapterPosition )
        }
    }

    class ViewHolder private constructor(val binding : LocationListItemBinding ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Location) {
            binding.locationItemTextView.text = formatLocation(item, binding.root.context)
            //binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LocationListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    interface OnLocationInteractionListener {
        fun onEditLocationClicked(v: View, itemClicked : Int)
        //fun onViewSnapInMapClicked(v: View, snap: SnapMessage?)
    }


}
package no.sintef.fiskinfo.ui.snap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.Snap;
import no.sintef.fiskinfo.ui.snap.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified
 * TODO: Replace the implementation with code for your data type.
 */
public class SnapRecyclerViewAdapter extends RecyclerView.Adapter<SnapRecyclerViewAdapter.ViewHolder> {

    private List<Snap> snaps;
    private final OnSnapInteractionListener mListener;

    public SnapRecyclerViewAdapter(OnSnapInteractionListener listener) {
        snaps =  new ArrayList<>();
        mListener = listener;
    }

    public void setSnaps(List<Snap> snaps) {
        this.snaps = snaps;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_snap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = snaps.get(position);

        holder.titleView.setText(holder.mItem.sender);

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm", Locale.getDefault());
//        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.datetime_format_yyyy_mm_dd_t_hh_mm_ss), Locale.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setTimeZone(TimeZone.getDefault());
        //holder.detail1View.setText(sdf.format(holder.mItem.getEchogram().timestamp));
        holder.detail1View.setText(holder.mItem.title);
        holder.detail2View.setText(sdf.format(holder.mItem.getEchogram().timestamp)); //holder.mItem.getEchogram().location);

        holder.viewButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onViewSnapClicked(v, holder.mItem);
                }
            }
        });
        holder.shareButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onViewSnapInMapClicked(v, holder.mItem);
               }
            }
        });

/*        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onViewEchogramClicked(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return snaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Snap mItem;
        public final View mView;
        public final TextView titleView;
        public final TextView detail1View;
        public final TextView detail2View;
        // public final ImageView imageView;
        public final ImageButton viewButton;
        public final ImageButton shareButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.snap_item_title_view);
            detail1View = (TextView) view.findViewById(R.id.snap_item_detail_1_view);
            detail2View = (TextView) view.findViewById(R.id.snap_item_detail_2_view);
            // imageView = (ImageView) view.findViewById(R.id.snap_item_image_view);
            shareButton = (ImageButton) view.findViewById(R.id.show_snap_in_map_button);
            viewButton = (ImageButton) view.findViewById(R.id.open_snap_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }

    public interface OnSnapInteractionListener {
        void onViewSnapClicked(View v, Snap snap);
        void onViewSnapInMapClicked(View v, Snap snap);
    }
}

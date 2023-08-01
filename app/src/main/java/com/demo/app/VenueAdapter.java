package com.demo.app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> {

    private List<venue> venueList;

    public VenueAdapter(List<venue> venueList) {
        this.venueList = venueList;
    }
    public void updateData(List<venue> newVenueList) {
        venueList.clear();
        venueList.addAll(newVenueList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView venueNameTextView;
        public TextView venueLocationTextView;
        public TextView venuePriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            venueNameTextView = itemView.findViewById(R.id.venue_name);
            venueLocationTextView = itemView.findViewById(R.id.venue_location);
            venuePriceTextView = itemView.findViewById(R.id.venue_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        venue venue = venueList.get(position);
        Picasso.get()
                .load(new File(venue.getImageUrl())) // Assuming getImageUrl() returns the local file path as a string
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.error_image)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Image loaded successfully, do any additional operations here if needed.
                    }

                    @Override
                    public void onError(Exception e) {
                        // Failed to load image, handle the error here.
                        e.printStackTrace();
                        // You can display a Toast message here to inform the user about the error.
                    }
                });
        holder.venueNameTextView.setText(venue.getName());
        holder.venueLocationTextView.setText(venue.getLocation());
        holder.venuePriceTextView.setText("$" + String.valueOf(venue.getPrice()));
    }

    @Override
    public int getItemCount() {

        return venueList.size();
    }

}


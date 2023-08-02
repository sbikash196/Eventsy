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
import android.util.Log;
import java.util.List;
import android.content.Context;
import android.net.Uri;


public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> {

    private final   List<venue> venueList;
    private final Context context;

    private final List<String> imageUrls;

    public VenueAdapter(Context context, List<venue> venueList, List<String> imageUrls) {
        this.context = context;
        this.venueList = venueList;
        this.imageUrls=imageUrls;
    }

    public void updateData(List<venue> newVenueList) {
        venueList.clear();
        venueList.addAll(newVenueList);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView venueNameTextView;
        public TextView venueLocationTextView;
        public TextView venuePriceTextView;
        public ImageView deleteIconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            venueNameTextView = itemView.findViewById(R.id.venue_name);
            venueLocationTextView = itemView.findViewById(R.id.venue_location);
            venuePriceTextView = itemView.findViewById(R.id.venue_price);
            deleteIconImageView = itemView.findViewById(R.id.delete);
            bind();


        }
        public void bind() {
            deleteIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        venue clickedVenue = venueList.get(position);

                        // Call the deleteVenue method from the UserInterface class
                        UserInterface userInterface = (UserInterface) context;
                        userInterface.deleteVenue(clickedVenue.getId());

                        venueList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
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
        Log.d("VenueAdapter", "imageUrls size: " + imageUrls.size());
        Uri contentUri = Uri.parse(imageUrls.get(position));
        Picasso.get()
                .load(contentUri)
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





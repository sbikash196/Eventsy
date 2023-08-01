package com.demo.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class VenueRegistration extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private Uri selectedImageUri;
    private EditText editVenueName, editVenueLocation, editVenuePrice;
    private ImageView imageVenue;
    private Button venueRegisterButton;

    // ActivityResultLauncher to handle image pick
    private ActivityResultLauncher<Intent> imagePickLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_registration);

        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            Log.d("VenueRegistration", "Selected Image URI: " + selectedImageUri.toString());
                            imageVenue.setImageURI(selectedImageUri);
                        } else {
                            Log.e("VenueRegistration", "Failed to get the image URI");
                        }
                    }
                }
        );

        database g = new database(VenueRegistration.this);

        editVenueName = findViewById(R.id.venue_name);
        editVenueLocation = findViewById(R.id.venue_location);
        editVenuePrice = findViewById(R.id.venue_price);
        imageVenue = findViewById(R.id.image_url);
        venueRegisterButton = findViewById(R.id.venueRegisterButton);

        imageVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGalleryOrCamera();
            }
        });

        venueRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String venue_name = editVenueName.getText().toString();
                String venue_location = editVenueLocation.getText().toString();
                String venue_price = editVenuePrice.getText().toString();
                if (selectedImageUri == null) {
                    Toast.makeText(VenueRegistration.this, "Please select an image.", Toast.LENGTH_SHORT).show();
                    return;
                }
                double parsedVenuePrice;
                try {
                    parsedVenuePrice = Double.parseDouble(venue_price);
                } catch (NumberFormatException e) {
                    // Handle the case where venuePriceStr cannot be converted to a double
                    Toast.makeText(VenueRegistration.this, "Invalid price format.", Toast.LENGTH_SHORT).show();
                    return;
                }
                database db = new database(VenueRegistration.this);
                boolean i = db.insertVenueData(venue_name, venue_location, parsedVenuePrice, selectedImageUri.toString());
                db.close();
                if (i) {
                    Toast.makeText(VenueRegistration.this, "Venue Registration Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VenueRegistration.this, UserInterface.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VenueRegistration.this, "Venue Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImageFromGalleryOrCamera() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickLauncher.launch(intent); // Use the new ActivityResultLauncher
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Log.d("VenueRegistration", "Selected Image URI: " + selectedImageUri.toString());
                imageVenue.setImageURI(selectedImageUri);
            } else {
                Log.e("VenueRegistration", "Failed to get the image URI");
            }
        }
    }
}

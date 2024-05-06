package com.example.firebasedata;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    // Initializing the ImageView
    ImageView rImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting ImageView by its id
        rImage = findViewById(R.id.rImage);

        // We will get the default FirebaseDatabase instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // We will get a DatabaseReference for the "Beautiful" child node
        DatabaseReference databaseReference = firebaseDatabase.getReference("Beautiful");

        // Adding a listener for a single change in the data at this location
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Getting the URL from the dataSnapshot
                String imageUrl = dataSnapshot.getValue(String.class);

                // Loading the image into rImage variable which is ImageView
                Picasso.get().load(imageUrl).into(rImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Showing an error message in Toast if there's a problem
                Toast.makeText(MainActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });
    }



        private void subscribeToImageUploadTopic() {
            // Subscribe to the 'imageUpload' topic
            FirebaseMessaging.getInstance().subscribeToTopic("imageUpload")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Subscription was successful
                                // You can add further logic here if needed
                                System.out.println("Subscribed to topic: imageUpload");
                            } else {
                                // Subscription failed
                                System.out.println("Failed to subscribe to topic: imageUpload");
                                if (task.getException() != null) {
                                    System.out.println("Error: " + task.getException().getMessage());
                                }
                            }
                        }
                    });
    }

}

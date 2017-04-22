package com.example.salalinux.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.salalinux.firebase.modules.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("restaurants");

        myRef.setValue("Hello, World!");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i=dataSnapshot.getChildren().iterator();
                while(i.hasNext()){
                    DataSnapshot resto=(DataSnapshot) i.next();
                    restaurant=resto.getValue(Restaurant.class);
                    Log.d("nombre","Values is "+restaurant.getName());
                }
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //restaurant = dataSnapshot.getValue(Restaurant.class);
                //Log.d("nombre", "Value is: " + restaurant.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("nanjsda", "Failed to read value.", error.toException());
            }
        });
    }
}

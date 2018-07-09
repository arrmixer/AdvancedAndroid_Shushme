package com.example.android.shushme;

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import static com.google.android.gms.location.places.Places.GEO_DATA_API;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Constants
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    // Member variables
    private PlaceListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private GoogleApiClient mGoogleApiClient;


    private Button mAddLocationButton;


    /**
     * Called when the activity is starting
     *
     * @param savedInstanceState The Bundle that contains the data supplied in onSaveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.places_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlaceListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAddLocationButton = findViewById(R.id.add_location_button);

        // TODO (9) Implement the Add Place Button click event to show  a toast message with the permission status
        mAddLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "No Permission granted. ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Permission granted. ", Toast.LENGTH_LONG).show();
                }

            }
        });

        // TODO (4) Create a GoogleApiClient with the LocationServices API and GEO_DATA_API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();


    }

    // TODO (5) Override onConnected, onConnectionSuspended and onConnectionFailed for GoogleApiClient
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "API Client Connection Successful!");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "API Client Connection Suspended!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "API Client Connection Failed!");
    }


    // TODO (8) Implement onLocationPermissionClicked to handle the CheckBox click event
    public void onLocationPermissionClicked(View view) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_FINE_LOCATION);

    }



    // TODO (7) Override onResume and inside it initialize the location permissions checkbox


    @Override
    protected void onResume() {
        super.onResume();

        CheckBox mCheckboxLocalPermissions = findViewById(R.id.location_permissions);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            mCheckboxLocalPermissions.setChecked(false);
        }else{
            mCheckboxLocalPermissions.setChecked(true);
            mCheckboxLocalPermissions.setEnabled(false);
        }

    }
}

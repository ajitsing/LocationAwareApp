package com.singh.ajit.locationawareapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final GoogleApiClient client = new GoogleApiClient.Builder(this)
        .addApi(Awareness.API)
        .addApi(LocationServices.API)
        .build();
    client.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
      @Override
      public void onConnected(@Nullable Bundle bundle) {
        registerGeoFence(client);
      }

      @Override
      public void onConnectionSuspended(int i) {

      }
    });
    client.isConnectionFailedListenerRegistered(new GoogleApiClient.OnConnectionFailedListener() {
      @Override
      public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
      }
    });

    client.connect();
  }

  private void registerGeoFence(GoogleApiClient client) {
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(this, "please give the required permission", Toast.LENGTH_SHORT).show();
      return;
    }

    GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
        .addGeofences(locationsToBeRegistered())
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .build();
    LocationServices.GeofencingApi.addGeofences(client, geofencingRequest, getGeofencePendingIntent());
  }

  private List<Geofence> locationsToBeRegistered() {
    int twentyFourHours = 24 * 60 * 60 * 1000;

    Geofence thoughtworks = new Geofence.Builder()
        .setRequestId("Request1")
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
            Geofence.GEOFENCE_TRANSITION_EXIT)
        .setExpirationDuration(twentyFourHours)
        .setCircularRegion(28.4975105, 77.0939932, 20).build();
    Geofence cyberhub = new Geofence.Builder()
        .setRequestId("Request2")
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
            Geofence.GEOFENCE_TRANSITION_EXIT)
        .setExpirationDuration(twentyFourHours)
        .setCircularRegion(28.4953952, 77.0862784, 30).build();
    Geofence home = new Geofence.Builder()
        .setRequestId("Request3")
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
            Geofence.GEOFENCE_TRANSITION_EXIT)
        .setExpirationDuration(twentyFourHours)
        .setCircularRegion(28.4878087, 77.1042562, 50).build();

    return asList(thoughtworks, cyberhub, home);
  }

  private PendingIntent getGeofencePendingIntent() {
    Intent intent = new Intent(this, LocationAwareService.class);
    return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }
}

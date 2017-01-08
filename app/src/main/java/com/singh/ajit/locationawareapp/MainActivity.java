package com.singh.ajit.locationawareapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements IGeofenceRequestRegistrar {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final GoogleApiClient client = new GoogleApiClient.Builder(this)
        .addApi(Awareness.API)
        .addApi(LocationServices.API)
        .build();

    GeofenceApiHelper geofenceApiHelper = new GeofenceApiHelper(client, this);
    geofenceApiHelper.registerLocations();
  }

  @Override
  public void registerGeofenceRequest(GoogleApiClient client, GeofencingRequest geofencingRequest) {
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(this, "please give the required permission", Toast.LENGTH_SHORT).show();
      return;
    }

    Intent intent = new Intent(this, LocationAwareService.class);
    PendingIntent geoFencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    LocationServices.GeofencingApi.addGeofences(client, geofencingRequest, geoFencePendingIntent);
  }
}

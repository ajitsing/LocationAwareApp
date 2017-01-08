package com.singh.ajit.locationawareapp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.List;

import static java.util.Arrays.asList;

public class GeofenceApiHelper {

  private final GoogleApiClient client;
  private final IGeofenceRequestRegistrar geofenceRequestRegistrar;

  public GeofenceApiHelper(GoogleApiClient client, IGeofenceRequestRegistrar geofenceRequestRegistrar) {
    this.client = client;
    this.geofenceRequestRegistrar = geofenceRequestRegistrar;
  }

  public void registerLocations() {
    client.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
      @Override
      public void onConnected(@Nullable Bundle bundle) {
        registerGeoFence();
      }

      @Override
      public void onConnectionSuspended(int i) {

      }
    });

    client.connect();
  }

  private void registerGeoFence() {
    GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
        .addGeofences(locationsToBeRegistered())
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .build();
    geofenceRequestRegistrar.registerGeofenceRequest(client, geofencingRequest);
  }

  private List<Geofence> locationsToBeRegistered() {
    int twentyFourHours = 24 * 60 * 60 * 1000;

    Geofence atlantaAirport = new Geofence.Builder()
        .setRequestId("atlantaAirport")
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
            Geofence.GEOFENCE_TRANSITION_EXIT)
        .setExpirationDuration(twentyFourHours)
        .setCircularRegion(33.6402486,-84.420513, 20).build();
    Geofence chicagoAirport = new Geofence.Builder()
        .setRequestId("chicagoAirport")
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
            Geofence.GEOFENCE_TRANSITION_EXIT)
        .setExpirationDuration(twentyFourHours)
        .setCircularRegion(41.9741625,-87.9095101, 30).build();

    return asList(atlantaAirport, chicagoAirport);
  }
}

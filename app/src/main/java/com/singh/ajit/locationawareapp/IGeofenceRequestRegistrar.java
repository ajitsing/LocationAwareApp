package com.singh.ajit.locationawareapp;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingRequest;

public interface IGeofenceRequestRegistrar {
  void registerGeofenceRequest(GoogleApiClient client, GeofencingRequest geofencingRequest);
}

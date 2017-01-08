package com.singh.ajit.locationawareapp;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.GeofencingEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationAwareService extends IntentService {

  public LocationAwareService() {
    super(LocationAwareService.class.getSimpleName());
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
    Location location = geofencingEvent.getTriggeringLocation();

    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    List<Address> addresses = new ArrayList<>();
    try {
      addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Intent message = new Intent("com.singh.ajit.action.DISPLAY_NOTIFICATION");
    message.putExtra("detail", !addresses.isEmpty() ? addresses.get(0).getAddressLine(1) : location.toString());

    sendBroadcast(message);
  }
}

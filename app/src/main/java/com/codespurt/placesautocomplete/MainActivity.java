package com.codespurt.placesautocomplete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codespurt.placesautocomplete.util.Alerts;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "PlacesAutoComplete";

    private Alerts alerts;
    private Button choosePlace;

    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choosePlace = (Button) findViewById(R.id.btn_choose_place);

        alerts = new Alerts();
        if (alerts.isGooglePlayServicesAvailable(this)) {
            choosePlace.setOnClickListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        choosePlace.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_place:
                try {
                    // use MODE_FULLSCREEN flag for fullscreen
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    Toast.makeText(this, place.getName(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Place: " + place.getName());
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    Log.d(TAG, status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {

                }
                break;
        }
    }
}
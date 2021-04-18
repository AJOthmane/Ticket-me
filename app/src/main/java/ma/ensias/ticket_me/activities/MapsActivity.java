package ma.ensias.ticket_me.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.forms.ResponseEvent;
import ma.ensias.ticket_me.fragments.EventInfo;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.requests.EventRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
    TODO LIST :


    - and call the api to send the informations
    - get the data sent from eventinfo
    - and solve the date in Event class

 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private Button button ;
    private MarkerOptions marker;
    android.location.Address address;
    String addressString = "";
    String nameOfEvent;
    String datePicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        Intent intent = getIntent();
        nameOfEvent = intent.getStringExtra(EventInfo.NAME_OF_EVENT);
        datePicked = intent.getStringExtra(EventInfo.DATE_OF_EVENT);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm ").parse(datePicked);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mapFragment.getMapAsync(this);
        button = findViewById(R.id.buttonmap);

        Snackbar.make(button,"Appuyez de manière prolongée sur le repère pour activer le déplacement",Snackbar.LENGTH_LONG).show();

        Date finalDate = date;
        button.setOnClickListener(v -> {

            Snackbar.make(v,"Adresse : "+addressString,Snackbar.LENGTH_LONG).show();

            EventRequest infoEvent = new EventRequest(1,nameOfEvent,datePicked,addressString);

            APIInterface apiInterface = APIClient.createService(APIInterface.class);
            Call<ResponseEvent> call = apiInterface.createEvent(infoEvent);
            call.enqueue(new Callback<ResponseEvent>() {
                @Override
                public void onResponse(Call<ResponseEvent> call, Response<ResponseEvent> response) {

                    if(response.code() == 200)
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                        alertDialog.setTitle("Done");
                        alertDialog.setMessage("Key : "+response.body().getKey());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Got it",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }

                    if(response.code() == 500)
                        Log.i("manadish","manadish");

                }
                @Override
                public void onFailure(Call<ResponseEvent> call, Throwable t) {
                    Log.e("Fail : login",t.getMessage());
                }
            });
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng rabat = new LatLng(33.969199, -6.9273029);
        marker = new MarkerOptions().position(rabat).draggable(true);
        googleMap.addMarker(marker);
        getAddress(marker.getPosition());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rabat));
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
            @Override
            public void onMarkerDrag(Marker marker) {

            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                getAddress(marker.getPosition());
            }
        });
    }
    public void getAddress(LatLng position) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            address = geocoder.getFromLocation(position.latitude, position.longitude, 1).get(0);
            addressString = address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
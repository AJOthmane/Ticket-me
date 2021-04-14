package ma.ensias.ticket_me.activities;

import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Locale;

import ma.ensias.ticket_me.R;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);
        button = findViewById(R.id.buttonmap);
        Snackbar.make(button,"Appuyez de manière prolongée sur le repère pour activer le déplacement",Snackbar.LENGTH_LONG).show();
        button.setOnClickListener(v -> {


            Snackbar.make(v,"Adresse : "+addressString,Snackbar.LENGTH_LONG).show();


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
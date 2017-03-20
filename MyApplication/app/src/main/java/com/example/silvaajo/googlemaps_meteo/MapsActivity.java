package com.example.silvaajo.googlemaps_meteo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap m_Map;
    Marker marker;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        m_Map = googleMap;

        // Défini la zone accessible sur la carte, dans ce cas, la Suisse et alentours
        //
        //47.811604, 5.952860            47.816528, 10.514585
        //                      Suisse
        //45.750280, 5.941828            45.800165, 10.518424
        //
        setMapArea(45.750280, 5.941828, 47.816528, 10.514585);
        moveCameraZoom(46.990125, 6.927998, 15.0f);
        //goToLocationZoom(46.990125, 6.927998, 15);
        //goToLocation(46.990125, 6.927998);

        if (m_Map != null){
            m_Map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    startActivity(new Intent(MapsActivity.this, Pop.class));
                }
            });

            m_Map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
            {
                @Override
                public void onMapLongClick(LatLng point){
                    LatLng latlng = point;

                        MarkerOptions options = new MarkerOptions().position(point);
                        marker = m_Map.addMarker(options);
                }
            });
        }
    }
    private void moveCameraZoom(double dbl_lat, double dbl_lng,float f_zoom)
    {
        m_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dbl_lat, dbl_lng), f_zoom));
    }

    //Limite l'accès à la carte Google selon les positions souhaitées. --> Seule la Suisse est entièrement visible
    private void setMapArea(double dbl_SW_lat, double dbl_SW_lng, double dbl_NE_lat, double dbl_NE_lng)
    {
        LatLngBounds Switzerland = new LatLngBounds(new LatLng(dbl_SW_lat, dbl_SW_lng), new LatLng(dbl_NE_lat, dbl_NE_lng));
        m_Map.setLatLngBoundsForCameraTarget(Switzerland);
    }

    public void geoLocate(View view) {

        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();
        Geocoder gc = new Geocoder(this);

        try {
            //Récupère l'adresse d'une ville entrée dans le champ texte
            List<Address> list = gc.getFromLocationName(location, 1);
            Address address = list.get(0);
            final String locality = address.getLocality();

            Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

            final double dbl_lat = address.getLatitude();
            final double dbl_lng = address.getLongitude();
            moveCameraZoom(dbl_lat, dbl_lng, 15);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

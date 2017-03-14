package com.example.silvaajo.googlemaps_meteo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Défini la zone accessible sur la carte, dans ce cas, la Suisse
        //
        //47.811604, 5.952860            47.816528, 10.514585
        //                      Suisse
        //45.750280, 5.941828            45.800165, 10.518424
        //
        setMapArea(45.750280, 5.941828, 47.816528, 10.514585);
        goToLocationZoom(46.990125, 6.927998, 15);
    }
    //Déplace le centre de la caméra sur les coordonées entrées
    private void goToLocation(double lat, double lng){
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = new CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);
    }

    //Déplace le centre de la caméra sur les coordonées entrées et fixe le niveau de zoom
    private void goToLocationZoom(double lat, double lng, float zoom){
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = new CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    //Limite l'accès à la carte Google selon les positions souhaitées. --> Seule la Suisse est entièrement visible
        private void setMapArea(double SW_lat, double SW_lng, double NE_lat, double NE_lng)
    {
        LatLngBounds Switzerland = new LatLngBounds(new LatLng(SW_lat, SW_lng), new LatLng(NE_lat, NE_lng));
        mMap.setLatLngBoundsForCameraTarget(Switzerland);
    }
}

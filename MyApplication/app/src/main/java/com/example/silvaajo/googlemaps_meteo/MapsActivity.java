package com.example.silvaajo.googlemaps_meteo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap m_Map;

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
        m_Map = googleMap;

        // Défini la zone accessible sur la carte, dans ce cas, la Suisse
        //
        //47.811604, 5.952860            47.816528, 10.514585
        //                      Suisse
        //45.750280, 5.941828            45.800165, 10.518424
        //
        setMapArea(45.750280, 5.941828, 47.816528, 10.514585);
        //goToLocationZoom(46.990125, 6.927998, 15);
    }
    /*Déplace le centre de la caméra sur les coordonées entrées
    private void goToLocation(double dbl_lat, double dbl_lng){
        LatLng latlng_position = new LatLng(dbl_lat, dbl_lng);
        CameraUpdate camup_update = new CameraUpdateFactory.newLatLng(latlng_position);
        m_Map.moveCamera(camup_update);
    }

    //Déplace le centre de la caméra sur les coordonées entrées et fixe le niveau de zoom
    private void goToLocationZoom(double dbl_lat, double dbl_lng, float f_zoom){
        LatLng latlng_position = new LatLng(dbl_lat, dbl_lng);
        CameraUpdate camup_update = new CameraUpdateFactory.newLatLngZoom(latlng_position, f_zoom);
        m_Map.moveCamera(camup_update);
    }
    */

    //Limite l'accès à la carte Google selon les positions souhaitées. --> Seule la Suisse est entièrement visible
        private void setMapArea(double dbl_SW_lat, double dbl_SW_lng, double dbl_NE_lat, double dbl_NE_lng)
    {
        LatLngBounds Switzerland = new LatLngBounds(new LatLng(dbl_SW_lat, dbl_SW_lng), new LatLng(dbl_NE_lat, dbl_NE_lng));
        m_Map.setLatLngBoundsForCameraTarget(Switzerland);
    }
}

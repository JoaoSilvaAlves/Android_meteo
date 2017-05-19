package com.example.silvaajo.googlemaps_meteo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        moveCameraZoom(46.7632539,8.3737403,7.8f);

        if (m_Map != null) {

            //Appel de nouvelle activité pour récupérer et afficher les données
            //météo quand l'on appuie n'importe ou sur la carte
            //Paramètres : Latitude et longitude du clic sur la carte
            m_Map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(MapsActivity.this, Pop.class);
                    Bundle b = new Bundle();
                    b.putDouble("lat", latLng.latitude);
                    b.putDouble("lng", latLng.longitude);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });

            //Fonction qui s'execute lors d'un long clic sur la carte,
            //elle place un marqueur sur le lieu du clic
            m_Map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng point) {
                    MarkerOptions options = new MarkerOptions().position(point).draggable(true);
                    marker = m_Map.addMarker(options);
                }
            });

            //Fonction qui limite le zoom de la carte à une certaine hauteur.
            //Si le zoom va au-dessus de 7.8f, la carte est re-zoomée à 7.8f
            m_Map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    float minZoom = 7.8f;
                    if (cameraPosition.zoom < minZoom)
                        m_Map.animateCamera(CameraUpdateFactory.zoomTo(minZoom));
                }
            });
        }
    }

    //Fonction qui déplace la caméra sur les coordonnées et avec le zoom passés en paramètre
    private void moveCameraZoom(double dbl_lat, double dbl_lng, float f_zoom) {
        m_Map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dbl_lat, dbl_lng), f_zoom));
    }

    //Limite l'accès à la carte Google selon les positions souhaitées.
    //--> Seule la Suisse est entièrement visible
    private void setMapArea(double dbl_SW_lat, double dbl_SW_lng, double dbl_NE_lat, double dbl_NE_lng) {
        LatLngBounds Switzerland = new LatLngBounds(new LatLng(dbl_SW_lat, dbl_SW_lng)
                ,new LatLng(dbl_NE_lat, dbl_NE_lng));
        m_Map.setLatLngBoundsForCameraTarget(Switzerland);
    }
    //                                                 __
    //Fonction appellée lors de l'appui sur le bouton |GO|, récupère les coordonnées du lieu
    //entré dans le champ et appel la fonction "moveCameraZoom" pour déplacer la carte sur ce lieu
    public void geoLocate(View view) {
        try {
            EditText et = (EditText) findViewById(R.id.editText);
            String location = et.getText().toString();
            Geocoder gc = new Geocoder(this);

            if(!TextUtils.isEmpty(location)) {

                //Récupère l'adresse d'une ville entrée dans le champ texte
                List<Address> list = gc.getFromLocationName(location, 1);

                if(list.size() > 0){
                    Address address = list.get(0);
                    final String locality = address.getLocality();

                    Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

                    final double dbl_lat = address.getLatitude();
                    final double dbl_lng = address.getLongitude();
                    moveCameraZoom(dbl_lat, dbl_lng, 15);
                    hideKeyboard(this, view);
                }
                else
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("La localité est introuvable, veuillez réessayer !");
                    dlgAlert.setTitle("Swiss Gamap");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.create().show();

                    et.setText("");
                }
            }
            else {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Veuillez entrer le nom d'une localité Suisse !");
                dlgAlert.setTitle("Swiss Gamap");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //                                                                        __
    //Fonction qui ferme le clavier une fois que l'on a appuyé sur le bouton |GO|
    public static void hideKeyboard(Context ctx, View view) {
        InputMethodManager inm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

/*public void geoFocus(View view) {
LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
Criteria criteria = new Criteria();

if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
Location location = LocationServices.FusedLocationApi.getLastLocation(
mGoogleApiClient);
if (location != null) {
m_Map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
}

CameraPosition cameraPosition = new CameraPosition.Builder()
.target(new LatLng(location.getLatitude(), location.getLongitude()))
.zoom(12.8f)
.build();

CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

m_Map.moveCamera(cameraUpdate);

return;

}
}*/
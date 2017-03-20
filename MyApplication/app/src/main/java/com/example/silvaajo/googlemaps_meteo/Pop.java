package com.example.silvaajo.googlemaps_meteo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by SilvaAJo on 20.03.2017.
 */
public class Pop extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Intent myIntent = getIntent();
        Bundle b = myIntent.getExtras();

        Double dbl_lat = b.getDouble("lat");
        Double dbl_lng = b.getDouble("lng");

        getForecast(dbl_lat, dbl_lng);
    }

    public void getForecast(double lat, double lng)
    {
        TextView t = (TextView)findViewById(R.id.tViewLatLng);
        t.setText("Temporaire");
    }

}

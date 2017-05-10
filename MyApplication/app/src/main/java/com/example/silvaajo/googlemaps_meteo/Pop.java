package com.example.silvaajo.googlemaps_meteo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by SilvaAJo on 20.03.2017.
 */
public class Pop extends Activity {

    private static String url = "www.prevision-meteo.com/services/json/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        Intent myIntent = getIntent();
        Bundle b = myIntent.getExtras();

        Double dbl_lat = b.getDouble("lat");
        Double dbl_lng = b.getDouble("lng");

        String response = getForecast(dbl_lat, dbl_lng);

        ProgressBar pgBar = (ProgressBar) findViewById(R.id.progressBar);
        pgBar.setVisibility(View.GONE);

        TextView t = (TextView) findViewById(R.id.responseView);
        t.setText(response);

        //Log.d("!!!!!!!!!!!", new String(response));
    }

    public String getForecast(double lat, double lng) {

        //System.out.println("~-_-~ " + lat + " " + lng);

        /*HttpURLConnection urlConnection = null;

        try{
            final String link = new String("http://www.prevision-meteo.ch/services/json/lat=" + lat + "lng=" + lng);
            //Log.d("~-_-~", link);
            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();

            //System.out.println("~-_-~ " + bufferedReader);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuilder json = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                json.append(line + "\n");
            }
            bufferedReader.close();

            return json.toString();

        } catch (IOException e) {
            Log.e("PlaceholderFragment", "error", e);
            return null;
        }finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }*/
        return null;
    }
}

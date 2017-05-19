package com.example.silvaajo.googlemaps_meteo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.silvaajo.googlemaps_meteo.cpln.ch.countryexempledemo.outils.HTTPClient;
import com.example.silvaajo.googlemaps_meteo.cpln.ch.countryexempledemo.outils.HTTPClientResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by SilvaAJo on 20.03.2017.
 */
public class Pop extends Activity {

    private static String url_base = "http://www.prevision-meteo.ch/services/json/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //Création de la zone d'affichage
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .7), (int) (height * .6));

        //Récupération des coordonnées de la carte
        Intent myIntent = getIntent();
        Bundle b = myIntent.getExtras();
        Double dbl_lat = b.getDouble("lat");
        Double dbl_lng = b.getDouble("lng");

        //Création du lien complet des données météo
        String url = url_base + "lat=" + dbl_lat + "lng=" + dbl_lng;

        try {
            //Appel de fonction qui récupère le nom de la localité d'un clic sur la carte
            SetLocationName(dbl_lat, dbl_lng);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Appel de la fonction pour récupérer les données météo (AsyncTask)
        new GetForecastTask().execute(url);
    }

    //Récupère le nom de la localité d'un clic sur la map et l'affiche sur la zone d'affichage
    public void SetLocationName(double latitude, double longitude) throws IOException {
        Geocoder gc = new Geocoder(Pop.this, Locale.getDefault());
            List<android.location.Address> addressList = gc.getFromLocation(latitude, longitude, 1);

        String rep = addressList.get(0).getLocality();

        if (rep == null)
            rep = "Nom inconnu";

        TextView tvNomLocalite = (TextView) findViewById(R.id.tv_Localite);
        tvNomLocalite.setText(rep);
    }

    //Classe asynchrone qui va chercher les données météo et les afficher dans la fenêtre d'affichage
    public class GetForecastTask extends AsyncTask<String, Void, HTTPClientResponse>{
        private ProgressDialog progress;

        //Création d'un message de chargement
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Pop.this, "Récupération des données...", "Merci de patienter");
        }

        //Appel de fonction pour éxécuter la requête des données météo
        //Fonction "HTTPClient.sendGet" m'a été fournie par M.Sacchetti
        @Override
        protected HTTPClientResponse doInBackground(String... args) {
            HTTPClientResponse clientResponse = HTTPClient.sendGet(args[0], null);
            return clientResponse;
        }

        //Récupération des données météo, affichage
        protected void onPostExecute(HTTPClientResponse clientResponse){
            try{
                TextView tvResultat = (TextView) findViewById(R.id.responseView);
                tvResultat.setText("");

                String response = clientResponse.getResponseContent();

                response = "[" + response + "]";

                JSONArray forecastList = new JSONArray(response);
                JSONObject responseJson;

                for(int i = 0; i < 5; i++) {
                    responseJson = forecastList.getJSONObject(0);
                    if(!responseJson.has("errors")){
                        responseJson = responseJson.getJSONObject("fcst_day_" + i);
                        tvResultat.setText(tvResultat.getText()
                                + responseJson.getString("day_short") + "     "
                                + responseJson.getString("tmin") + "°     "
                                + responseJson.getString("tmax") + "°"
                                + "\n");
                        new DownloadImageTask().execute(responseJson.getString("icon_big"));
                    }
                    else
                    {
                        TextView tvHeader = (TextView) findViewById(R.id.responseHeader);
                        tvHeader.setVisibility(View.GONE);
                        TextView tvBottom = (TextView) findViewById(R.id.responseBottom);
                        tvBottom.setVisibility(View.GONE);
                        tvResultat.setText("Prévisions météorologiques introuvables pour ce lieu");
                    }
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            progress.dismiss();
        }
    }

    //Classe Asynchrone qui récupère l'image des conditions météo et l'affiche dans un ImageView sur la fenêtre d'affichage
    public class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... args) {

            String image_URL = args[0];
            Bitmap img = null;

            try {
                InputStream is = new URL(image_URL).openStream();
                img = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return img;
        }

        protected void onPostExecute(Bitmap img){
            ImageView imgViewIcon = (ImageView) findViewById(R.id.imgViewIcon);
            imgViewIcon.setImageBitmap(img);
        }
    }
}

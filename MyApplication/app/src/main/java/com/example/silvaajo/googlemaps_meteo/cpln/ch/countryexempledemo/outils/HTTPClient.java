package com.example.silvaajo.googlemaps_meteo.cpln.ch.countryexempledemo.outils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Plinio Sacchetti on 01/08/16.
 */
public class HTTPClient {

    public static HTTPClientResponse sendGet(String url, Map<String, String> headers) {

        HTTPClientResponse response = new HTTPClientResponse();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            if (null != headers) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    con.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
                    it.remove();
                }
            }

            response.setResponseCode(con.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            response.setResponseContent(responseContent.toString());

            try {
                response.setResponseJson(new JSONObject(responseContent.toString()));
            } catch (JSONException e) {}

        } catch (Exception e) {
            System.out.println("Exception with HTTPClient GET :" + e.getMessage());
        }

        return response;
    }

    public static HTTPClientResponse sendPost(String url, Map<String, String> headers, JSONObject payload) {

        HTTPClientResponse response = new HTTPClientResponse();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            //add request header
            if (null != headers) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    con.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
                    it.remove();
                }
            }
            con.setRequestProperty("Content-Type", "application/json");

            // Send request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(((payload != null) ? payload.toString() : ""));
            wr.flush();
            wr.close();

            response.setResponseCode(con.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            response.setResponseContent(responseContent.toString());

            try {
                response.setResponseJson(new JSONObject(responseContent.toString()));
            } catch (JSONException e) {}

        } catch (Exception e) {
            System.out.println("Exception with HTTPClient POST :" + e.getMessage());
        }

        return response;
    }

    public static HTTPClientResponse sendPut(String url, Map<String, String> headers, JSONObject payload) {

        HTTPClientResponse response = new HTTPClientResponse();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("PUT");

            //add request header
            if (null != headers) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    con.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
                    it.remove();
                }
            }
            con.setRequestProperty("Content-Type", "application/json");

            // Send request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(((payload != null) ? payload.toString() : ""));
            wr.flush();
            wr.close();

            response.setResponseCode(con.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            response.setResponseContent(responseContent.toString());

            try {
                response.setResponseJson(new JSONObject(responseContent.toString()));
            } catch (JSONException e) {}

        } catch (Exception e) {
            System.out.println("Exception with HTTPClient PUT" + e.getMessage());
        }

        return response;
    }

    public static HTTPClientResponse sendDelete(String url, Map<String, String> headers, JSONObject payload) {

        HTTPClientResponse response = new HTTPClientResponse();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("DELETE");

            //add request header
            if (null != headers) {
                Iterator it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    con.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
                    it.remove();
                }
            }
            con.setRequestProperty("Content-Type", "application/json");

            // Send request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(((payload != null) ? payload.toString() : ""));
            wr.flush();
            wr.close();

            response.setResponseCode(con.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            response.setResponseContent(responseContent.toString());

            try {
                response.setResponseJson(new JSONObject(responseContent.toString()));
            } catch (JSONException e) {}

        } catch (Exception e) {
            System.out.println("Exception with HTTPClient DELETE :" + e.getMessage());
        }

        return response;
    }

    public static Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(HTTPClient.class.getSimpleName(), "Error getting bitmap", e);
        }
        return bm;
    }
}

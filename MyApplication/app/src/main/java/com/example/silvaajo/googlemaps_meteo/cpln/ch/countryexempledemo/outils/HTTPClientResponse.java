package com.example.silvaajo.googlemaps_meteo.cpln.ch.countryexempledemo.outils;

import org.json.JSONObject;

/**
 * Created by Plinio Sacchetti on 01/08/16.
 */
public class HTTPClientResponse {

    private String responseContent;
    private JSONObject responseJson;
    private int responseCode;

    public HTTPClientResponse() {
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public JSONObject getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(JSONObject responseJson) {
        this.responseJson = responseJson;
    }
}

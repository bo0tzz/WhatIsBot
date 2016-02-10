package me.bo0tzz.whatisbot;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


/**
 * Created by boet on 5-2-2016.
 */
public class GoogleHook {
    private final WhatIsBot bot;
    private final String[] keys;
    private int lastKey = 0;

    private static final String API_URL = "https://kgsearch.googleapis.com/v1/entities:search?";

    public GoogleHook(WhatIsBot bot) {
        this.bot = bot;
        this.keys = bot.getKeys();
    }

    public GraphResult query(String query) {

        HttpResponse<String> response = null;
        try {
            response = Unirest.get(getUrl() + query.replace(" ", "+"))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response.getBody().contains("\"error:\"")) {
            return null;
        }

        GraphResult result = null;
        try {
            result = new Gson().fromJson(response.getBody().replaceAll("@", ""), GraphResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getUrl() {
        int chosenKey = ++lastKey;

        if(chosenKey >= keys.length) {

            chosenKey = 0;
            lastKey = 0;
        }

        return API_URL + "key=" + keys[chosenKey] + "&query=";
    }
}

package me.bo0tzz.whatisbot;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public JSONArray query(String query) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(getUrl() + query.replace(" ", "+"))
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return response.getBody().getObject().getJSONArray("itemListElement");
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

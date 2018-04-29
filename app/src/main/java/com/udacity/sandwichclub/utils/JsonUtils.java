package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {


    private static final String NAME = "name";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String ALSOKNOWAS = "alsoKnownAs";
    private static final String DESCRIPTION = "description";
    private static final String IMAGELINK = "image";
    private static final String INGREDIENTS = "ingredients";

    public String LOG_TAG = "";
    public static Sandwich parseSandwichJson(String json) {
        JSONObject jasonObj ;
        JSONObject jsonNameobj;
        String mainName = null;
        String placeOfOrigin = null ;
        String description = null;
        String imagelink = null;
        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnowAs = new ArrayList<>();

        try {
            jasonObj = new JSONObject(json);
            jsonNameobj = jasonObj.getJSONObject(NAME);
            alsoKnowAs = makeList(jsonNameobj.getJSONArray(ALSOKNOWAS));
            placeOfOrigin = jasonObj.optString(PLACE_OF_ORIGIN);
            description = jasonObj.optString(DESCRIPTION);
            imagelink = jasonObj.optString(IMAGELINK);
            ingredients = makeList(jasonObj.getJSONArray(INGREDIENTS));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return Sandwich object
        Sandwich sandwich = new Sandwich(mainName, alsoKnowAs, placeOfOrigin, description, imagelink, ingredients);
        return sandwich;
    }

    private static List<String> makeList(JSONArray jsonArray) {

        List<String> listdata = new ArrayList<String>();
        if (jsonArray != null) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    listdata.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listdata;
    }

}

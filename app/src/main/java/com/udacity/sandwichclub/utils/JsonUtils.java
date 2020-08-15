package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//https://www.androidhive.info/2012/01/android-json-parsing-tutorial/
//https://stackoverflow.com/questions/11012044/parse-string-array-from-json

public class JsonUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private static Sandwich Sandwich = null;

    /**
     * Create a private constructor because no one should ever create a {@link JsonUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name JsonUtils (and an object instance of JsonUtils is not needed).
     */
    private JsonUtils() {
    }

    //https://stackoverflow.com/questions/15702315/unhandled-exception-type-jsonexception

    public static Sandwich parseSandwichJson(String json) {

        // If the JSON string is empty or null, then return early.

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        //https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/47a044f4-4324-4e51-8960-151737705a06/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/8ed3a385-34d5-4ea5-acbe-8b0be72ee9b6
        //https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html

        try {

            //Initialize JSONObject from JSONString

            JSONObject jsonObject = new JSONObject(json);

            //Name into a JSONObject

            JSONObject name = jsonObject.getJSONObject("name");

            //String mainName from Name JSONObject

            String mainName = "";

            if (name.has("mainName")) {
                // Extract the value for the key called "mainName"
                mainName = name.getString("mainName");

            } else {
                mainName = "No mainName!";
            }

            String placeOfOrigin = "";
            String description = "";
            String image = "";

            if (jsonObject.has("placeOfOrigin")) {
                // Extract the value for the key called "placeOfOrigin"
                placeOfOrigin = jsonObject.getString("placeOfOrigin");

            } else {
                placeOfOrigin = "No placeOfOrigin!";
            }

            if (jsonObject.has("description")) {
                // Extract the value for the key called "description"
                description = jsonObject.getString("description");

            } else {
                description = "No description!";
            }

            if (jsonObject.has("image")) {
                // Extract the value for the key called "image"
                image = jsonObject.getString("image");

            } else {
                image = "No image!";
            }

            //TODO Parse String Array from Json
            //https://stackoverflow.com/questions/11012044/parse-string-array-from-json
            //https://reversecoding.net/java-8-convert-list-string-comma/
            //https://github.com/survivingwithandroid/Surviving-with-android/blob/master/JsonTutorial/src/com/survivingwithandro

            //https://discussions.udacity.com/t/help-in-sandwich-club-project/548082/103

            //String ListArray alsoKnownAs to String

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");

            List<String> alsoKnownAs = new ArrayList<String>();

            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add( (String) alsoKnownAsArray.get(i) );
            }

            //String ListArray ingredientsArray to String

            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");

            List<String> ingredients = new ArrayList<String>();

            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add( (String) ingredientsArray.get(i) );
            }

            //List<String> ingredients = new ArrayList<String>();

            Sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {

            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.

            Log.e("JsonUtils", "Problem parsing the JSON results", e);
        }

        return Sandwich;
    }

}

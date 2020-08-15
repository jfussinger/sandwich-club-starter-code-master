package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Glide.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        //https://developer.android.com/reference/android/text/TextUtils.html

        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAsTv.setText("");
        }
        else{
            alsoKnownAsTv.setText(TextUtils.join(",", sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getIngredients().isEmpty()){
            ingredientsTv.setText("");
        }
        else {
            ingredientsTv.setText(TextUtils.join(",", sandwich.getIngredients()));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOriginTv.setText("");
        }
        else {
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()){
            descriptionTv.setText("");
        }
        else {
            descriptionTv.setText(sandwich.getDescription());
        }

        //https://reversecoding.net/java-8-convert-list-string-comma/
        //https://github.com/survivingwithandroid/Surviving-with-android/blob/master/JsonTutorial/src/com/survivingwithandroid/jsontutorial/MainActivity.java

        //List<String> alsoKnownAs = new ArrayList<String>();
        //alsoKnownAs.add("");
        //String[] alsoKnownAsStr = alsoKnownAs.toArray(new String[0]);
        //System.out.println(Arrays.toString(alsoKnownAsStr));

        //List<String> ingredients = new ArrayList<String>();
        //ingredients.add("");
        //String[] ingredientsStr = ingredients.toArray(new String[0]);
        //System.out.println(Arrays.toString(ingredientsStr));

    }
}

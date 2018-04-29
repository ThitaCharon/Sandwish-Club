package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    public TextView tv_description, tv_also_know, tv_place_of_origin, tv_ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tv_place_of_origin = findViewById(R.id.place_of_origin_tv);
        tv_description = findViewById(R.id.description_tv);
        tv_also_know = findViewById(R.id.detail_also_known_tv);
        tv_ingredients = findViewById(R.id.ingredients_tv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {


        if (sandwich.getAlsoKnownAs().isEmpty()){
            tv_also_know.setText("N/A");
        }else{
            tv_also_know.setText(printList(sandwich.getAlsoKnownAs()));
        }
        if(sandwich.getPlaceOfOrigin().isEmpty()){
            tv_place_of_origin.setText("N/A");
        }else{
            tv_place_of_origin.setText(sandwich.getPlaceOfOrigin());
        }
        tv_description.setText(sandwich.getDescription());

        if (sandwich.getIngredients().isEmpty()){
            tv_ingredients.setText("N/A");
        }else {
            tv_ingredients.setText(printList(sandwich.getIngredients()));
        }

    }

    private String printList(List<String> alsoKnownAs) {
        String str = new String ();
            for(int i =0 ; i < alsoKnownAs.size(); i++){
                str += alsoKnownAs.get(i).toString() + " ";
            }
        return str;
    }
}

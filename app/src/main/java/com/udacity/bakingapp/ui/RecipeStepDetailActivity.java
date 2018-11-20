package com.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.RecipeStep;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private static final String TAG = RecipeStepDetailActivity.class.getSimpleName();
    private static final String EXTRA_RECIPE_STEP = "com.udacity.bakingapp.extras.EXTRA_RECIPE_STEP";
    private static final String EXTRA_RECIPE_NAME = "com.udacity.bakingapp.extras.EXTRA_RECIPE_NAME";

    private RecipeStep mRecipeStep;

    public static Intent getStartIntent(Context context, String recipeName, RecipeStep recipeStep) {
        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_NAME, recipeName);
        intent.putExtra(EXTRA_RECIPE_STEP, recipeStep);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Intent intent = getIntent();
        mRecipeStep = null;
        if (intent != null && intent.hasExtra(EXTRA_RECIPE_NAME) && intent.hasExtra(EXTRA_RECIPE_STEP)) {
            mRecipeStep = (RecipeStep) intent.getSerializableExtra(EXTRA_RECIPE_STEP);
            setTitle(intent.getStringExtra(EXTRA_RECIPE_NAME));
        } else {
            closeOnError();
        }

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setRecipeStep(mRecipeStep);
            recipeStepDetailFragment.setRetainInstance(true);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_step_detail_container, recipeStepDetailFragment)
                    .commit();
        }

    }

    private void closeOnError() {
        Toast.makeText(this, R.string.error_close, Toast.LENGTH_SHORT).show();
        finish();
    }
}

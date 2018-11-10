package com.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.models.RecipeStep;

public class RecipeStepListActivity extends AppCompatActivity {

    private static final String TAG = RecipeStepListActivity.class.getSimpleName();
    private static final String EXTRA_RECIPE = "com.udacity.bakingapp.extras.EXTRA_RECIPE";

    private Recipe mRecipe;

    public static Intent getStartIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeStepListActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_list);

        Intent intent = getIntent();
        mRecipe = null;
        if (intent != null && intent.hasExtra(EXTRA_RECIPE)) {
            mRecipe = (Recipe) intent.getSerializableExtra(EXTRA_RECIPE);
        } else {
            closeOnError();
        }

        setTitle(mRecipe.getName());

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepListFragment recipeStepListFragment = new RecipeStepListFragment();
            recipeStepListFragment.setRecipeSteps(mRecipe.getSteps());
            recipeStepListFragment.setRetainInstance(true);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_step_list_container, recipeStepListFragment)
                    .commit();
        }

    }

    public void displayRecipeStepDetails(int position) {
        String recipeName = mRecipe.getName();
        RecipeStep recipeStep = mRecipe.getSteps().get(position);
        Intent intent = RecipeStepDetailActivity.getStartIntent(this, recipeName, recipeStep);
        startActivity(intent);
    }

    private void closeOnError() {
        Toast.makeText(this, R.string.error_close, Toast.LENGTH_SHORT).show();
        finish();
    }
}

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
    public static final String EXTRA_RECIPE = "com.udacity.bakingapp.extras.EXTRA_RECIPE";

    private Recipe mRecipe;
    private boolean mTwoPane;
    private FragmentManager mFragmentManager;

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

        if (findViewById(R.id.fragment_recipe_step_detail_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            RecipeStepListFragment recipeStepListFragment = new RecipeStepListFragment();
            recipeStepListFragment.setRecipeSteps(mRecipe.getSteps());
            recipeStepListFragment.setRetainInstance(true);
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_step_list_container, recipeStepListFragment)
                    .commit();
            if (mTwoPane) {
                RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                recipeStepDetailFragment.setRecipeStep(mRecipe.getSteps().get(0));
                recipeStepDetailFragment.setRetainInstance(true);
                mFragmentManager.beginTransaction()
                        .add(R.id.fragment_recipe_step_detail_container, recipeStepDetailFragment)
                        .commit();
            }
        }

    }

    public void displayRecipeStepDetails(int position) {

        RecipeStep recipeStep = mRecipe.getSteps().get(position);

        if (mTwoPane) {
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setRecipeStep(recipeStep);
            recipeStepDetailFragment.setRetainInstance(true);
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_recipe_step_detail_container, recipeStepDetailFragment)
                    .commit();
        } else {
            String recipeName = mRecipe.getName();
            Intent intent = RecipeStepDetailActivity.getStartIntent(this, recipeName, recipeStep);
            startActivity(intent);
        }
    }

    private void closeOnError() {
        Toast.makeText(this, R.string.error_close, Toast.LENGTH_SHORT).show();
        finish();
    }
}

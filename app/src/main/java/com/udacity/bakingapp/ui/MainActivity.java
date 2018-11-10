package com.udacity.bakingapp.ui;

import android.accounts.NetworkErrorException;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.RecipesNetworkDataListener;
import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesNetworkDataListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Recipe> mRecipes;
    private RecipesViewModel mRecipesViewModel;
    RecipeListFragment mRecipeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecipesViewModel();

        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            mRecipeListFragment = new RecipeListFragment();
            mRecipeListFragment.setRecipes(mRecipes);
            mRecipeListFragment.setRetainInstance(true);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_list_container, mRecipeListFragment)
                    .commit();
        }

    }

    @Override
    public void updateRecipes(List<Recipe> recipes) {
        mRecipes.clear();
        if (recipes != null) {
            mRecipes.addAll(recipes);
        }
        if (mRecipeListFragment != null) {
            mRecipeListFragment.notifyRecipesUpdated();
        }
    }

    public void displayRecipeStepList(int position) {
        Recipe recipe = mRecipes.get(position);
        Intent intent = RecipeStepListActivity.getStartIntent(this, recipe);
        startActivity(intent);
    }

    private void setupRecipesViewModel() {
        mRecipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        if (mRecipesViewModel.getRecipes() == null) {
            mRecipes = new ArrayList<Recipe>();
            mRecipesViewModel.setRecipes(mRecipes);
            try {
                NetworkUtils.loadRecipesFromNetworkResource(this);
            } catch (NetworkErrorException e) {
                e.printStackTrace();
                // TODO
            }
        } else {
            mRecipes = mRecipesViewModel.getRecipes();
        }
    }

}

package com.udacity.bakingapp.ui;

import android.accounts.NetworkErrorException;
import android.arch.lifecycle.ViewModelProviders;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecipesViewModel();

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        recipeListFragment.setRecipes(mRecipes);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_recipe_list_container, recipeListFragment)
                .commit();
    }

    @Override
    public void updateRecipes(List<Recipe> recipes) {
        mRecipes.clear();
        if (recipes != null) {
            mRecipes.addAll(recipes);
        }
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
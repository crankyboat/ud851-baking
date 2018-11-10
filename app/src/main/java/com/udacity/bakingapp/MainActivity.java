package com.udacity.bakingapp;

import android.accounts.NetworkErrorException;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Recipe> mRecipes;
    private RecipesViewModel mRecipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecipesViewModel();
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

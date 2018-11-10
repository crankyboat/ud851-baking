package com.udacity.bakingapp;

import com.udacity.bakingapp.models.Recipe;

import java.util.List;

public interface RecipesNetworkDataListener {
    void updateRecipes(List<Recipe> recipes);
}
package com.udacity.bakingapp;

import com.udacity.bakingapp.models.Recipe;

import java.util.List;

public interface RecipesListener {
    void updateRecipes(List<Recipe> recipes);
}
package com.udacity.bakingapp.ui;

import android.arch.lifecycle.ViewModel;

import com.udacity.bakingapp.models.Recipe;

import java.util.List;

public class RecipesViewModel extends ViewModel {

    private List<Recipe> recipes;

    public RecipesViewModel() {
        recipes = null;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}

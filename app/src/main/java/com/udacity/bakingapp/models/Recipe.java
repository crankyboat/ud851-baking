package com.udacity.bakingapp.models;

import java.util.List;

//{
//    "id": 1,
//    "name": "Nutella Pie",
//    "servings": 8,
//    "image": "",
//    "ingredients": [
//        ....
//    ],
//    "steps": [
//        ....
//    ]
//},

public class Recipe {

    private int id;
    private String name;
    private int servings;
    private String image;
    private List<RecipeIngredient> ingredients;
    private List<RecipeStep> steps;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

}

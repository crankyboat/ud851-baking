package com.udacity.bakingapp.models;

import java.io.Serializable;

//{
//    "quantity": 6,
//    "measure": "TBLSP",
//    "ingredient": "unsalted butter, melted"
//},
//{
//    "quantity": 0.5,
//    "measure": "CUP",
//    "ingredient": "granulated sugar"
//},
//{
//    "quantity": 1,
//    "measure": "K",
//    "ingredient": "Nutella or other chocolate-hazelnut spread"
//},
//{
//    "quantity": 500,
//    "measure": "G",
//    "ingredient": "Mascapone Cheese(room temperature)"
//},

public class RecipeIngredient implements Serializable {

    private double quantity;
    private String measure;
    private String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}

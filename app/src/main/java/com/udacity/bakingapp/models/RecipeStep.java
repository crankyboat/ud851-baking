package com.udacity.bakingapp.models;

import java.io.Serializable;

//{
//    "id": 0,
//    "shortDescription": "Recipe Introduction",
//    "description": "Recipe Introduction",
//    "videoURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
//    "thumbnailURL": ""
//},
//{
//    "id": 1,
//    "shortDescription": "Starting prep",
//    "description": "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.",
//    "videoURL": "",
//    "thumbnailURL": ""
//},
//{
//    "id": 5,
//    "shortDescription": "Finish filling prep",
//    "description": "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.",
//    "videoURL": "",
//    "thumbnailURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4"
//},

public class RecipeStep implements Serializable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoURL;
    }

    public String getThumbnailUrl() {
        return thumbnailURL;
    }

}

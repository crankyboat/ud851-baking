package com.udacity.bakingapp;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.bakingapp.models.Recipe;
import com.udacity.bakingapp.models.RecipeIngredient;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, RecipesNetworkDataListener {

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private List<Recipe> mRecipes;

    ListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
        mRecipes = new ArrayList<Recipe>();
    }

    @Override
    public void onCreate() {
        getRecipes();
    }

    @Override
    public void onDataSetChanged() {
        getRecipes();
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mRecipes = null;
    }

    @Override
    public int getCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Recipe recipe = mRecipes.get(i);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_item);
        views.setTextViewText(R.id.tv_widget_list_item_recipe_name, recipe.getName());

        StringBuilder stringBuilder = new StringBuilder("");
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            stringBuilder.append(ingredient.getIngredient())
                    .append(", ");
        }
        String recipeIngredients = stringBuilder.toString();

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RecipeWidgetProvider.EXTRA_RECIPE_NAME, recipe.getName());
        fillInIntent.putExtra(RecipeWidgetProvider.EXTRA_RECIPE_INGREDIENTS, recipeIngredients);
        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void updateRecipes(List<Recipe> recipes) {
        mRecipes.clear();
        if (recipes != null) {
            mRecipes.addAll(recipes);
        }
    }

    private void getRecipes() {
        try {
            NetworkUtils.loadRecipesFromNetworkResource(mContext, this);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
            // TODO
        }
    }
}

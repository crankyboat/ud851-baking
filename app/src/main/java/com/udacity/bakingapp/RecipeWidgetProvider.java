package com.udacity.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.udacity.bakingapp.ui.MainActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static final String ACTION_DISPLAY_LIST = "com.udacity.backingapp.actions.ACTION_DISPLAY_LIST";
    private static final String ACTION_DISPLAY_INGREDIENTS = "com.udacity.backingapp.actions.ACTION_DISPLAY_INGREDIENTS";
    static final String EXTRA_RECIPE_NAME = "com.udacity.backingapp.extras.RECIPE_NAME";
    static final String EXTRA_RECIPE_INGREDIENTS = "com.udacity.backingapp.extras.RECIPE_INGREDIENTS";

    public static void updateAppWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_list);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ACTION_DISPLAY_LIST, null, null);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String action, @Nullable String recipeName,
                                @Nullable String recipeIngredients) {

        RemoteViews views = null;
        if (action.equals(ACTION_DISPLAY_LIST)) {
            views = getRecipeListRemoteView(context, appWidgetId);
        } else if (action.equals(ACTION_DISPLAY_INGREDIENTS)) {
            views = getRecipeIngredientsRemoteView(context, appWidgetId, recipeName,
                    recipeIngredients);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ACTION_DISPLAY_LIST, null, null);   // TODO
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        if (action.equals(ACTION_DISPLAY_LIST)) {
            updateAppWidget(context, appWidgetManager, appWidgetId, action, null, null);
        } else if (action.equals(ACTION_DISPLAY_INGREDIENTS)) {
            String recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
            String recipeIngredients = intent.getStringExtra(EXTRA_RECIPE_INGREDIENTS);
            updateAppWidget(context, appWidgetManager, appWidgetId, action, recipeName,
                    recipeIngredients);
        }
        super.onReceive(context, intent);
    }

    private static RemoteViews getRecipeListRemoteView(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_list);
        Intent intent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_recipe_list, intent);
        views.setEmptyView(R.id.widget_recipe_list, R.id.widget_empty_view_default_image);

        Intent displayIngredientsIntent = new Intent(context, RecipeWidgetProvider.class);
        displayIngredientsIntent.setAction(ACTION_DISPLAY_INGREDIENTS);
        displayIngredientsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                displayIngredientsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_recipe_list, pendingIntent);

        Intent startActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0,
                startActivityIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_empty_view_default_image, startActivityPendingIntent);

        return views;
    }

    private static RemoteViews getRecipeIngredientsRemoteView(Context context, int appWidgetId,
                                                              String recipeName,
                                                              String recipeIngredients) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);
        views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);
        views.setTextViewText(R.id.tv_widget_recipe_ingredients, recipeIngredients);

        Intent displayListIntent = new Intent(context, RecipeWidgetProvider.class);
        displayListIntent.setAction(ACTION_DISPLAY_LIST);
        displayListIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                displayListIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


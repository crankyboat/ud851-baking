package com.udacity.bakingapp.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.bakingapp.BuildConfig;
import com.udacity.bakingapp.RecipesNetworkDataListener;
import com.udacity.bakingapp.models.Recipe;

import java.lang.reflect.Type;
import java.util.List;

final public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private NetworkUtils() {}

    private static boolean isConnectedToInternet(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return isConnected;
    }

    private static StringRequestListener getStringToJsonRequestListener(final RecipesNetworkDataListener recipesNetworkDataListener) {
        return new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Recipe>>(){}.getType();
                List<Recipe> recipes = gson.fromJson(response, listType);
                recipesNetworkDataListener.updateRecipes(recipes);
                if (BuildConfig.DEBUG) { Log.d(TAG, String.valueOf(recipes)); }
            }
            @Override
            public void onError(ANError error) {
                if (BuildConfig.DEBUG) { Log.d(TAG, String.valueOf(error)); }
                recipesNetworkDataListener.updateRecipes(null);
            }
        };
    }

    public static void loadRecipesFromNetworkResource(Context context, RecipesNetworkDataListener dataListener) throws NetworkErrorException {
        if (isConnectedToInternet(context)) {
            AndroidNetworking.initialize(context);
            AndroidNetworking.get(RECIPES_URL)
                    .build()
                    .getAsString(getStringToJsonRequestListener(dataListener));
        } else {
            throw new NetworkErrorException();
        }
    }

}

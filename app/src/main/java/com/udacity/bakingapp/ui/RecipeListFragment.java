package com.udacity.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.BuildConfig;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements ItemViewOnClickListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    @BindView(R.id.rv_recipe_list) RecyclerView mRecipesRecyclerView;
    private List<Recipe> mRecipes;
    private RecipesAdapter mRecipesAdapter;

    public RecipeListFragment() {
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    public void notifyRecipesUpdated() {
        mRecipesAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), calculateBestSpanCount());
        mRecipesRecyclerView.setLayoutManager(layoutManager);
        mRecipesAdapter = new RecipesAdapter(mRecipes, this);
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);

        return rootView;
    }

    private int calculateBestSpanCount() {
        return 1;
//        int posterHeight = getResources().getDimensionPixelSize(R.dimen.list_item_recipe_height);
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//        return Math.round(outMetrics.widthPixels / posterHeight);
    }

    @Override
    public void onItemViewClick(int position) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "onItemViewClick: " + String.valueOf(position)); }
        ((MainActivity) getActivity()).displayRecipeStepList(position);
    }
}
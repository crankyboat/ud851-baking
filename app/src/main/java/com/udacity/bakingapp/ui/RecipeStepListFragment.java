package com.udacity.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepListFragment extends Fragment implements ItemViewOnClickListener {

    private static final String TAG = RecipeStepListFragment.class.getSimpleName();

    @BindView(R.id.rv_recipe_step_list) RecyclerView mRecipeStepsRecyclerView;
    private List<RecipeStep> mRecipeSteps;
    private RecipeStepsAdapter mRecipeStepsAdapter;

    public RecipeStepListFragment() {
    }

    public void setRecipeSteps(List<RecipeStep> recipeSteps) {
        mRecipeSteps = recipeSteps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecipeStepsRecyclerView.setLayoutManager(layoutManager);
        mRecipeStepsAdapter = new RecipeStepsAdapter(mRecipeSteps, this);
        mRecipeStepsRecyclerView.setAdapter(mRecipeStepsAdapter);

        return rootView;
    }

    @Override
    public void onItemViewClick(int position) {
        Log.d(TAG, "onItemViewClick: " + String.valueOf(position));
        ((RecipeStepListActivity) getActivity()).displayRecipeStepDetails(position);
    }
}
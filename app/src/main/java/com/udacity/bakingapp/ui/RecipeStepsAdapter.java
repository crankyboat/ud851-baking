package com.udacity.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepViewHolder> {

    private List<RecipeStep> mRecipeSteps;
    private ItemViewOnClickListener mItemViewOnClickListener;

    public RecipeStepsAdapter(List<RecipeStep> recipeSteps, ItemViewOnClickListener listener) {
        this.mRecipeSteps = recipeSteps;
        this.mItemViewOnClickListener = listener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item_recipe_step, parent, attachToParentImmediately);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeSteps != null ? mRecipeSteps.size() : 0;
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_list_item_recipe_step_short_description) TextView mRecipeStepShortDescriptionTextView;
        private Context mContext;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            RecipeStep recipeStep = mRecipeSteps.get(position);
            mRecipeStepShortDescriptionTextView.setText(recipeStep.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            mItemViewOnClickListener.onItemViewClick(getAdapterPosition());
        }
    }
}

package com.udacity.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();

    private List<Recipe> mRecipes;
    private ItemViewOnClickListener mItemViewOnClickListener;

    public RecipesAdapter(List<Recipe> recipes, ItemViewOnClickListener listener) {
        this.mRecipes = recipes;
        this.mItemViewOnClickListener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item_recipe, parent, attachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_list_item_recipe_icon) ImageView mRecipeIconImageView;
        @BindView(R.id.tv_list_item_recipe_name) TextView mRecipeNameTextView;
        @BindView(R.id.tv_list_item_recipe_servings) TextView mRecipeServingsTextView;
        private Context mContext;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            Recipe recipe = mRecipes.get(position);
            mRecipeNameTextView.setText(recipe.getName());
            mRecipeServingsTextView.setText(String.valueOf(recipe.getServings()));
            String recipeImageUrl = recipe.getImage();
            if (recipeImageUrl != null && !recipeImageUrl.isEmpty()) {
                Picasso.with(mContext)
                        .load(recipe.getImage())
                        .placeholder(R.drawable.whisk_and_bowl)
                        .into(mRecipeIconImageView);
            }
        }

        @Override
        public void onClick(View itemView) {
            mItemViewOnClickListener.onItemViewClick(getAdapterPosition());
        }

    }
}

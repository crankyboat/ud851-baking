package com.udacity.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment {

    private static final String TAG = RecipeStepDetailFragment.class.getSimpleName();
    private static final String EXTRA_PLAYER_POSITION = "com.udacity.bakingapp.ui.extras.EXTRA_PLAYER_POSITION";

    @BindView(R.id.exo_player_view) SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.iv_recipe_step) ImageView mRecipeStepImageView;
    @BindView(R.id.tv_recipe_step_description) TextView mRecipeStepDescriptionTextView;

    private RecipeStep mRecipeStep;
    private SimpleExoPlayer mExoPlayer;
    private long mExoPlayerCurrentPosition;

    public RecipeStepDetailFragment() {
    }

    public void setRecipeStep(RecipeStep recipeStep) {
        mRecipeStep = recipeStep;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            mExoPlayerCurrentPosition = savedInstanceState.getLong(EXTRA_PLAYER_POSITION, C.TIME_UNSET);
        } else {
            mExoPlayerCurrentPosition = 0;
        }

        String videoUrl = mRecipeStep.getVideoUrl();
        String imageUrl = mRecipeStep.getThumbnailUrl();

        if (videoUrl != null && !videoUrl.isEmpty()) {
            initializePlayer(Uri.parse(videoUrl), mExoPlayerCurrentPosition);
            mExoPlayerView.setVisibility(View.VISIBLE);
        } else if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.with(getActivity())
                    .load(imageUrl)
                    .placeholder(R.drawable.whisk_and_bowl)
                    .into(mRecipeStepImageView);
            mRecipeStepImageView.setVisibility(View.VISIBLE);
        }

        mRecipeStepDescriptionTextView.setText(mRecipeStep.getDescription());

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayerCurrentPosition = mExoPlayer.getCurrentPosition();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();;
        initializePlayer(Uri.parse(mRecipeStep.getVideoUrl()), mExoPlayerCurrentPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXTRA_PLAYER_POSITION, mExoPlayerCurrentPosition);
    }

    private void initializePlayer(Uri mediaUri, long startPosition) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(startPosition);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}

package com.cmonzon.redditapp.ui.frontpage;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmonzon.redditapp.R;
import com.cmonzon.redditapp.data.RedditPost;
import com.cmonzon.redditapp.data.RedditPostData;
import com.cmonzon.redditapp.databinding.FragmentFrontPageBinding;
import com.cmonzon.redditapp.util.ReddiPostUtils;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public class FrontPageFragment extends Fragment implements FrontPageContract.View, FrontPagePostsAdapter.RedditPostOnClickListener {

    private static final String LIST_STATE = "list_state";

    FragmentFrontPageBinding binding;

    private FrontPagePostsAdapter adapter;

    private FrontPageContract.Presenter presenter;

    private Parcelable listState;

    public FrontPageFragment() {
        // Required empty public constructor
    }

    public static FrontPageFragment newInstance() {
        return new FrontPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_front_page, container, false);
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPosts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new FrontPagePostsAdapter(this);
        binding.rvPosts.setAdapter(adapter);
        binding.swipeToRefresh.setOnRefreshListener(() -> presenter.loadFrontPage(true));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE);
        }
        presenter.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE, binding.rvPosts.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    //region view contract
    @Override
    public void showRedditPosts(ArrayList<RedditPost> posts) {
        binding.rvPosts.setVisibility(View.VISIBLE);
        binding.tvErrorMessageDisplay.setVisibility(View.INVISIBLE);
        adapter.setRedditPosts(posts);
        binding.rvPosts.getLayoutManager().onRestoreInstanceState(listState);
    }

    @Override
    public void showProgressIndicator(boolean isVisible) {
        binding.swipeToRefresh.setRefreshing(isVisible);
    }

    @Override
    public void showLoadingError() {
        binding.tvErrorMessageDisplay.setVisibility(View.VISIBLE);
        binding.tvErrorMessageDisplay.setText(R.string.error_message);
        binding.rvPosts.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoDataFound() {
        binding.rvPosts.setVisibility(View.INVISIBLE);
        binding.tvErrorMessageDisplay.setVisibility(View.VISIBLE);
        binding.tvErrorMessageDisplay.setText(R.string.no_data_found);
    }

    @Override
    public void setPresenter(FrontPageContract.Presenter presenter) {
        this.presenter = presenter;
    }
    //endregion

    //region FrontPagePostsAdapter callbacks
    @Override
    public void onRedditPostClick(RedditPostData postData) {
        String path = postData.getPostUrlPath();
        if (!path.isEmpty()) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(ReddiPostUtils.buildPostUrl(path)));
            startActivity(i);
        }
    }
}

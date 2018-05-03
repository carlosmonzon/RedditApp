package com.cmonzon.redditapp.ui.frontpage;


import android.arch.lifecycle.ViewModelProviders;
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
import com.cmonzon.redditapp.data.RedditPostsRepository;
import com.cmonzon.redditapp.data.remote.RedditPostsRemoteDataSource;
import com.cmonzon.redditapp.databinding.FragmentFrontPageBinding;
import com.cmonzon.redditapp.network.RedditService;
import com.cmonzon.redditapp.ui.ViewModelFactory;
import com.cmonzon.redditapp.util.ReddiPostUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author cmonzon
 */
public class FrontPageFragment extends Fragment implements FrontPagePostsAdapter.RedditPostOnClickListener {

    private static final String LIST_STATE = "list_state";

    FragmentFrontPageBinding binding;

    private FrontPagePostsAdapter adapter;

    private Parcelable listState;

    private FrontPageViewModel viewModel;

    private final CompositeDisposable disposable = new CompositeDisposable();

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
        binding.swipeToRefresh.setOnRefreshListener(() -> refreshPost(true));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //create repository
        RedditPostsRepository repository = RedditPostsRepository.getInstance(
                RedditPostsRemoteDataSource.getInstance(new RedditService().getRedditApi(), Schedulers.io()));
        ViewModelFactory viewModelFactory = new ViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FrontPageViewModel.class);

        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE);
        }

        refreshPost(false);
    }

    private void refreshPost(boolean forceUpdate) {
        disposable.clear();
        disposable.add(viewModel.getRedditPost(forceUpdate).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> showProgressIndicator(true))
                .doFinally(() -> showProgressIndicator(false))
                .subscribe(posts -> {
                    //posts list validation
                    if (posts.isEmpty()) {
                        showNoDataFound();
                    } else {
                        showRedditPosts(new ArrayList<>(posts));
                    }
                }, error -> showLoadingError()));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE, binding.rvPosts.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.clear();
    }

    //region private methods
    private void showRedditPosts(ArrayList<RedditPost> posts) {
        binding.rvPosts.setVisibility(View.VISIBLE);
        binding.tvErrorMessageDisplay.setVisibility(View.INVISIBLE);
        adapter.setRedditPosts(posts);
        binding.rvPosts.getLayoutManager().onRestoreInstanceState(listState);
    }


    private void showProgressIndicator(boolean isVisible) {
        binding.swipeToRefresh.setRefreshing(isVisible);
    }

    private void showLoadingError() {
        binding.tvErrorMessageDisplay.setVisibility(View.VISIBLE);
        binding.tvErrorMessageDisplay.setText(R.string.error_message);
        binding.rvPosts.setVisibility(View.INVISIBLE);
    }

    private void showNoDataFound() {
        binding.rvPosts.setVisibility(View.INVISIBLE);
        binding.tvErrorMessageDisplay.setVisibility(View.VISIBLE);
        binding.tvErrorMessageDisplay.setText(R.string.no_data_found);
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

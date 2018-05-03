package com.cmonzon.redditapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cmonzon.redditapp.data.RedditPostsDataSource;
import com.cmonzon.redditapp.ui.frontpage.FrontPageViewModel;

/**
 * @author cmonzon
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RedditPostsDataSource postsDataSource;

    public ViewModelFactory(RedditPostsDataSource dataSource) {
        postsDataSource = dataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FrontPageViewModel.class)) {
            return (T) new FrontPageViewModel(postsDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

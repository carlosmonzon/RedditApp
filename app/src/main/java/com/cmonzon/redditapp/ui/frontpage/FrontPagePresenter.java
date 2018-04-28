package com.cmonzon.redditapp.ui.frontpage;

import android.support.annotation.NonNull;

import com.cmonzon.redditapp.data.RedditPostsDataSource;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author cmonzon
 */
public class FrontPagePresenter implements FrontPageContract.Presenter {

    @NonNull
    private FrontPageContract.View view;

    @NonNull
    private RedditPostsDataSource repository;

    @NonNull
    private CompositeDisposable composite;

    private Scheduler uiScheduler;

    public FrontPagePresenter(@NonNull FrontPageContract.View view, @NonNull RedditPostsDataSource repository, @NonNull Scheduler uiScheduler) {

        this.view = view;
        this.repository = repository;
        this.uiScheduler = uiScheduler;
        composite = new CompositeDisposable();
        view.setPresenter(this);
    }


    @Override
    public void loadFrontPage(boolean forceUpdate) {
        if (forceUpdate) {
            repository.refreshFrontPage();
        }
        composite.clear();
        composite.add(repository.getRedditFrontPage()
                .flattenAsObservable(frontPage -> frontPage.getData().getPosts())
                .toSortedList(((o1, o2) -> o1.getData().getVotes() - o2.getData().getVotes()))
                .observeOn(uiScheduler)
                .subscribe(posts -> {
                    view.showRedditPosts(new ArrayList<>(posts));
                }, error -> view.showLoadingError()));
    }

    @Override
    public void start() {
        loadFrontPage(false);
    }

    @Override
    public void unSubscribe() {
        composite.clear();
    }
}

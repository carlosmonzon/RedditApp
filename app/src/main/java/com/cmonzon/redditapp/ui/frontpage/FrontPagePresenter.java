package com.cmonzon.redditapp.ui.frontpage;

import android.support.annotation.NonNull;

import com.cmonzon.redditapp.data.PreviewImage;
import com.cmonzon.redditapp.data.PreviewSource;
import com.cmonzon.redditapp.data.RedditPostsDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                //create observable from Single
                .flattenAsObservable(frontPage -> frontPage.getData().getPosts())
                //filter post with no data available
                .filter(post -> post.getData() != null)
                //map each post and sort preview images sources
                .map(post -> {
                    if (post.getData().getPreview() != null && post.getData().getPreview().getImages() != null) {
                        List<PreviewImage> previewImageList = post.getData().getPreview().getImages();
                        if (!previewImageList.isEmpty()) {
                            List<PreviewSource> sourceList = previewImageList.iterator().next().getResolutions();
                            //sort preview images to get the smallest one
                            Collections.sort(sourceList, (s1, s2) -> s1.getWidth() - s2.getWidth());
                        }
                    }
                    return post;
                })
                //sort posts in descending order
                .toSortedList(((o1, o2) -> o2.getData().getVotes() - o1.getData().getVotes()))
                .observeOn(uiScheduler)
                .doOnSubscribe(disposable -> view.showProgressIndicator(true))
                .doFinally(() -> view.showProgressIndicator(false))
                .subscribe(posts -> {
                    //posts list validation
                    if (posts.isEmpty()) {
                        view.showNoDataFound();
                    } else {
                        view.showRedditPosts(new ArrayList<>(posts));
                    }
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

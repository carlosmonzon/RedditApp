package com.cmonzon.redditapp.ui.frontpage;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.cmonzon.redditapp.data.PreviewImage;
import com.cmonzon.redditapp.data.PreviewSource;
import com.cmonzon.redditapp.data.RedditPost;
import com.cmonzon.redditapp.data.RedditPostsDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

/**
 * @author cmonzon
 */
public class FrontPageViewModel extends ViewModel {

    @NonNull
    private RedditPostsDataSource repository;

    public FrontPageViewModel(@NonNull RedditPostsDataSource repository) {
        this.repository = repository;
    }

    public Single<List<RedditPost>> getRedditPost(boolean forceUpdate) {
        if (forceUpdate) {
            repository.refreshFrontPage();
        }
        //TODO: This could be a use case to be tested separately
        return repository.getRedditFrontPage()
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
                .toSortedList(((o1, o2) -> o2.getData().getVotes() - o1.getData().getVotes()));

    }

}

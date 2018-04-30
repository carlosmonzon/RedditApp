package com.cmonzon.redditapp.ui.frontpage;

import com.cmonzon.redditapp.data.RedditPost;
import com.cmonzon.redditapp.ui.BasePresenter;
import com.cmonzon.redditapp.ui.BaseView;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public interface FrontPageContract {

    interface View extends BaseView<Presenter> {

        void showRedditPosts(ArrayList<RedditPost> posts);

        void showProgressIndicator(boolean isVisible);

        void showLoadingError();

        void showNoDataFound();
    }

    interface Presenter extends BasePresenter {

        void loadFrontPage(boolean forceUpdate);

    }
}

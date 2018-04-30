package com.cmonzon.redditapp.ui.frontpage;

import com.cmonzon.redditapp.data.Preview;
import com.cmonzon.redditapp.data.PreviewImage;
import com.cmonzon.redditapp.data.PreviewSource;
import com.cmonzon.redditapp.data.RedditData;
import com.cmonzon.redditapp.data.RedditFrontPage;
import com.cmonzon.redditapp.data.RedditPost;
import com.cmonzon.redditapp.data.RedditPostData;
import com.cmonzon.redditapp.data.RedditPostsDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author cmonzon
 */
public class FrontPagePresenterTest {

    private static RedditFrontPage MOCK_DATA;

    private static RedditFrontPage EMPTY_MOCK_DATA;

    @Mock
    private RedditPostsDataSource repository;

    @Mock
    private FrontPageContract.View view;

    private FrontPagePresenter presenter;

    private TestScheduler testScheduler;

    @Before
    public void setUp() {
        //init mock annotations injection
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        //create presenter
        presenter = new FrontPagePresenter(view, repository, Schedulers.trampoline());
        setUpMockData();
    }

    @After
    public void after() {
        presenter.unSubscribe();
    }

    @Test
    public void loadRedditFrontPageAndLoadIntoView() {
        // Given an initialized repository with initialized mock data
        when(repository.getRedditFrontPage()).thenReturn(Single.just(MOCK_DATA));

        // When load front page is requested
        presenter.loadFrontPage(true);

        //execute scheduler actions immediately
        testScheduler.triggerActions();

        // Then redditPost are shown, no errors found and showProgressIndicator
        verify(view).showRedditPosts(any());
        verify(view, never()).showLoadingError();
        verify(view).showProgressIndicator(true);
        verify(view).showProgressIndicator(false);
        //verify force update
        verify(repository).refreshFrontPage();
    }

    @Test
    public void loadEmptyRedditPostsAndLoadIntoView() {
        // Given an initialized repository with initialized mock data
        when(repository.getRedditFrontPage()).thenReturn(Single.just(EMPTY_MOCK_DATA));

        // When load front page is requested
        presenter.loadFrontPage(false);

        //execute scheduler actions immediately
        testScheduler.triggerActions();

        // Then redditPost are shown, no errors found and showProgressIndicator
        verify(view, never()).showRedditPosts(any());
        verify(view, never()).showLoadingError();
        verify(view).showNoDataFound();
    }

    @Test
    public void errorLoadingFrontPage_showError() {
        // Given an initialized repository with initialized mock data
        when(repository.getRedditFrontPage()).thenReturn(Single.error(new Exception()));

        presenter.start();

        //execute scheduler actions immediately
        testScheduler.triggerActions();

        verify(view).showLoadingError();
        verify(view, never()).showRedditPosts(any());

    }

    private void setUpMockData() {
        MOCK_DATA = new RedditFrontPage();
        RedditData data = new RedditData();
        RedditPost post1 = new RedditPost();
        RedditPost post2 = new RedditPost();
        RedditPost post3 = new RedditPost();
        RedditPostData postData1 = new RedditPostData();
        postData1.setVotes(123);
        postData1.setTitle("Votes 123");
        Preview preview = new Preview();
        List<PreviewImage> previewImages = new ArrayList<>();
        List<PreviewSource> imagesSource = new ArrayList<>();
        PreviewSource source1 = new PreviewSource();
        source1.setWidth(120);
        PreviewSource source2 = new PreviewSource();
        source2.setWidth(600);
        PreviewSource source3 = new PreviewSource();
        source3.setWidth(90);
        imagesSource.add(source1);
        imagesSource.add(source2);
        imagesSource.add(source3);
        PreviewImage image = new PreviewImage();
        previewImages.add(image);
        image.setResolutions(imagesSource);
        preview.setImages(previewImages);
        postData1.setPreview(preview);
        RedditPostData postData2 = new RedditPostData();
        postData2.setVotes(789);
        postData2.setTitle("Votes 789");
        RedditPostData postData3 = new RedditPostData();
        postData3.setVotes(4);
        postData3.setTitle("Votes 4");
        post1.setData(postData1);
        post2.setData(postData2);
        post3.setData(postData3);
        ArrayList<RedditPost> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(new RedditPost()); //adding post with empty postData
        data.setPosts(posts);
        MOCK_DATA.setData(data);
        EMPTY_MOCK_DATA = new RedditFrontPage();
        RedditData emptyData = new RedditData();
        emptyData.setPosts(new ArrayList<>());
        EMPTY_MOCK_DATA.setData(emptyData);
    }
}

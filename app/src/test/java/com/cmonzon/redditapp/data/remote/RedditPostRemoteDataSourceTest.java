package com.cmonzon.redditapp.data.remote;

import com.cmonzon.redditapp.data.RedditData;
import com.cmonzon.redditapp.data.RedditFrontPage;
import com.cmonzon.redditapp.network.RedditApi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

/**
 * @author cmonzon
 */
public class RedditPostRemoteDataSourceTest {

    @Mock
    private RedditApi redditApi;

    private RedditPostsRemoteDataSource repository;

    private TestScheduler testScheduler;

    @Before
    public void setUp() {
        //init mock annotations injection
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        repository = RedditPostsRemoteDataSource.getInstance(redditApi, testScheduler);
    }

    @After
    public void destroyRepositoryInstance() {
        RedditPostsRemoteDataSource.destroyInstance();
    }

    @Test
    public void test_getRedditFrontPage() {
        //Given Mock data
        setUpStubbing();
        //When getRedditFrontPage is called
        TestObserver<RedditFrontPage> observer = repository.getRedditFrontPage().test();
        //trigger pending actions
        testScheduler.triggerActions();

        //then response is complete
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValueCount(1);
    }

    //set up mock data
    private void setUpStubbing() {
        RedditFrontPage frontPage = new RedditFrontPage();
        RedditData data = new RedditData();
        data.setDist(1);
        frontPage.setData(data);
        Single<RedditFrontPage> mockObservable = Single.just(frontPage);
        when(redditApi.getRedditFrontPage()).thenReturn(mockObservable);
    }

}

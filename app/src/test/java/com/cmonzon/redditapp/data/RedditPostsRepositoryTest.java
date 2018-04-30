package com.cmonzon.redditapp.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author cmonzon
 */
public class RedditPostsRepositoryTest {

    @Mock
    private RedditPostsDataSource remoteDataSource;

    private RedditPostsRepository repository;

    private RedditFrontPage mockRedditFrontPage;

    @Before
    public void setUp() {
        //init mock annotations injection
        MockitoAnnotations.initMocks(this);
        //create repository with mock remote data source
        repository = RedditPostsRepository.getInstance(remoteDataSource);
        //set up mock data
        mockRedditFrontPage = new RedditFrontPage();
        RedditData data = new RedditData();
        data.setDist(1);
        mockRedditFrontPage.setData(data);
    }

    @After
    public void destroyRepositoryInstance() {
        RedditPostsRepository.destroyInstance();
    }

    @Test
    public void getRedditFrontPage_repositoryCachesAfterFirstSubscriptionFromRemote() {
        //Given the remote data source has data available
        setFrontPageAvailableFromRemote();

        //when to subscription are set
        TestObserver<RedditFrontPage> observer = repository.getRedditFrontPage().test();
        TestObserver<RedditFrontPage> secondObserver = repository.getRedditFrontPage().test();

        //Then redditFrontPage were requested only once from remote and then used cached value.
        verify(remoteDataSource, times(1)).getRedditFrontPage();
        assertFalse(repository.cacheIsDirty);
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValues(mockRedditFrontPage);
        secondObserver.assertNoErrors();
        secondObserver.assertComplete();
        secondObserver.assertValues(mockRedditFrontPage);
    }

    @Test
    public void getRedditFrontPageWithDirtyCache() {
        //Given the remote data source has data available
        setFrontPageAvailableFromRemote();

        //when calling refresh front page, invalidates any cached value and force hitting remoteDataSource again
        repository.getRedditFrontPage().test();
        repository.refreshFrontPage();
        repository.getRedditFrontPage().test();
        verify(remoteDataSource, times(2)).getRedditFrontPage();
    }

    private void setFrontPageAvailableFromRemote() {
        when(remoteDataSource.getRedditFrontPage()).thenReturn(Single.just(mockRedditFrontPage));
    }
}

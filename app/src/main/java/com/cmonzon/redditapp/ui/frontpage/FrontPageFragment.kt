package com.cmonzon.redditapp.ui.frontpage


import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cmonzon.redditapp.R
import com.cmonzon.redditapp.data.RedditPost
import com.cmonzon.redditapp.data.RedditPostData
import com.cmonzon.redditapp.data.RedditPostsRepository
import com.cmonzon.redditapp.data.remote.RedditPostsRemoteDataSource
import com.cmonzon.redditapp.databinding.FragmentFrontPageBinding
import com.cmonzon.redditapp.network.RedditService
import com.cmonzon.redditapp.ui.ViewModelFactory
import com.cmonzon.redditapp.util.buildPostUrl

import java.util.ArrayList

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author cmonzon
 */
class FrontPageFragment : Fragment(), FrontPagePostsAdapter.RedditPostOnClickListener {

    private lateinit var binding: FragmentFrontPageBinding

    private var adapter: FrontPagePostsAdapter? = null

    private var listState: Parcelable? = null

    private var viewModel: FrontPageViewModel? = null

    private val disposable = CompositeDisposable()

    private val LIST_STATE = "list_state"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_front_page, container, false)
        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        adapter = FrontPagePostsAdapter(this)
        binding.rvPosts.adapter = adapter
        binding.swipeToRefresh.setOnRefreshListener { refreshPost(true) }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //create repository
        val repository = RedditPostsRepository.getInstance(
                RedditPostsRemoteDataSource.getInstance(RedditService().redditApi, Schedulers.io()))
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FrontPageViewModel::class.java)

        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE)
        }

        refreshPost(false)
    }

    private fun refreshPost(forceUpdate: Boolean) {
        disposable.clear()
        disposable.add(viewModel!!.getRedditPost(forceUpdate).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> showProgressIndicator(true) }
                .doFinally { showProgressIndicator(false) }
                .subscribe({ posts ->
                    //posts list validation
                    if (posts.isEmpty()) {
                        showNoDataFound()
                    } else {
                        showRedditPosts(ArrayList(posts))
                    }
                }) { _ -> showLoadingError() })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE, binding.rvPosts.layoutManager.onSaveInstanceState())
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }

    //region private methods
    private fun showRedditPosts(posts: ArrayList<RedditPost>) {
        binding.rvPosts.visibility = View.VISIBLE
        binding.tvErrorMessageDisplay.visibility = View.INVISIBLE
        adapter!!.setRedditPosts(posts)
        binding.rvPosts.layoutManager.onRestoreInstanceState(listState)
    }


    private fun showProgressIndicator(isVisible: Boolean) {
        binding.swipeToRefresh.isRefreshing = isVisible
    }

    private fun showLoadingError() {
        binding.tvErrorMessageDisplay.visibility = View.VISIBLE
        binding.tvErrorMessageDisplay.setText(R.string.error_message)
        binding.rvPosts.visibility = View.INVISIBLE
    }

    private fun showNoDataFound() {
        binding.rvPosts.visibility = View.INVISIBLE
        binding.tvErrorMessageDisplay.visibility = View.VISIBLE
        binding.tvErrorMessageDisplay.setText(R.string.no_data_found)
    }

    //endregion

    //region FrontPagePostsAdapter callbacks
    override fun onRedditPostClick(postData: RedditPostData) {
        val path = postData.postUrlPath
        if (!path!!.isEmpty()) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(path.buildPostUrl())
            startActivity(i)
        }
    }

    companion object {
        fun newInstance(): FrontPageFragment {
            return FrontPageFragment()
        }
    }
}

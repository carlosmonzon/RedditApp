package com.cmonzon.redditapp.ui.frontpage;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cmonzon.redditapp.BR;
import com.cmonzon.redditapp.R;
import com.cmonzon.redditapp.data.RedditPost;
import com.cmonzon.redditapp.data.RedditPostData;

import java.util.ArrayList;

/**
 * @author cmonzon
 */
public class FrontPagePostsAdapter extends RecyclerView.Adapter<FrontPagePostsAdapter.ViewHolder> {

    private ArrayList<RedditPost> posts;

    private RedditPostOnClickListener listener;

    public FrontPagePostsAdapter(RedditPostOnClickListener listener) {
        this.listener = listener;
    }

    public void setRedditPosts(ArrayList<RedditPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_reddit_post, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position).getData());
    }

    @Override
    public int getItemCount() {
        if (posts != null) {
            return posts.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RedditPostData postData) {
            binding.setVariable(BR.post, postData);
            binding.setVariable(BR.listener, listener);
            binding.executePendingBindings();
        }
    }


    public interface RedditPostOnClickListener {

        void onRedditPostClick(RedditPostData postData);
    }
}

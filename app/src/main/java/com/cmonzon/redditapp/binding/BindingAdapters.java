package com.cmonzon.redditapp.binding;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmonzon.redditapp.R;
import com.cmonzon.redditapp.data.PreviewImage;
import com.cmonzon.redditapp.data.PreviewSource;
import com.cmonzon.redditapp.data.RedditPost;
import com.cmonzon.redditapp.data.RedditPostData;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import static com.cmonzon.redditapp.util.ReddiPostUtils.withSuffix;

/**
 * @author cmonzon
 */
public class BindingAdapters {

    @BindingAdapter("redditPostImage")
    public static void setStepImageUrl(ImageView view, RedditPostData post) {
        if (post.getPreview() != null && post.getPreview().getImages() != null) {
            List<PreviewImage> previewImageList = post.getPreview().getImages();
            if (!previewImageList.isEmpty()) {
                List<PreviewSource> sourceList = previewImageList.iterator().next().getResolutions();
                if (!sourceList.isEmpty()) {
                    PreviewSource source = sourceList.iterator().next();
                    view.setVisibility(View.VISIBLE);
                    Picasso.with(view.getContext()).load(source.getUrl()).error(R.mipmap.ic_launcher).into(view);
                } else {
                    view.setVisibility(View.GONE);
                }
            } else {
                view.setVisibility(View.GONE);
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("redditVotes")
    public static void setVotes(TextView view, int votes) {
        view.setText(withSuffix(votes));
    }

}

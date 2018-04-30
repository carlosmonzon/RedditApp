package com.cmonzon.redditapp.data;

import java.util.List;

/**
 * @author cmonzon
 */
public class Preview {

    private List<PreviewImage> images;

    private boolean enabled;

    public List<PreviewImage> getImages() {
        return images;
    }

    public void setImages(List<PreviewImage> images) {
        this.images = images;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}


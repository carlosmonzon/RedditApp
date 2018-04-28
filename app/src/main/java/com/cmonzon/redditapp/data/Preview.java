package com.cmonzon.redditapp.data;

import java.util.List;

/**
 * @author cmonzon
 */
public class Preview {

    private List<PreviewImage> previewImages;

    private boolean enabled;

    public List<PreviewImage> getPreviewImages() {
        return previewImages;
    }

    public void setPreviewImages(List<PreviewImage> previewImages) {
        this.previewImages = previewImages;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}


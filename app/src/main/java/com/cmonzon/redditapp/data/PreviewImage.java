package com.cmonzon.redditapp.data;

import java.util.List;

/**
 * @author cmonzon
 */
public class PreviewImage {

    private List<PreviewSource> resolutions;

    public List<PreviewSource> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<PreviewSource> resolutions) {
        this.resolutions = resolutions;
    }
}

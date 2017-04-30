package com.github.maxcriser.grower.async;

public interface ProgressCallback<Progress> {

    void onProgressChanged(Progress progress);

}
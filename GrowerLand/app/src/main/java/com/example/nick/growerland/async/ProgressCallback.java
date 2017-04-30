package com.example.nick.growerland.async;

public interface ProgressCallback<Progress> {

    void onProgressChanged(Progress progress);

}
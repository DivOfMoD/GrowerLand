package com.github.maxcriser.grower.async;

public interface Task<Params, Progress, Result> {

    Result doInBackground(Params pParams, ProgressCallback<Progress> pProgressProgressCallback) throws Exception;

}
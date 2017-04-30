package com.github.maxcriser.grower.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnAsyncTask {

    private final ExecutorService mExecutorService;

    public OwnAsyncTask() {
        int numberOfThreads = 3;
        if (Runtime.getRuntime().availableProcessors() > 2) {
            numberOfThreads = Runtime.getRuntime().availableProcessors();
        }
        this.mExecutorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public OwnAsyncTask(final ExecutorService mExecutorService) {
        this.mExecutorService = mExecutorService;
    }

    public <Params, Progress, Result> void execute(
            final Task<Params, Progress, Result> task,
            final Params param,
            final OnResultCallback<Result, Progress> onResultCallback) {

        mExecutorService.execute(new Runnable() {

            Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void run() {
                try {
                    final Result result = task.doInBackground(param, new ProgressCallback<Progress>() {

                        @Override
                        public void onProgressChanged(final Progress pProgress) {
                            mHandler.post(new Runnable() {

                                @Override
                                public void run() {
                                    if (onResultCallback != null) {
                                        onResultCallback.onProgressChanged(pProgress);
                                    }
                                }
                            });
                        }
                    });
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            if (onResultCallback != null) {
                                onResultCallback.onSuccess(result);
                            }
                        }
                    });
                } catch (final Exception pE) {
                    if (onResultCallback != null) {
                        onResultCallback.onError(pE);
                    }
                }
            }
        });
    }
}

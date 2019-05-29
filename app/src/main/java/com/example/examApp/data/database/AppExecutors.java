package com.example.examApp.data.database;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors Instance;
    private final Executor mDiskIO;
    private final Executor mainThread;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        Executor networkIO1 = networkIO;
        this.mainThread = mainThread;
    }

    public static AppExecutors getInstance() {
        if (Instance == null) {
            synchronized (LOCK) {
                Instance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return Instance;
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }


    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}

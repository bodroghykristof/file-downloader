package com.codecool.filedownloader.progress;

import com.codecool.filedownloader.network.Downloader;
import com.codecool.filedownloader.view.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgressManager {

    private final List<Downloader> downloads = new ArrayList<>();
    private final Logger logger;
    private final ExecutorService executor;

    public ProgressManager(Logger logger, int threads) {
        this.logger = logger;
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public boolean addDownloadProcess(Downloader downloader) {
        return downloads.add(downloader);
    }

    public boolean removeDownloadProcess(Downloader downloader) {
        return downloads.remove(downloader);
    }

    public void downloadFilesWithOneThread() {
        for (Downloader downloader : downloads) {
            downloader.download("../../src/main/single-output/" + downloader.getDomain() + ".html");
        }
    }

    public void downloadFilesWithMultipleThreads() {
        for (Downloader downloader : downloads) {
            executor.submit(() -> {
                downloader.download("../../src/main/multi-output/" + downloader.getDomain() + ".html");
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(12, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resetProgresses() {
        downloads.forEach(Downloader::resetProgress);
    }
}

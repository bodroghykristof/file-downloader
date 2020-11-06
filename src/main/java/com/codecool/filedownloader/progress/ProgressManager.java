package com.codecool.filedownloader.progress;

import com.codecool.filedownloader.network.DownloadLogData;
import com.codecool.filedownloader.network.Downloader;
import com.codecool.filedownloader.view.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProgressManager {

    private final List<Downloader> downloads = new ArrayList<>();
    private final Logger logger;
    private final ExecutorService executor;
    private final int repeats;

    public ProgressManager(Logger logger, int threads, int repeats) {
        this.logger = logger;
        this.executor = Executors.newFixedThreadPool(threads);
        this.repeats = repeats;
    }

    public boolean addDownloadProcess(Downloader downloader) {
        return downloads.add(downloader);
    }

    public boolean removeDownloadProcess(Downloader downloader) {
        return downloads.remove(downloader);
    }

    public void downloadFilesWithOneThread() {
        for (Downloader downloader : downloads) {
            downloadRepeatedly(downloader, "single");
        }
    }

    public void downloadFilesWithMultipleThreads() {
        for (Downloader downloader : downloads) {
            executor.submit(() -> {
                downloadRepeatedly(downloader, "multi");
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(12, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void downloadRepeatedly(Downloader downloader, String mode) {
        for (int i = 0; i < repeats; i++) {
            downloader.download("src/main/" + mode + "-output/" + downloader.getDomain() + ".html");
            downloader.progress(1.0 / repeats);

            List<DownloadLogData> currentState = downloads.stream().map(DownloadLogData::new).collect(Collectors.toList());
            logger.displayDownloadStates(currentState);

        }
    }

}

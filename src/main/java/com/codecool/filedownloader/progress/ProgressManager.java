package com.codecool.filedownloader.progress;

import com.codecool.filedownloader.network.DownloadLogData;
import com.codecool.filedownloader.network.Downloader;
import com.codecool.filedownloader.view.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgressManager {

    public static final int UPDATE_FREQUENCY = 500;
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
            downloader.download();
        }
    }

    public void downloadFilesWithMultipleThreads() {
        for (Downloader downloader : downloads) {
            executor.submit(downloader::download);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(12, TimeUnit.SECONDS);
//            executor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void monitorDownloads() throws IOException {

        List<DownloadLogData> downloadLogData;

        while (!allDownloadsCompleted()) {

            System.out.println("called");

            downloadLogData = new ArrayList<>();

            for (Downloader downloader : downloads) downloader.registerFormerSize();

            try {
                Thread.sleep(UPDATE_FREQUENCY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Downloader downloader : downloads) downloadLogData.add(new DownloadLogData(downloader, UPDATE_FREQUENCY));

        }
    }

    private boolean allDownloadsCompleted() {
        return downloads.stream().allMatch(downloader -> downloader.getContentLength() == downloader.getFormerSize());
    }
}

package com.codecool.filedownloader.network;

public class DownloadLogData {

    private final String domain;
    private final double progress;

    public DownloadLogData(Downloader downloader) {
        this.domain = downloader.getDomain();
        this.progress = downloader.getProgress();
    }

    public String getDomain() {
        return domain;
    }

    public double getProgress() {
        return progress;
    }
}

package com.codecool.filedownloader.network;

import java.io.IOException;

public class DownloadLogData {

    private final String domain;
    private final long contentLength;
    private final long formerSize;
    private final long currentSize;
    private final int timeElapsed;


    public DownloadLogData(Downloader downloader, int timeElapsed) throws IOException {
        this.domain = downloader.getDomain();
        this.contentLength = downloader.getContentLength();
        this.formerSize = downloader.getFormerSize();
        this.currentSize = downloader.getCurrentSize();
        this.timeElapsed = timeElapsed;
    }

    public String getDomain() {
        return domain;
    }



}

package com.codecool.filedownloader.network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Downloader {

    private final URL url;
    private int contentLength;
    private final String domain;
    private final ReadableByteChannel readableByteChannel;
    private final FileChannel fileChannel;
    private double progress = 0;

    public Downloader(String urlString, String domain, String filePath) throws IOException {
        this.url = new URL(urlString);
        this.contentLength = url.openConnection().getContentLength();
        this.domain = domain;
        this.readableByteChannel = Channels.newChannel(url.openStream());
        this.fileChannel = new FileOutputStream(filePath).getChannel();
    }

    public void download() {
        try {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            System.out.println("Could not download site " + url);
        }
    }

    public String getDomain() {
        return domain;
    }

    public void resetProgress() {
        progress = 0;
    }

    public double getProgress() {
        return progress;
    }

    public void progress(double progress) {
        this.progress += progress;
    }

}

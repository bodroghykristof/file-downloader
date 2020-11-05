package com.codecool.filedownloader.network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Downloader {

    private final URL url;
    private final ReadableByteChannel readableByteChannel;
    FileChannel fileChannel;

    public Downloader(String urlString, String filePath) throws IOException {
        this.url = new URL(urlString);
        this.readableByteChannel = Channels.newChannel(url.openStream());
        this.fileChannel = new FileOutputStream(filePath).getChannel();
    }

    public void download() throws IOException {
        long start = System.currentTimeMillis();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        System.out.println("site " + url + " done");
        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
    }
}

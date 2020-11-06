package com.codecool.filedownloader.network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Downloader {

    private final URL url;
    private final String domain;
    private final ReadableByteChannel readableByteChannel;
    private final int repeatTime;

    public Downloader(String urlString, String domain, int repeatTime) throws IOException {
        this.url = new URL(urlString);
        this.readableByteChannel = Channels.newChannel(url.openStream());
        this.repeatTime = repeatTime;
        this.domain = domain;
    }

    public void download(String filePath) {
        try {
            FileChannel fileChannel = new FileOutputStream(filePath).getChannel();
            for (int i = 0; i < repeatTime; i++) {
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            }
        } catch (IOException e) {
            System.out.println("Could not download site " + url);
        }
    }

    public String getDomain() {
        return domain;
    }

}

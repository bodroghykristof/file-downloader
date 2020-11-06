package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;
import com.codecool.filedownloader.progress.ProgressManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class App {

    private static final int repeats = 5;
    private static Map<String, String> sites = new HashMap<>();
    static {
        sites.put("https://www.bbc.com/", "bbc");
        sites.put("https://index.hu/", "index");
        sites.put("https://www.telegraph.co.uk/", "telegraph");
        sites.put("https://telex.hu/","telex");
        sites.put("https://www.origo.hu/index.html", "origo");
        sites.put("https://edition.cnn.com/", "cnn");
        sites.put("https://news.sky.com/", "origo");
        sites.put("https://www.rtl.de/", "origo");
    }


    public static void main(String[] args) throws IOException {

        ProgressManager progressManager = new ProgressManager(4);
        createDownloads(progressManager);
        progressManager.downloadFilesWithOneThread();
        progressManager.downloadFilesWithMultipleThreads();

    }


    private static void createDownloads(ProgressManager progressManager) throws IOException {
        for (String site : sites.keySet()) {
            Downloader downloader = new Downloader(site, sites.get(site), repeats);
            progressManager.addDownloadProcess(downloader);
        }
    }

}


package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;
import com.codecool.filedownloader.progress.ProgressManager;
import com.codecool.filedownloader.view.ConsoleLogger;
import com.codecool.filedownloader.view.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class App {

    private static final int REPEATS = 5;
    private static final int THREADS = 4;
    private static Map<String, String> sites = new HashMap<>();

    static {
        sites.put("https://www.bbc.com/", "bbc");
        sites.put("https://index.hu/", "index");
        sites.put("https://www.telegraph.co.uk/", "telegraph");
        sites.put("https://telex.hu/","telex");
        sites.put("https://www.origo.hu/index.html", "origo");
        sites.put("https://edition.cnn.com/", "cnn");
        sites.put("https://news.sky.com/", "sky");
        sites.put("https://www.rtl.de/", "rtl");
    }


    public static void main(String[] args) throws IOException {

        Logger logger = new ConsoleLogger();
        ProgressManager progressManager = new ProgressManager(logger, THREADS, REPEATS);
        createDownloads(progressManager);
//        progressManager.downloadFilesWithOneThread();
        progressManager.downloadFilesWithMultipleThreads();
    }


    private static void createDownloads(ProgressManager progressManager) throws IOException {
        for (String site : sites.keySet()) {
            Downloader downloader = new Downloader(site, sites.get(site));
            progressManager.addDownloadProcess(downloader);
        }
    }

}


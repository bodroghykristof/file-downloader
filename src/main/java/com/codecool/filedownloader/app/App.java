package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;
import com.codecool.filedownloader.progress.ProgressManager;
import com.codecool.filedownloader.view.ConsoleLogger;
import com.codecool.filedownloader.view.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class App {

    private static final int THREADS = 2;
    private static Map<String, String> sites = new HashMap<>();

    static {

//        sites.put("http://www.fsn.hu/testfiles/1GiB", "fsn");
        sites.put("https://edition.cnn.com/", "cnn");
        sites.put("https://news.sky.com/", "sky");
        sites.put("https://dlcdnets.asus.com/pub/ASUS/nb/DriversForWin10/VGA/Graphic_IGCC_DCH_Intel_F_V27.20.100.8190_18337.exe", "asus");

    }


    public static void main(String[] args) throws IOException {
        Logger logger = new ConsoleLogger();
        ProgressManager progressManager = new ProgressManager(logger, THREADS);
        createDownloads(progressManager);
        progressManager.downloadFilesWithMultipleThreads();
        progressManager.monitorDownloads();
    }


    private static void createDownloads(ProgressManager progressManager) throws IOException {
        for (String site : sites.keySet()) {
            Downloader downloader = new Downloader(site, sites.get(site), "../../src/main/output/" + sites.get(site));
            progressManager.addDownloadProcess(downloader);
        }
    }

}


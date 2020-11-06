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
        // 1GB file for later testing
//        sites.put("http://www.fsn.hu/testfiles/1GiB", "bbc");

        //https://meet.google.com/linkredirect?authuser=0&dest=https%3A%2F%2Fdlcdnets.asus.com%2Fpub%2FASUS%2Fnb%2FDriversForWin10%2FVGA%2FGraphic_IGCC_DCH_Intel_F_V27.20.100.8190_18337.exe

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
        ProgressManager progressManager = new ProgressManager(logger, THREADS);
        createDownloads(progressManager);

//        progressManager.downloadFilesWithOneThread();
//
//        progressManager.resetProgresses();


        try {
            Thread.sleep(1000);
            logger.clearScreen();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* Two different behaviours can be observed depending on whether single-thread operation
        was performed beforehand or not */

        progressManager.downloadFilesWithMultipleThreads();
    }


    private static void createDownloads(ProgressManager progressManager) throws IOException {
        for (String site : sites.keySet()) {
            Downloader downloader = new Downloader(site, sites.get(site), "../../src/main/output/" + sites.get(site) + ".html");
            progressManager.addDownloadProcess(downloader);
        }
    }

}


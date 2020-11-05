package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class App {

    private static String[] sites = new String[]{"https://www.bbc.com/",
            "https://index.hu/",
            "https://www.telegraph.co.uk/",
            "https://telex.hu/",
            "https://www.origo.hu/index.html",
            "https://edition.cnn.com/",
            "https://news.sky.com/",
            "https://www.rtl.de/"};
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {

        // First running tends to take more time, so it is not considered as a valid measurement
        downloadSingleThread(false);

        // Measurements
        downloadSingleThread(true);
        downloadMultiThread();

    }

    private static void downloadSingleThread(boolean logging) throws IOException {
        if (logging) System.out.println("SOLUTION USING A SINGLE THREAD");
        else System.out.println("Preparing for measurement...");
        long start = System.currentTimeMillis();

        for (int i = 0; i < sites.length; i++) {
            downloadSite(i, "src/main/single-output/site", logging);
        }

        long end = System.currentTimeMillis();
        if (logging) System.out.println("OVERALL TIME: " + (end - start) + " ms");
    }

    private static void downloadMultiThread() throws IOException {
        System.out.println("SOLUTION USING MULTI-THREADING");
        long start = System.currentTimeMillis();

        for (int i = 0; i < sites.length; i++) {
            downloadSite(i, "src/main/multi-output/site", true);
        }

        long end = System.currentTimeMillis();
        System.out.println("OVERALL TIME: " + (end - start) + " ms");

    }

    private static void downloadSite(int siteNumber, String path, boolean logging) throws IOException {

        Downloader downloader = new Downloader(sites[siteNumber], path + (siteNumber + 1) + ".html");
        long startPiece = System.currentTimeMillis();

        downloader.download();

        if (logging) {
            System.out.println("site " + sites[siteNumber] + " done");
            System.out.println("Time: " + (System.currentTimeMillis() - startPiece) + " ms");
        }
    }
}


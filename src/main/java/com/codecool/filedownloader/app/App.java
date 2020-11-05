package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class App {

    private static final int repeats = 5;
    private static String[] sites = new String[]{"https://www.bbc.com/",
            "https://index.hu/",
            "https://www.telegraph.co.uk/",
            "https://telex.hu/",
            "https://www.origo.hu/index.html",
            "https://edition.cnn.com/",
            "https://news.sky.com/",
            "https://www.rtl.de/"};

    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException {

//         First running tends to take more time, so it is not considered as a valid measurement
        downloadSingleThread(false);

        // Measurements
        downloadSingleThread(true);
        downloadMultiThread();

    }

    private static void downloadSingleThread(boolean logging) {
        if (logging) System.out.println("SOLUTION USING A SINGLE THREAD");
        else System.out.println("Preparing for measurement...");
        long start = System.currentTimeMillis();

        for (int i = 0; i < sites.length; i++) {
            for (int j = 0; j < repeats; j++) {
                downloadSite(i, "src/main/single-output/site", logging);
            }
        }

        long end = System.currentTimeMillis();
        if (logging) System.out.println("OVERALL TIME: " + (end - start) + " ms");
    }

    private static void downloadMultiThread() {
        System.out.println("SOLUTION USING MULTI-THREADING");
        long start = System.currentTimeMillis();

        for (int i = 0; i < sites.length; i++) {
            int currentSite = i;

            // If inner for-cycle is inside of Runnable average execution time is ca. 6-6.5 sec

            for (int j = 0; j < repeats; j++) {
                executor.submit(() -> {

                    downloadSite(currentSite, "src/main/multi-output/site", true);
                });
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(12, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("OVERALL TIME: " + (end - start) + " ms");

    }

    private static void downloadSite(int siteNumber, String path, boolean logging) {

        try {
            Downloader downloader = new Downloader(sites[siteNumber], path + (siteNumber + 1) + ".html");
            long startPiece = System.currentTimeMillis();

            downloader.download();

            if (logging) {
                System.out.println("site " + sites[siteNumber] + " done");
                System.out.println("Time: " + (System.currentTimeMillis() - startPiece) + " ms");
            }
        } catch (IOException e) {
            System.out.println("Could not download file " + (siteNumber + 1) + ".html");
        }
    }
}


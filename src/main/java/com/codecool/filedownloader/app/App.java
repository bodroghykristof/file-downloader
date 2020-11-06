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

        downloadSingleThread();
        downloadMultiThread();

    }

    private static void downloadSingleThread() {

        for (int i = 0; i < sites.length; i++) {
            for (int j = 0; j < repeats; j++) {
                downloadSite(i, "src/main/single-output/site");
            }
        }

    }

    private static void downloadMultiThread() {

        for (int i = 0; i < sites.length; i++) {
            int currentSite = i;

            for (int j = 0; j < repeats; j++) {
                executor.submit(() -> {

                    downloadSite(currentSite, "src/main/multi-output/site");
                });
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(12, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static void downloadSite(int siteNumber, String path) {

        try {
            Downloader downloader = new Downloader(sites[siteNumber], path + (siteNumber + 1) + ".html");
            downloader.download();

        } catch (IOException e) {
            System.out.println("Could not download file " + (siteNumber + 1) + ".html");
        }

    }
}


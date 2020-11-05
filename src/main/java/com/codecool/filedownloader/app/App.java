package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;

import java.io.IOException;


public class App {

    private static String[] sites = new String[]{"https://www.bbc.com/", "https://index.hu/", "https://www.telegraph.co.uk/"};

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        for (int i = 0; i < sites.length; i++) {
            Downloader downloader = new Downloader(sites[i], "src/main/outputs/site" + (i + 1) + ".html");
            downloader.download();
            System.out.println("site " + (i + 1) + " done");

            long end = System.currentTimeMillis();

            System.out.println("Time: " + (end - start) + " ms");
            start = end;
        }

    }
}


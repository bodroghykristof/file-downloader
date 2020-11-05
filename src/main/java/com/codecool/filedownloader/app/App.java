package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        Downloader downloader = new Downloader("https://www.bbc.com/", "src/main/outputs/bbc.html");
        downloader.download();
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");

    }

}

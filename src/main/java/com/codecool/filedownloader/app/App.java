package com.codecool.filedownloader.app;

import com.codecool.filedownloader.network.Downloader;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        Downloader downloaderOne = new Downloader("https://www.bbc.com/", "src/main/outputs/bbc.html");
        downloaderOne.download();
        System.out.println("BBC done");
        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
        Downloader downloaderTwo = new Downloader("https://index.hu/", "src/main/outputs/index.html");
        downloaderTwo.download();
        System.out.println("Index done");
        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
        Downloader downloaderThree = new Downloader("https://www.telegraph.co.uk/", "src/main/outputs/telegraph.html");
        downloaderThree.download();
        System.out.println("Telegraph done");
        System.out.println("Overall: " + (System.currentTimeMillis() - start) + " ms");

    }

}

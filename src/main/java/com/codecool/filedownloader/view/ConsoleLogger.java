package com.codecool.filedownloader.view;

import com.codecool.filedownloader.network.DownloadLogData;

import java.util.List;

public class ConsoleLogger implements Logger {

    @Override
    public synchronized void displayDownloadStates(List<DownloadLogData> downloads) {
        int domainColumnLength = 12;
        int progressFullLength = 20;
        clearScreen();

        for (DownloadLogData download : downloads) {
            System.out.printf("%s:", download.getDomain());
            System.out.print(" ".repeat(domainColumnLength - download.getDomain().length()));

            int progressLength = (int) ((download.getProgress() * domainColumnLength));

            System.out.print("\u25A6".repeat(progressLength));
            System.out.print(" ".repeat(progressFullLength - progressLength));
            System.out.printf("  %.0f%%%n", (download.getProgress() * 100));

        }
    }

    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[J");
        System.out.flush();
    }
}

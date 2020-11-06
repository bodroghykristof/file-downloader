package com.codecool.filedownloader.view;

import com.codecool.filedownloader.network.DownloadLogData;

import java.util.List;

public class ConsoleLogger implements Logger {

    @Override
    public void displayDownloadStates(List<DownloadLogData> downloads) {
        clearScreen();

        for (DownloadLogData download : downloads) {
            System.out.printf("%s: %.0f%%%n", download.getDomain(), (download.getProgress() * 100));
        }

    }

    private void clearScreen() {
        System.out.print("\033[H\033[J");
        System.out.flush();
    }
}

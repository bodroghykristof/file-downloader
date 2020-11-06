package com.codecool.filedownloader.view;

import com.codecool.filedownloader.network.DownloadLogData;

import java.util.List;

public class ConsoleLogger implements Logger {

    @Override
    public void displayDownloadStates(List<DownloadLogData> downloads) {
        clearScreen();

        for (DownloadLogData download : downloads) {
            System.out.println(download.getDomain() + ": " + (100 * download.getProgress()) + "%");
        }

    }

    private void clearScreen() {
        System.out.print("\033[H\033[J");
        System.out.flush();
    }
}

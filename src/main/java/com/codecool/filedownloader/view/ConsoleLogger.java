package com.codecool.filedownloader.view;

import com.codecool.filedownloader.network.DownloadLogData;

import java.util.List;

public class ConsoleLogger implements Logger {

    private final int[] columnLengths = new int[] {20, 20, 20, 20, 20, 20, 20};
    private final double barFillPercentage = 0.75;

    @Override
    public synchronized void displayDownloadStates(List<DownloadLogData> downloads) {
        clearScreen();
        printHeader();

        for (DownloadLogData download : downloads) {

            int contentLengthInKiloBytes = (int) (download.getContentLength() / 1024);
            int sizeInKiloBytes = (int) (download.getCurrentSize() / 1024);
            int formerSizeInKiloBytes = (int) (download.getFormerSize() / 1024);
            int progressPercentage = (int) (((float) download.getCurrentSize() / (float) download.getContentLength()) * 100);
            int actualSpeed = (int) ((float) (sizeInKiloBytes - formerSizeInKiloBytes) / (float) download.getTimeElapsed() * 1000);
            String remainingSeconds =
                    sizeInKiloBytes == contentLengthInKiloBytes ? "Done" :
                    actualSpeed == 0 ? "Infinite" :
                    (contentLengthInKiloBytes - sizeInKiloBytes) / actualSpeed + " s";


            String[] downloadData = new String[] {
                    download.getDomain(),
                    createProgressBar(progressPercentage, (int) (columnLengths[1] * barFillPercentage)),
                    progressPercentage + "%",
                    contentLengthInKiloBytes  + " kB",
                    sizeInKiloBytes + " kB",
                    actualSpeed + " kB/s",
                    remainingSeconds
            };

            printContentInColumns(downloadData);

        }
    }

    private String createProgressBar(int progress, int barLength) {
        int progressLength = (int) (((float) progress / 100) * barLength);
        return "\u25A6".repeat(progressLength);
    }

    private void printHeader() {
        printContentInColumns(new String[] {"Name", "Progress Bar", "Progress", "Full size", "Downloaded", "Speed", "Remaining"});
        System.out.println();
    }

    private void printContentInColumns(String[] content) {

        if (content.length != columnLengths.length) throw new IllegalArgumentException("Cannot print given content in given table");

        for (int i = 0; i < columnLengths.length; i++) {
            System.out.print(content[i]);
            System.out.print(" ".repeat(columnLengths[i] - content[i].length()));
        }

        System.out.println();
    }

    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[J");
        System.out.flush();
    }
}

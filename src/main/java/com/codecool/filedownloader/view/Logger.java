package com.codecool.filedownloader.view;

import com.codecool.filedownloader.network.DownloadLogData;

import java.util.List;

public interface Logger {

    void displayDownloadStates(List<DownloadLogData> downloads);

    void clearScreen();
}

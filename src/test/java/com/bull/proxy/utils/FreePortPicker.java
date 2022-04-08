package com.bull.proxy.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class FreePortPicker {

    public static int findAvailablePort() {
        try (var serverSocket = new ServerSocket(0);) {
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.bull.proxy;

import com.bull.proxy.utils.CounterRegistry;
import com.bull.proxy.utils.SysOutLoggingListener;
import io.netty.handler.logging.LogLevel;

public class Main {

    public static void main(String[] args) {
        int localPort = Integer.parseInt(System.getProperty("localPort", "8443"));
        String remoteHost = System.getProperty("remoteHost", "google.com");
        int remotePort = Integer.parseInt(System.getProperty("remotePort", "443"));
        LogLevel logLevel = LogLevel.valueOf(System.getProperty("logLevel", LogLevel.ERROR.name()));

        Proxy proxy = new Proxy(localPort, remoteHost, remotePort, new CounterRegistry(new SysOutLoggingListener()), logLevel);
        System.err.println("Proxying *:" + localPort + " to " + remoteHost + ':' + remotePort + " ...");
    }
}

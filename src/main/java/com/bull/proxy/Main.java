package com.bull.proxy;

import com.bull.proxy.utils.CounterRegistry;
import com.bull.proxy.utils.SysOutLoggingListener;

public class Main {

    public static void main(String[] args) {
        int localPort = Integer.parseInt(System.getProperty("localPort", "8443"));
        String remoteHost = System.getProperty("remoteHost", "google.com");
        int remotePort = Integer.parseInt(System.getProperty("remotePort", "443"));

        Proxy proxy = new Proxy(localPort, remoteHost, remotePort, new CounterRegistry(new SysOutLoggingListener()));
        System.err.println("Proxying *:" + localPort + " to " + remoteHost + ':' + remotePort + " ...");
    }
}

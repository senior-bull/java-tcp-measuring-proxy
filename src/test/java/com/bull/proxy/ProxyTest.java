package com.bull.proxy;

import com.bull.proxy.echo.EchoClient;
import com.bull.proxy.echo.EchoServer;
import com.bull.proxy.utils.CounterRegistry;
import com.bull.proxy.utils.FreePortPicker;
import com.bull.proxy.utils.SysOutLoggingListener;
import io.netty.handler.logging.LogLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProxyTest {

    @Test
    public void test() {
        var counterRegistry = new CounterRegistry(new SysOutLoggingListener());
        try (var echoServer = new EchoServer(FreePortPicker.findAvailablePort())) {
            int proxyPort = FreePortPicker.findAvailablePort();
            try (var proxy = new Proxy(proxyPort, "127.0.0.1", echoServer.getPort(), counterRegistry, LogLevel.INFO)) {

                var client = new EchoClient(proxyPort, 348, 3);
                client.sendMessage();

                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Assertions.assertEquals(348 * 3, echoServer.getBytesProcessed());

                System.out.println(counterRegistry.availableCounters());
            }
        }
    }
}

package com.bull.proxy.echo;

import com.bull.proxy.utils.FreePortPicker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EchoClientServerTest {

    @Test
    public void test() {
        try (var server = new EchoServer(FreePortPicker.findAvailablePort())) {

            Assertions.assertEquals(0, server.getBytesProcessed());
        }

        try (var server = new EchoServer(FreePortPicker.findAvailablePort())) {

            var client = new EchoClient(server.getPort(), 256);
            client.sendMessage();

            Assertions.assertEquals(256, server.getBytesProcessed());
        }

        try (var server = new EchoServer(FreePortPicker.findAvailablePort())) {

            var client = new EchoClient(server.getPort(), 1024);
            client.sendMessage();

            Assertions.assertEquals(1024, server.getBytesProcessed());
        }
    }
}

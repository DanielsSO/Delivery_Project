package org.example.utils;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

public class GoogleLocalServer {

    private final LocalServerReceiver receiver;

    public GoogleLocalServer() {
        this.receiver = new LocalServerReceiver.Builder()
                .setHost("localhost")
                .setPort(8888)
                .build();
    }

    public LocalServerReceiver getReceiver() {
        return receiver;
    }
}

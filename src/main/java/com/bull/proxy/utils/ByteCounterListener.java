package com.bull.proxy.utils;

public interface ByteCounterListener {

    ByteCounterListener EMPTY = new ByteCounterListener() {
        @Override
        public void onCounterRegistered(ByteCounter byteCounter) {

        }

        @Override
        public void onCounterModified(ByteCounter byteCounter) {

        }

        @Override
        public void onCounterClosed(ByteCounter byteCounter) {

        }
    };

    void onCounterRegistered(ByteCounter byteCounter);

    void onCounterModified(ByteCounter byteCounter);

    void onCounterClosed(ByteCounter byteCounter);
}

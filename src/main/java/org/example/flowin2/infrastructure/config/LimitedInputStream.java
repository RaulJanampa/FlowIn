package org.example.flowin2.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends InputStream {
    private final InputStream delegate;
    private long remaining;

    public LimitedInputStream(InputStream delegate, long limit) {
        this.delegate = delegate;
        this.remaining = limit;
    }

    @Override
    public int read() throws IOException {
        if (remaining <= 0) return -1;
        int b = delegate.read();
        if (b != -1) remaining--;
        return b;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (remaining <= 0) return -1;
        int toRead = (int) Math.min(len, remaining);
        int bytesRead = delegate.read(b, off, toRead);
        if (bytesRead != -1) remaining -= bytesRead;
        return bytesRead;
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}

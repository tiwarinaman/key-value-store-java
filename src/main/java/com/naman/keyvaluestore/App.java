package com.naman.keyvaluestore;

import com.naman.keyvaluestore.config.ConfigLoader;
import com.naman.keyvaluestore.server.Server;

public class App {
    public static void main(String[] args) {
        int port = Integer.parseInt(ConfigLoader.get("server.port"));
        int threadPoolSize = Integer.parseInt(ConfigLoader.get("thread.pool.size"));
        new Server(port, threadPoolSize).start();
    }
}

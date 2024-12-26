package com.naman.keyvaluestore.server;

import com.naman.keyvaluestore.commands.CommandFactory;
import com.naman.keyvaluestore.storage.Storage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final ExecutorService threadPool;

    public Server(int port, int threadPoolSize) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        Storage storage = Storage.getInstance();
        CommandFactory commandFactory = CommandFactory.getInstance();

        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("Server started on port " + port);

            while (true) {
                SocketChannel client = serverSocket.accept();
                threadPool.submit(new ClientHandler(client, storage, commandFactory));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        System.out.println("Shutting down server...");
        threadPool.shutdown();
    }

}

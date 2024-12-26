package com.naman.keyvaluestore.server;

import com.naman.keyvaluestore.commands.BaseCommand;
import com.naman.keyvaluestore.commands.CommandFactory;
import com.naman.keyvaluestore.storage.Storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final SocketChannel client;
    private final Storage storage;
    private final CommandFactory commandFactory;

    public ClientHandler(SocketChannel client, Storage storage, CommandFactory commandFactory) {
        this.client = client;
        this.storage = storage;
        this.commandFactory = commandFactory;
    }

    @Override
    public void run() {
        try (InputStream clientInput = client.socket().getInputStream();
             OutputStream clientOutput = client.socket().getOutputStream();
             Scanner scanner = new Scanner(clientInput)) {

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String[] parts = input.split(" ");
                String commandName = parts[0];
                String[] args = parts.length > 1 ? input.substring(commandName.length()).trim().split(" ") : new String[0];

                BaseCommand command = commandFactory.getCommand(commandName);
                if (command == null) {
                    clientOutput.write("ERROR: Unknown command\n".getBytes());
                    continue;
                }

                Object result = command.execute(args, storage);
                clientOutput.write((result + "\n").getBytes());
            }
        } catch (Exception e) {
            System.err.println("Client handler error: " + e.getMessage());
        }
    }
}

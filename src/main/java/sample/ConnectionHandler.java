package sample;

import sample.types.SendMessageTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import static java.lang.System.err;
import static java.lang.System.out;

public class ConnectionHandler {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8081;
    private static final int TIMEOUT = 600;

    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader reader;

    public ConnectionHandler() {
        try {
            socket = new Socket();
            out.println(String.format("Connecting to server at %s:%s with %dms timeout", SERVER_ADDRESS, SERVER_PORT,
                    TIMEOUT));
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), TIMEOUT);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            out.println("Connection successful!");
        } catch (SocketTimeoutException e) {
            err.println("Could not connect to the server. Timeout !");
        } catch (IOException e) {
            err.println("Could not connect to the server.");
        }
    }

    public void sendMessage(String message) throws IOException {
        outputStreamWriter.write(message);
        outputStreamWriter.flush();
    }

    public String receiveMessage() throws IOException {
        String line = reader.readLine();
        if (line == null) throw new IOException();
        out.println(String.format("Received %d bytes: %s", line.length(), line));
        return String.valueOf(line);
    }

    public void closeConnection() {
        try {
            socket.close();
            outputStreamWriter.close();
            inputStreamReader.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("Error with closing connection: " + e.getMessage());
        }
    }
}

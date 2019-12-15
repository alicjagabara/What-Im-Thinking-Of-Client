package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static java.lang.System.err;
import static java.lang.System.out;

public class ConnectionHandler {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8080;
    private static final int TIMEOUT = 5000;

    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;

    public ConnectionHandler() {
        try {
            socket = new Socket();
            out.println(String.format("Connecting to server at %s:%s with %dms timeout", SERVER_ADDRESS, SERVER_PORT,
                    TIMEOUT));
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), TIMEOUT);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            out.println("Connection successful!");
        } catch (IOException e) {
            err.println("Could not connect to the server");
        }
    }

    public void sendMessage(String message) throws IOException {
        outputStreamWriter.write(message + "\n");
        outputStreamWriter.flush();
        out.println("send line : " + message);
    }

    public String receiveMessage() throws IOException {
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        out.println(String.format("Received %d bytes: %s", line.length(), line));
        return String.valueOf(line);
    }

    public void closeConnection() throws IOException {
        socket.close();
        outputStreamWriter.close();
        inputStreamReader.close();
    }
}

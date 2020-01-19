package sample;

import lombok.Getter;

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

    private static final String SERVER_ADDRESS = Config.getProperty("server.address");
    private static final int SERVER_PORT = Config.getInteger("server.port");
    private static final int TIMEOUT = Config.getInteger("server.timeout");

    @Getter
    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader reader;

    public ConnectionHandler() {
        connect();
    }

    private void connect() {
        try {
            socket = new Socket();
            out.println(String.format("Connecting to server at %s:%s with %dms timeout",
                    SERVER_ADDRESS,
                    SERVER_PORT,
                    TIMEOUT));
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), TIMEOUT);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            out.println("Connection successful!");
        } catch (SocketTimeoutException e) {
            err.println("Could not connect to the server. Connection timeout!");
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
        return line;
    }

    public void closeConnection() {
        try {
            socket.close();
            if (outputStreamWriter != null) outputStreamWriter.close();
            if (inputStreamReader != null) inputStreamReader.close();
            if (reader != null) reader.close();
        } catch (IOException e) {
            System.out.println("Error with closing connection: " + e.getMessage());
        }
    }
}

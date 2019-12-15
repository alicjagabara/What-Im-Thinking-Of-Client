package sample;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import static java.lang.System.err;
import static java.lang.System.out;

public class ConnectionHandler {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8082;
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
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            out.println("Connection successful!");
        } catch (IOException e) {
            err.println("Could not connect to the server");
        }
    }

    public void sendMessage(String message) throws IOException {
        outputStreamWriter.write(message + "\n");
        outputStreamWriter.flush();
    }

    public String receiveMessage() throws IOException {
        String message = "";
        char[] line = new char[16];
        inputStreamReader.read(line);
        return message;
    }

    public void closeConnection() throws IOException {
        socket.close();
        outputStreamWriter.close();
        inputStreamReader.close();
    }
}

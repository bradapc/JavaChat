import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SocketThread extends Thread {
    private Socket socket;
    private ChatServer chatServer;

    public SocketThread(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    public void run() {
        while (true) {
            try {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int byteRead;
                while ((byteRead = input.read()) != -1) {
                    buffer.write(byteRead);

                    if (byteRead == '\n') {
                        String message = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
                        chatServer.receiveMessage(message);
                        buffer.reset();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

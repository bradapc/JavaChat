import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketThread extends Thread {
    private Socket socket;
    private ChatServer chatServer;
    private String username;

    public SocketThread(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public void run() {
        while (true) {
            try {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int byteRead;
                while ((byteRead = input.read()) != -1) {
                    buffer.write(byteRead);

                    if (byteRead == '\0') {
                        byte[] bytes = buffer.toByteArray();
                        if (buffer.toByteArray()[0] == 42) {
                            username = new String(bytes, 1, bytes.length - 2, StandardCharsets.UTF_8);
                            chatServer.broadcastMessage(username + " has joined the chat. There are " + chatServer.getConnectedUsers() + "/" +
                                    chatServer.MAX_CONNECTIONS + " users connected.");
                        } else {
                            String message = new String(bytes, 0, bytes.length - 1, StandardCharsets.UTF_8);
                            chatServer.receiveMessage(message, this);
                        }
                        buffer.reset();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

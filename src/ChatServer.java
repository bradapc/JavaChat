import java.net.ServerSocket;

public class ChatServer {
    private int port;

    public ChatServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ChatServer cs = new ChatServer(4433);
    }
}

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class ChatServer {
    private SocketThread[] socketThreads;

    public ChatServer() {
        socketThreads = new SocketThread[10];
        ChatServerListener chatServerListener = new ChatServerListener(4433, this);
    }

    public void acceptConnection(Socket socket) {
        SocketThread socketThread = new SocketThread(socket);
        String clientAddrStr = socket.getInetAddress().getHostAddress();
        int clientPort = socket.getPort();
        System.out.println("Accepted connection from " + clientAddrStr + ":" + clientPort);
        for (int i = 0; i < socketThreads.length; i++) {
            if (socketThreads[i] == null) {
                socketThreads[i] = socketThread;
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
    }
}

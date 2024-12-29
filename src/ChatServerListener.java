import java.net.ServerSocket;

public class ChatServerListener {
    private int port;
    ChatServer chatServer;

    public ChatServerListener(int port, ChatServer chatServer) {
        try {
            this.chatServer = chatServer;
            ServerSocket serverSocket = new ServerSocket(port);
            handleIncomingConnections(serverSocket);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleIncomingConnections(ServerSocket serverSocket) {
        try {
            while (true) {
                chatServer.acceptConnection(serverSocket.accept());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

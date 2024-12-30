import java.net.ServerSocket;

public class ChatServerListener extends Thread {
    ChatServer chatServer;
    private ServerSocket serverSocket;

    public ChatServerListener(int port, ChatServer chatServer) {
        try {
            this.chatServer = chatServer;
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            while (true) {
                chatServer.acceptConnection(serverSocket.accept());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

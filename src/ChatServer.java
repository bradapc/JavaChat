import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class ChatServer {
    private SocketThread[] socketThreads;

    public ChatServer() {
        socketThreads = new SocketThread[10];
        ChatServerListener chatServerListener = new ChatServerListener(4433, this);
        chatServerListener.start();
        System.out.println("ChatServer is now running.");
    }

    public void acceptConnection(Socket socket) {
        SocketThread socketThread = new SocketThread(socket, this);
        String clientAddrStr = socket.getInetAddress().getHostAddress();
        int clientPort = socket.getPort();
        System.out.println("Accepted connection from " + clientAddrStr + ":" + clientPort);
        for (int i = 0; i < socketThreads.length; i++) {
            if (socketThreads[i] == null) {
                socketThreads[i] = socketThread;
                socketThreads[i].start();
                return;
            }
        }
    }

    public void logMessage(String logMsg) {
        try {
            FileOutputStream fos = new FileOutputStream("log.txt", true);
            char[] chars = logMsg.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                fos.write(chars[i]);
            }
            fos.write('\n');
            fos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void receiveMessage(String message, SocketThread socketThread) {
        String username = socketThread.getUsername();
        String newMessage = username + ": " + message + '\0';
        logMessage(username + ": " + message);
        for (SocketThread sThread : socketThreads) {
            if (sThread != null && !sThread.equals(socketThread)) {
                try {
                    Socket sThreadSocket = sThread.getSocket();
                    DataOutputStream output = new DataOutputStream(sThreadSocket.getOutputStream());
                    output.writeBytes(newMessage);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
    }
}

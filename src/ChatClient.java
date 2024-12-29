import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    Scanner stdin = new Scanner(System.in);
    Socket conn;

    public ChatClient() {
        try {
            Socket conn = new Socket(InetAddress.getLocalHost(), 4433);
            this.conn = conn;
            System.out.println("Connected.");
            ChatClientReceiver chatClientThread = new ChatClientReceiver(this, conn);
            chatClientThread.start();
            receiveMessages();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void receiveMessages() {
        System.out.println("Send a message.");
        while (true) {
            String message = stdin.nextLine();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void sendMessage(String message) {
        try {
            message += '\n';
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.writeBytes(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
    }
}

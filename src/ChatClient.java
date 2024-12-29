import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ChatClient {
    Scanner stdin = new Scanner(System.in);
    Socket conn;

    public ChatClient() {
        try {
            Socket conn = new Socket(InetAddress.getLocalHost(), 4433);
            this.conn = conn;
            System.out.println("Connected.");
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

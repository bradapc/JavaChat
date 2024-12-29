import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    Scanner stdin = new Scanner(System.in);
    Socket conn;
    String username;

    public ChatClient() {
        try {
            Socket conn = new Socket(InetAddress.getLocalHost(), 4433);
            this.conn = conn;
            System.out.println("Connected.");
            ChatClientReceiver chatClientThread = new ChatClientReceiver(this, conn);
            chatClientThread.start();
            username = createUsername();
            System.out.println("Hello, " + username);
            sendUsername();
            receiveMessages();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String createUsername() {
        String user = "";
        do {
            System.out.print("Enter a username: ");
            user = stdin.next();
            System.out.println();
        } while (user.isEmpty());
        return user;
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
            message += '\0';
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.writeBytes(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendUsername() {
        try {
            byte[] transmission = new byte[username.length() + 2];
            transmission[0] = 42;
            for (int i = 0; i < username.length(); i++) {
                transmission[i + 1] = (byte) username.charAt(i);
            }
            transmission[transmission.length - 1] = '\0';
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.write(transmission);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
    }
}

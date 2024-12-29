import java.net.InetAddress;
import java.net.Socket;

public class ChatClient {
    public ChatClient() {
        try {
            Socket conn = new Socket(InetAddress.getLocalHost(), 4433);
            System.out.println("Connected.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
    }
}

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClientReceiver extends Thread {
    private ChatClient chatClient;
    private Socket socket;

    public ChatClientReceiver(ChatClient chatClient, Socket socket) {
        this.chatClient = chatClient;
        this.socket = socket;
    }

    @Override
    public void run() {
            try {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int byteRead;
                while ((byteRead = input.read()) != -1) {
                    buffer.write(byteRead);

                    if (byteRead == '\n') {
                        String message = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
                        chatClient.printMessage(message);
                        buffer.reset();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
    }
}

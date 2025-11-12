import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {

        ServerSocket mioServerSocket = new ServerSocket(3000);

        do {
            Socket mioSocket = mioServerSocket.accept();
            indovina t = new indovina(mioSocket);
            t.start();
        } while (true);

    }
}
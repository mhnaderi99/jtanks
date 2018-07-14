import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */

public class Server implements Runnable{

    private int port = 2018;

    public Server(int port) {
        this.port = port;
    }

    /**
     * this method opens a connection between server and client and finally closes it
     */
    public void run()  {
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server started.\nWaiting for a client ... ");
            InetAddress iAddress = InetAddress.getLocalHost();
            String server_IP = iAddress.getHostAddress();
            System.out.println(server_IP);
            Socket client = server.accept();
            pool.execute(new ClientHandler(client));
            pool.shutdown();
            System.out.print("done.\nClosing server ... ");
            //server.
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.");

    }
}
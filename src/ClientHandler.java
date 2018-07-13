import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    private Socket client;
    private static OutputStream out;
    private static InputStream in;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {
            out = client.getOutputStream();
            in = client.getInputStream();
            byte[] buffer = new byte[4096];
            while (true) {
                int read = in.read(buffer);
                try {
                    String rcv = new String(buffer, 0, read);
                    processInput(rcv);
                }
                catch (StringIndexOutOfBoundsException e) { continue;}
                //System.out.println(rcv);

            }

        } catch (IOException e) { }
        finally {
            try {
                client.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public Socket getClient() {
        return client;
    }

    public static InputStream getIn() {
        return in;
    }

    public static OutputStream getOut() {
        return out;
    }

    public static void writeOnStream(String s) {
        try {
            out.write(s.getBytes());
        }
        catch (IOException e) { }
        catch (NullPointerException e) { }
    }

    private void processInput(String rcv) {
        if (rcv.equals("CLIENT-SW")) {
            GameLoop.getState().getTank2().switchGun(false);
        }
        if (rcv.startsWith("CLIENT-KB-")) {
            String message = rcv.replaceFirst("CLIENT-KB-", "");
            boolean u = (message.charAt(0) == '1') ? true : false;
            boolean d = (message.charAt(1) == '1') ? true : false;
            boolean l = (message.charAt(2) == '1') ? true : false;
            boolean r = (message.charAt(3) == '1') ? true : false;
            //System.out.println("" + u + d + l + r);
            GameLoop.getState().getTank2().setKeyUP(u);
            GameLoop.getState().getTank2().setKeyDOWN(d);
            GameLoop.getState().getTank2().setKeyLEFT(l);
            GameLoop.getState().getTank2().setKeyRIGHT(r);
            //GameLoop.getState().getTank2().update();
        }
        if (rcv.startsWith("CLIENT-M-")) {
            String message = rcv.replaceFirst("CLIENT-M-", "");
            try {
                double theta = Double.parseDouble(message);
                GameLoop.getState().getTank2().setGunAngle(theta);
            }
            catch (NumberFormatException e) { }
        }
        if (rcv.startsWith("CLIENT-S-")) {
            String message = rcv.replaceFirst("CLIENT-S-", "");
            try {
                double theta = Double.parseDouble(message);
                GameLoop.getState().getTank2().shoot(theta);
            }
            catch (NumberFormatException e) { }
        }
    }
}

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Scanner;

public class Client implements Runnable{

    private String host;
    private int port;
    private static OutputStream out;
    private static InputStream in;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket server = new Socket(host, port)) {
            System.out.println("Connected to server");
            out = server.getOutputStream();
            in = server.getInputStream();
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
        }
        catch (UnknownHostException e) { }
        catch (IOException e) { }
    }


    public static OutputStream getOut() {
        return out;
    }

    public static InputStream getIn() {
        return in;
    }

    public static void writeOnStream(String s) {
        try {
            out.write(s.getBytes());
        }
        catch (IOException e) { }
        catch (NullPointerException e) { }
    }

    private void processInput(String rcv) {
        if (rcv.equals("SERVER-SW")) {
            GameLoop.getState().getTank2().switchGun(false);
        }
        if (rcv.startsWith("SERVER-KB-")) {
            String message = rcv.replaceFirst("SERVER-KB-", "");
            boolean u = (message.charAt(0) == '1') ? true : false;
            boolean d = (message.charAt(1) == '1') ? true : false;
            boolean l = (message.charAt(2) == '1') ? true : false;
            boolean r = (message.charAt(3) == '1') ? true : false;
            GameLoop.getState().getTank2().setKeyUP(u);
            GameLoop.getState().getTank2().setKeyDOWN(d);
            GameLoop.getState().getTank2().setKeyLEFT(l);
            GameLoop.getState().getTank2().setKeyRIGHT(r);
            //GameLoop.getState().getTank2().update();
        }
        if (rcv.startsWith("SERVER-M-")) {
            String message = rcv.replaceFirst("SERVER-M-", "");
            try {
                double theta = Double.parseDouble(message);
                GameLoop.getState().getTank2().setGunAngle(theta);
            }
            catch (NumberFormatException e) { }
        }
        if (rcv.startsWith("SERVER-S-")) {
            String message = rcv.replaceFirst("SERVER-S-", "");
            try {
                double theta = Double.parseDouble(message);
                GameLoop.getState().getTank2().shoot(theta);
            }
            catch (NumberFormatException e) { }
        }
        if (rcv.startsWith("SERVER-ALL-")) {
            String message = rcv.replaceFirst("SERVER-ALL-", "");
            try {
                int commaIndex = message.lastIndexOf(",");
                int x = Integer.parseInt(message.substring(0, commaIndex));
                int y = Integer.parseInt(message.substring(commaIndex + 1, message.length()));
                GameLoop.getState().getTank2().setXPosition(x);
                GameLoop.getState().getTank2().setYPosition(y);
            }
            catch (NumberFormatException e) { }
        }
    }
}

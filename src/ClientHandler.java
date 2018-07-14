import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */

public class ClientHandler implements Runnable{

    private Socket client;
    private static OutputStream out;
    private static InputStream in;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    /**
     * this method receives and sends data for updating the game
     */
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

    /**
     * write outs the string
     * @param s the string to be written
     */
    public static void writeOnStream(String s) {
        try {
            out.write(s.getBytes());
        }
        catch (IOException e) { }
        catch (NullPointerException e) { }
    }

    /**
     * processes the data received
     * @param rcv string that have been received
     */
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
        if (rcv.startsWith("CLIENT-ALL-")) {
            String message = rcv.replaceFirst("CLIENT-ALL-", "");
            try {
                int commaIndex = message.lastIndexOf(",");
                int x = Integer.parseInt(message.substring(0, commaIndex));
                int y = Integer.parseInt(message.substring(commaIndex + 1, message.length()));
                GameLoop.getState().getTank2().setXPosition(x);
                GameLoop.getState().getTank2().setYPosition(y);
            }
            catch (NumberFormatException e) { }
        }
        if (rcv.startsWith("CLIENT-P-")) {
            String message = rcv.replaceFirst("CLIENT-P-", "");
            try {
                String[] array = message.split(",", -1);
                int i = Integer.parseInt(array[0]);
                int j = Integer.parseInt(array[1]);
                int code = Integer.parseInt(array[2]);
                Prize prize= GameConstants.getPrizeByCode(code);
                System.out.println(i + "," + j + ": " + prize);
            }
            catch (NumberFormatException e) { }
        }
    }
}

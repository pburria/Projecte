
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import org.milaifontanals.projecte.LiniaComanda;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cromp
 */
public class Opcio6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, IOException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeInt(6);
        oos.writeInt(1);
        oos.writeInt(1);
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
        int num = ois.readInt();
        System.out.println(num);
        ois.readFloat();
        ois.close();
        oos.close();    }
    
}


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.milaifontanals.projecte.LiniaComanda;

public class Opcio5 {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        ArrayList<LiniaComanda> linies = new ArrayList<>();
        LiniaComanda linia = new LiniaComanda(1, 2, 0, 5, true);
        LiniaComanda linia1 = new LiniaComanda(1, 3, 0,3, false);
        LiniaComanda linia2 = new LiniaComanda(1, 4, 0, 4, true);
        LiniaComanda linia3 = new LiniaComanda(1, 5, 0, 1, false);
        LiniaComanda linia4 = new LiniaComanda(1, 6, 0, 1, false);
        LiniaComanda linia5 = new LiniaComanda(1, 7, 0, 2, true);
        LiniaComanda linia6 = new LiniaComanda(1, 8, 0, 6, true);
        LiniaComanda linia7 = new LiniaComanda(1, 9, 0, 8, true);
        LiniaComanda linia8 = new LiniaComanda(1, 10, 0, 2, false);
        linies.add(linia);
        linies.add(linia1);
        linies.add(linia2);
        linies.add(linia3);
        linies.add(linia4);
        linies.add(linia5);
        linies.add(linia6);
        linies.add(linia7);
        linies.add(linia8);

        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeInt(5);
        oos.writeInt(1);
        oos.writeInt(1);
        oos.writeInt(linies.size());
        oos.writeObject(linies);
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
        int num = ois.readInt();
        System.out.println(num);
        ois.readFloat();
        ois.close();
        oos.close();
    }

}

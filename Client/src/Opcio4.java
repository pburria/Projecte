
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import org.milaifontanals.projecte.Categoria;
import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.Plat;

public class Opcio4 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeInt(4);
        oos.writeInt(1);
        oos.writeInt(1);
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());

        int num = ois.readInt();
        System.out.println(num);
        if (num != -1) {
            ArrayList<LiniaComanda> linies = new ArrayList<>();
            linies = (ArrayList<LiniaComanda>) ois.readObject();
            for (int i = 0; i < linies.size(); i++) {
                System.out.println(linies.get(i));
            }
        } else {
            System.out.println("NO FUNCIONA");
        }
        ois.readFloat();
        ois.close();
        oos.close();
    }

}

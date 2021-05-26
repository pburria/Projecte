
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import org.milaifontanals.projecte.Categoria;
import org.milaifontanals.projecte.InfoTaula;
import org.milaifontanals.projecte.Plat;

public class Opcio3 {

    public static void main(String[] args) throws IOException, IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeInt(3);
        oos.writeInt(1);
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());

        int num = ois.readInt();
        System.out.println(num);
        if (num != -1) {
            ArrayList<Categoria> categories = new ArrayList<>();
            categories = (ArrayList<Categoria>) ois.readObject();
            int num2 = ois.readInt();
            ArrayList<Plat> plats = new ArrayList<>();
            plats = (ArrayList<Plat>) ois.readObject();
            for (int i = 0; i < categories.size(); i++) {
                System.out.println(categories.get(i));
            }
            System.out.println(num2);
            for (int i = 0; i < plats.size(); i++) {
                System.out.println(plats.get(i));
            }
        } else {
            System.out.println("NO FUNCIONA");
        }
        ois.readFloat();
        ois.close();
        oos.close();
    }

}

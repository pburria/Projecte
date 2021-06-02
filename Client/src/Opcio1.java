
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import org.milaifontanals.projecte.Cambrer;
import org.milaifontanals.projecte.InfoTaula;

public class Opcio1 {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();

        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);
        
        oos = new ObjectOutputStream(socket.getOutputStream());
        
        oos.writeInt(1);
        
        ArrayList<String> dadesConexio = new ArrayList<String>();
        dadesConexio.add("plopez");
        dadesConexio.add("plopez");

        oos.writeObject(dadesConexio);
        
        ois = new ObjectInputStream(socket.getInputStream());
        int session = ois.readInt();
        System.out.println(session);
        if (session != -1) {
            Cambrer c =  (Cambrer) ois.readObject();
            System.out.println(c);
        } else {
            System.out.println("No s'ha trobat el usuari");
        }
        oos.flush();
        ois.close();
        oos.close();
        socket.close();
    }

}

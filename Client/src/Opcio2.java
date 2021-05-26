
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import org.milaifontanals.projecte.InfoTaula;

public class Opcio2 {

    public static void main(String[] args) throws IOException, IOException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeInt(2);
        oos.writeInt(1);
        
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
        
        int num = ois.readInt();
        System.out.println(num);
        if (num != -1) {
            ArrayList<InfoTaula> taules = new ArrayList<>();
            taules = (ArrayList<InfoTaula>) ois.readObject();
            for (int i = 0; i < taules.size(); i++) {
                System.out.println(taules.get(i));
            }
        }else{
            System.out.println("NO FUNCIONA");
        }
        ois.readFloat();
        ois.close();
        oos.close();
    }

}

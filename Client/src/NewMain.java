
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class NewMain {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        socket = new Socket(host.getHostName(), 9876);

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("" + "1");

        ArrayList<String> dadesConexio = new ArrayList<String>();
        dadesConexio.add("plopez");
        dadesConexio.add("plopez");
        
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(dadesConexio);
        
        ois = new ObjectInputStream(socket.getInputStream());
        int session= (int) ois.readObject();
        if(session!=-1){
            int codi= (int) ois.readObject();
            String nom=(String) ois.readObject();
            String cognom=(String) ois.readObject();
            String cognom1=(String) ois.readObject();
            String user=(String) ois.readObject();
            String password=(String) ois.readObject();
            Cambrer c;
            if(cognom1!=null){
                c=new Cambrer(codi, nom, cognom,cognom1, user, password);
            }else{
                c=new Cambrer(codi, nom, cognom, user, password);
            }
            System.out.println(session);
            System.out.println(c);

        }
        
        
        
        ois.close();
        oos.close();
    }

}

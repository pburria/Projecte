package org.milaifontanals.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.milaifontanals.projecte.Cambrer;
import org.milaifontanals.projecte.Login;

public class Server {

    private Connection con;
    private JFrame f;
    private JTextArea log;
    private JButton start, stop;
    private JScrollPane scroll;
    private boolean stopServer = false;
    private Servidor s;
    private ServerSocket serverSocket;
    private int port = 9876;
    private int sesionId = 0;
    private ArrayList<Integer> sessions;

    public static void main(String[] args) {
        try {
            Server ser = new Server();
            ser.crearConexio();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void crearConexio() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            log.setText(log.getText() + "ERROR" + ex + "\n");
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant", "root", "");
            con.setAutoCommit(false);
            System.out.println("CONEXIÓ CREADA");
            crearGUI();

        } catch (Exception e) {
            log.setText(log.getText() + "ERROR" + e + "\n");
        }
    }

    private void crearGUI() {
        f = new JFrame("Server");

        JPanel area = new JPanel();
        log = new JTextArea(200, 200);
        log.setEditable(false);
        area.add(log);

        scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel botons = new JPanel();
        botons.setLayout(new BoxLayout(botons, BoxLayout.X_AXIS));
        start = new JButton("START");
        start.addActionListener(new Start());
        stop = new JButton("STOP");
        stop.addActionListener(new Stop());

        botons.add(start);
        botons.add(stop);

        f.add(scroll, BorderLayout.CENTER);
        f.add(botons, BorderLayout.SOUTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(550, 250);
        f.setResizable(false);
        f.setVisible(true);
    }

    class Start implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            log.setText(log.getText() + " Servidor Engegat\n");
            stopServer = false;
            s = new Servidor();
            s.start();
        }
    }

    class Stop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            log.setText(log.getText() + " Servidor Aturat\n");
            stopServer = true;
            s.stop();
        }
    }

    class Servidor extends Thread {

        public void run() {
            while (!stopServer) {
                try {
                    Socket socket = serverSocket.accept();
                    ObjectInputStream codi = new ObjectInputStream(socket.getInputStream());
                    String opcio = (String) codi.readObject();
                    log.setText(log.getText() + "Opcio rebuda: " + opcio + "\n");
                    if (opcio.equals("1")) {
                        login(socket);

                    } else if (opcio.equals("2")) {
                        getTaules(socket);

                    } else if (opcio.equals("3")) {

                    } else if (opcio.equals("4")) {

                    } else if (opcio.equals("5")) {

                    } else if (opcio.equals("6")) {

                    } else {
                        //oos = new ObjectOutputStream(socket.getOutputStream());
                        //oos.writeObject("ERROR, inserir numero de la opcio (1-6)");

                    }
                    codi.close();
                    //oos.close();
                    socket.close();
                } catch (IOException ex) {
                    log.setText(log.getText() + "ERROR" + ex + "\n");
                } catch (ClassNotFoundException ex) {
                    log.setText(log.getText() + "ERROR" + ex + "\n");
                } catch (SQLException ex) {
                    log.setText(log.getText() + "ERROR" + ex + "\n");
                }
            }
        }

        private void login(Socket socket) throws IOException, ClassNotFoundException, SQLException {
            ArrayList<String> dadesConnect = new ArrayList<String>();
            ObjectInputStream dadesUser = new ObjectInputStream(socket.getInputStream());
            dadesConnect = (ArrayList<String>) dadesUser.readObject();
            String user = (String) dadesConnect.get(0);
            String password = (String) dadesConnect.get(1);
            Cambrer cambrer = existeixUser(user, password);
            log.setText(log.getText() + "User: " + user + "\n");
            log.setText(log.getText() + "Password: " + password + "\n");
            ObjectOutputStream respostaLoginUser = null;
            respostaLoginUser = new ObjectOutputStream(socket.getOutputStream());
            if (cambrer != null) {
                log.setText(log.getText() + "Usuari correcte: " + "\n");
                respostaLoginUser.writeObject(sesionId + 1);
                sesionId++;
                sessions.add(sesionId);
                respostaLoginUser.writeObject(cambrer.getCodi());
                respostaLoginUser.writeObject(cambrer.getNom());
                respostaLoginUser.writeObject(cambrer.getCognom1());
                respostaLoginUser.writeObject(cambrer.getCognom2());
                respostaLoginUser.writeObject(cambrer.getUser());
                respostaLoginUser.writeObject(cambrer.getPassword());
            } else {
                log.setText(log.getText() + "Usuari incorrecte: " + "\n");
                respostaLoginUser.writeObject(-1);
            }
        }

        private void getTaules(Socket socket) throws IOException, ClassNotFoundException {
            ObjectInputStream dadesSesio = new ObjectInputStream(socket.getInputStream());
            int sessio=(int) dadesSesio.readObject();
            if(sessions.contains(sessio)){
                
            }
            

        }

        private Cambrer existeixUser(String user, String password) throws SQLException {
            Cambrer c;
            ResultSet set;
            String consulta = "select * from cambrer "
                    + "where user=? and password=?";
            PreparedStatement psLin = con.prepareStatement(consulta);
            psLin.setString(1, user);
            psLin.setString(2, password);
            set = psLin.executeQuery();
            if (set.next() == false) {
                return null;
            } else {
                int codi = set.getInt("codi");
                String nom = set.getString("nom");
                String cognom1 = set.getString("cognom1");
                String cognom2 = set.getString("cognom2");
                String userr = set.getString("user");
                String pass = set.getString("password");
                if (cognom2 != null) {
                    c = new Cambrer(codi, nom, cognom1, cognom2, userr, pass);
                } else {
                    c = new Cambrer(codi, nom, cognom1, userr, pass);
                }
                return c;
            }
        }
    }

}

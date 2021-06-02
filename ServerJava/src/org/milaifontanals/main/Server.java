package org.milaifontanals.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.milaifontanals.projecte.Cambrer;
import org.milaifontanals.projecte.Categoria;
import org.milaifontanals.projecte.InfoTaula;
import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.Plat;

public class Server {

    private Connection con;
    private String url, user, password;
    private JFrame f;
    private JTextArea log;
    private JButton start, stop;
    private JScrollPane scroll;
    private boolean stopServer = false;
    private Servidor s;
    private ServerSocket serverSocket;
    private int port = 9876;
    private HashMap<Integer, Cambrer> sessions;

    public static void main(String[] args) {
        try {
            Server ser = new Server();
            ser.crearConexio();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void crearConexio() {
        llegirPropietats();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            log.setText(log.getText() + "ERROR" + ex + "\n");
        }
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            System.out.println("CONEXIÓ CREADA");
            crearGUI();

        } catch (Exception e) {
            log.setText(log.getText() + "ERROR" + e + "\n");
        }
    }

    private void llegirPropietats() {
        Properties p = new Properties();
        try {
            p.load(new FileReader("Conexio.properties"));
        } catch (IOException ex) {
            log.setText(log.getText() + "ERROR" + ex + "\n");
            System.exit(1);
        }
        url = p.getProperty("url");
        user = p.getProperty("usuari");
        password = p.getProperty("contrasenya");
        if (url == null || user == null || password == null) {
            log.setText(log.getText() + "Falta alguna propietat" + "\n");
            System.exit(1);
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

        sessions = new HashMap<>();
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
            try {
                con.close();
            } catch (SQLException ex) {
                log.setText(log.getText() + "ERROR" + ex + "\n");
            }
        }
    }

    class Servidor extends Thread {

        public void run() {
            try {
                while (!stopServer) {
                    Socket socket = serverSocket.accept();

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                servirPeticio(socket);
                            } catch (SQLException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    );
                    t.start();
                }
            } catch (Exception e) {
                log.setText(log.getText() + "ERROR " + e + "\n");

            }

        }
    }

    public void servirPeticio(Socket socket) throws SQLException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            int opcio = ois.readInt();
            log.setText(log.getText() + "Opcio rebuda: " + opcio + "\n");

            if (opcio == 1) {
                login(oos, ois);
            } else if (opcio == 2) {
                getTaules(oos, ois);
            } else if (opcio == 3) {
                getCarta(oos, ois);
            } else if (opcio == 4) {
                getComanda(oos, ois);
            } else if (opcio == 5) {
                createComanda(oos, ois);
            } else if (opcio == 6) {
                buidarTaula(oos, ois);
            }

        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private void login(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException, SQLException {

        ArrayList<String> dadesConnect = new ArrayList<String>();
        dadesConnect = (ArrayList<String>) ois.readObject();

        String user = (String) dadesConnect.get(0);
        String password = (String) dadesConnect.get(1);

        log.setText(log.getText() + "User: " + user + "\n");
        log.setText(log.getText() + "Password: " + password + "\n");

        Cambrer cambrer = existeixUser(user, password);

        if (cambrer != null) {

            int sessio = numeroAleatoriPerSesio(cambrer);
            log.setText(log.getText() + "Usuari correcte " + sessio + "\n");
            oos.writeInt(sessio);
            oos.writeObject(cambrer);
        } else {
            log.setText(log.getText() + "Usuari incorrecte " + "\n");
            oos.writeInt(-1);
        }
    }

    private void getTaules(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException, SQLException {
        int sessio = ois.readInt();
        log.setText(log.getText() + "Sessio rebuda: " + sessio + "\n");

        if (sessions.containsKey(sessio)) {
            ArrayList<InfoTaula> taules = new ArrayList<>();
            taules = getTotesTaules(sessions.get(sessio));
            log.setText(log.getText() + "Taules: " + taules + "\n");
            System.out.println(taules);
            if (taules != null) {
                oos.writeInt(taules.size());
                oos.writeObject(taules);
                log.setText(log.getText() + "Taules Enviades" + "\n");
            } else {
                oos.writeInt(-1);
            }
        }
    }

    private void getCarta(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, SQLException {
        int sessio = ois.readInt();
        if (sessions.containsKey(sessio)) {
            ArrayList<Categoria> categories = new ArrayList<>();
            ArrayList<Plat> plats = new ArrayList<>();
            categories = getCategories();
            plats = getPlats();
            if (categories != null && plats != null) {
                oos.writeInt(categories.size());
                oos.writeObject(categories);
                oos.writeInt(plats.size());
                oos.writeObject(plats);
                log.setText(log.getText() + "Cartas enviades" + "\n");
            } else {
                oos.writeInt(-1);
            }
        }
    }

    private void getComanda(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, SQLException {
        int sessio = ois.readInt();
        if (sessions.containsKey(sessio)) {
            int codiComanda = ois.readInt();
            ArrayList<LiniaComanda> liniasComanda = new ArrayList<>();
            liniasComanda = getLiniesComanda(codiComanda);
            if (liniasComanda != null) {
                oos.writeInt(liniasComanda.size());
                oos.writeObject(liniasComanda);
                log.setText(log.getText() + "Comanda enviada" + "\n");
            } else {
                oos.writeInt(-1);
            }
        }

    }

    private void createComanda(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException, SQLException {
        int sessio = ois.readInt();
        if (sessions.containsKey(sessio)) {
            int codiComanda = getUltimaComanda() + 1;
            int taula = ois.readInt();
            int numLiniaComanda = ois.readInt();
            ArrayList<LiniaComanda> liniesComanda = new ArrayList<>();
            liniesComanda = (ArrayList<LiniaComanda>) ois.readObject();
            Cambrer c = new Cambrer(1, "Pere", "Lopez", "Garcia", "plopez", "plopez");
            int a = insertComanda(taula, c, codiComanda);
            int b = insertLiniaComanda(liniesComanda, codiComanda);
            if (b > 0 && a == 1) {
                oos.writeInt(codiComanda);
                log.setText(log.getText() + "Comanda crada correctament" + "\n");
                con.commit();
            } else {
                oos.writeInt(-1);
            }
        }
    }

    private void buidarTaula(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, SQLException {
        int sessio = ois.readInt();
        if (sessions.containsKey(sessio)) {
            int taula = ois.readInt();
            int comanda = getComandaDeLaTula(taula);
            int plats = posarTotsElsPlatsAcabat(comanda);
            if (plats > 0) {
                oos.writeInt(0);
                con.commit();
                log.setText(log.getText() + "Taula buidada correctament" + "\n");

            } else {
                oos.writeInt(-1);
            }
        }
    }

    private int numeroAleatoriPerSesio(Cambrer c) {
        int range = 100000 - 1 + 1;
        int aleatori = 0;
        do {
            aleatori = (int) (Math.random() * range) + 1;
        } while (sessions.containsKey(aleatori));
        sessions.put(aleatori, c);
        return aleatori;
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

    private ArrayList<InfoTaula> getTotesTaules(Cambrer c) throws SQLException {
        String consulta = "select co.codi as codi ,cambrer,(select count(lii.plat) from linia_comanda lii\n"
                + " join comanda coo on \n"
                + "lii.COMANDA=coo.codi where coo.taula=?) as plats_totals,\n"
                + " count(li.PLAT) as plats_preparats,ca.nom as nom_cambrer\n"
                + "from comanda co join linia_comanda li on co.codi=li.comanda join\n"
                + "cambrer ca on co.cambrer=ca.codi \n"
                + "where co.taula=? and li.ACABAT=true\n"
                + "group by co.codi";
        PreparedStatement psLin = con.prepareStatement(consulta);
        ArrayList<InfoTaula> info = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet taules = st.executeQuery("select * from taula");
        ResultSet infoTaula;
        while (taules.next()) {
            int codiTaula = taules.getInt("numero");
            psLin.setInt(1, codiTaula);
            psLin.setInt(2, codiTaula);
            infoTaula = psLin.executeQuery();
            if (infoTaula.next()) {
                boolean es_meva;
                int codi = infoTaula.getInt("codi");
                int codiCambrer = infoTaula.getInt("cambrer");
                int platsTotals = infoTaula.getInt("plats_totals");
                int platsPreparats = infoTaula.getInt("plats_preparats");
                String nomCambrer = infoTaula.getString("nom_cambrer");
                if (codiCambrer == c.getCodi()) {
                    es_meva = true;
                } else {
                    es_meva = false;
                }
                if (platsPreparats == platsTotals) {
                    codi = -1;
                }
                InfoTaula inf = new InfoTaula(codiTaula, codi, es_meva, platsTotals, platsPreparats, nomCambrer);
                info.add(inf);
            } else {
                InfoTaula inf = new InfoTaula(codiTaula, -1, false, 0, 0, null);
                info.add(inf);
            }

        }
        return info;
    }

    private ArrayList<Categoria> getCategories() throws SQLException {
        ArrayList<Categoria> cate = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet categories = st.executeQuery("select * from categoria");
        while (categories.next()) {
            int codi = categories.getInt("codi");
            String nom = categories.getString("nom");
            Categoria c = new Categoria(codi, nom);
            cate.add(c);
        }
        return cate;
    }

    private ArrayList<Plat> getPlats() throws SQLException {
        ArrayList<Plat> platss = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet plats = st.executeQuery("select * from plat");
        while (plats.next()) {
            int codi = plats.getInt("codi");
            String nom = plats.getString("nom");
            BigDecimal preu = plats.getBigDecimal("preu");
            ByteArrayInputStream foto = (ByteArrayInputStream) plats.getBinaryStream("foto");
            int tamany = getTamanyFoto(codi);
            byte[] fotoBytes = new byte[tamany];
            foto.read(fotoBytes, 0, tamany);
            Plat p = new Plat(codi, nom, preu, tamany, fotoBytes);
            platss.add(p);
        }
        return platss;
    }

    private int getTamanyFoto(int codi) throws SQLException {
        ResultSet set;
        String consulta = "SELECT OCTET_LENGTH(foto) FROM plat where CODI=" + codi;
        PreparedStatement psLin = con.prepareStatement(consulta);
        set = psLin.executeQuery(consulta);
        set.next();
        return (int) set.getInt(1);
    }

    private ArrayList<LiniaComanda> getLiniesComanda(int codiComanda) throws SQLException {
        ArrayList<LiniaComanda> linies = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet linia = st.executeQuery("select num,quantitat,plat from linia_comanda where comanda=" + codiComanda);
        while (linia.next()) {
            int codi = linia.getInt("num");
            int qtat = linia.getInt("quantitat");
            int plat = linia.getInt("plat");
            LiniaComanda c = new LiniaComanda(codi, qtat, plat);
            linies.add(c);
        }
        return linies;
    }

    private int insertComanda(int taula, Cambrer c, int ultimaComanda) throws SQLException {
        java.sql.Date data = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        long time = data.getTime();
        Timestamp dataaa = new Timestamp(time);
        java.sql.Date novaDate = new java.sql.Date(dataaa.getTime());

        String consulta = "insert into comanda values(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(consulta);
        ps.setInt(1, ultimaComanda);
        ps.setDate(2, novaDate);
        ps.setInt(3, taula);
        ps.setInt(4, c.getCodi());
        int comanda = ps.executeUpdate();
        return comanda;
    }

    private int getUltimaComanda() throws SQLException {
        Statement st = con.createStatement();
        ResultSet comanda = st.executeQuery("select max(codi) from comanda");
        comanda.next();
        return (int) comanda.getInt(1);
    }

    private int insertLiniaComanda(ArrayList<LiniaComanda> linies, int ultimaComanda) throws SQLException {
        int comanda = 0;
        String consulta = "insert into linia_comanda values(?,?,?,?,?)";
        PreparedStatement ps1 = con.prepareStatement(consulta);
        for (int i = 0; i < linies.size(); i++) {
            ps1.setInt(1, ultimaComanda);
            ps1.setInt(2, linies.get(i).getPlat());
            ps1.setInt(3, i + 1);
            ps1.setInt(4, linies.get(i).getQtat());
            ps1.setBoolean(5, linies.get(i).getAcabat());
            comanda += ps1.executeUpdate();
        }

        return comanda;
    }

    private int getComandaDeLaTula(int taula) throws SQLException {
        ResultSet set;
        String consulta = "select codi from comanda where taula=" + taula;
        PreparedStatement ps2 = con.prepareStatement(consulta);
        set = ps2.executeQuery(consulta);
        set.next();
        return (int) set.getInt(1);
    }

    private int posarTotsElsPlatsAcabat(int comanda) throws SQLException {
        String consulta = "update linia_comanda set acabat=true\n"
                + "where comanda=" + comanda;

        PreparedStatement ps3 = con.prepareStatement(consulta);
        return ps3.executeUpdate(consulta);
    }

}

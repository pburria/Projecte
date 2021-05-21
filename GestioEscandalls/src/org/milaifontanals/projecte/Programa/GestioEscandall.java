package org.milaifontanals.projecte.Programa;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javafx.scene.layout.Border;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.milaifontanals.projecte.Cambrer;
import org.milaifontanals.projecte.Categoria;
import org.milaifontanals.projecte.Comanda;
import org.milaifontanals.projecte.Ingredient;
import org.milaifontanals.projecte.LiniaComanda;
import org.milaifontanals.projecte.LiniaComandaId;
import org.milaifontanals.projecte.LiniaEscandall;
import org.milaifontanals.projecte.LiniaEscandallId;
import org.milaifontanals.projecte.Plat;
import org.milaifontanals.projecte.Taula;
import org.milaifontanals.projecte.Unitat;

public class GestioEscandall extends JFrame {

    private String host;
    private String user;
    private String pass;
    private String driver;
    private String dialect;
    private String up;
    private JComboBox categories;
    private JFrame f;
    private JButton cercar;
    private JRadioButton si, no, totes;
    private JPanel centre, esquerra;
    private List<Categoria> listCategories;
    private List<Plat> llistaPlats;
    private ButtonGroup grupRadios;
    private JList<Plat> llistaPlatsInterficie;
    private DefaultListModel modelLlista;
    private JScrollPane scroll;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public static void main(String[] args) {
        GestioEscandall x = null;
        try {
            x = new GestioEscandall();
            x.engegarEntityManager();
        } catch (IOException ex) {
            throw new RuntimeException("Error" + ex);
        }
    }

    private void engegarEntityManager() throws IOException {
        obtenirPropietats();
        try {
            em = null;
            emf = null;
            if (up == null || host == null || user == null || user == null || pass == null || driver == null || dialect == null) {
                throw new RuntimeException("Error en carregar les propietats necesaries EntityManager");
            }
            System.out.println("Intent amb " + up);
            HashMap<String, String> propietats = new HashMap();
            propietats.put("javax.persistence.jdbc.url", host);
            propietats.put("javax.persistence.jdbc.user", user);
            propietats.put("javax.persistence.jdbc.password", pass);
            propietats.put("javax.persistence.jdbc.driver", driver);
            propietats.put("hibernate.dialect", dialect);
            emf = Persistence.createEntityManagerFactory(up, propietats);
            System.out.println("EntityManagerFactory creada");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            System.out.println("EntityManager creat");
            omplirCombo();
            mostrarGUI();
            omplirLlistaPlats(null, null);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        }
    }

    private void obtenirPropietats() throws IOException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("JPA.properties"));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("No es troba fitxer de propietats", ex);
        } catch (IOException ex) {
            throw new RuntimeException("Error en carregar fitxer de propietats", ex);
        }
        host = props.getProperty("host");
        user = props.getProperty("user");
        pass = props.getProperty("password");
        driver = props.getProperty("driver");
        dialect = props.getProperty("dialect");
        up = props.getProperty("up");
    }

    private void omplirCombo() {
        String consultaCategories = "select ca from Categoria ca";
        Query cat = em.createQuery(consultaCategories);
        listCategories = cat.getResultList();
    }

    private void omplirLlistaPlats(Boolean disponible, String categoria) {
        String consultaPlats = "";
        Query plats;
        if (disponible != null && categoria != null) {
            consultaPlats = "select p.codi,p.nom,p.descripcio_md,p.preu,p.foto,p.disponible,c.nom as nomcat,c.color\n"
                    + "from plat p join categoria c on p.categoria=c.codi\n"
                    + "where p.disponible=:disponible and c.nom like :categoria";
            plats = em.createNativeQuery(consultaPlats);
            plats.setParameter("disponible", disponible);
            plats.setParameter("categoria", categoria);

        } else if (disponible != null && categoria == null) {
            System.out.println(disponible);
            consultaPlats = "select p.codi,p.nom,p.descripcio_md,p.preu,p.foto,p.disponible,c.nom as nomcat,c.color\n"
                    + "from plat p join categoria c on p.categoria=c.codi\n"
                    + "where p.disponible=:disponible";
            plats = em.createNativeQuery(consultaPlats);
            plats.setParameter("disponible", disponible);

        } else if (disponible == null && categoria != null) {
            consultaPlats = "select p.codi,p.nom,p.descripcio_md,p.preu,p.foto,p.disponible,c.nom as nomcat,c.color\n"
                    + "from plat p join categoria c on p.categoria=c.codi\n"
                    + "where c.nom like:categoria ";
            plats = em.createNativeQuery(consultaPlats);
            plats.setParameter("categoria", categoria);

        } else {
            consultaPlats = "select p.codi,p.nom,p.descripcio_md,p.preu,p.foto,p.disponible,c.nom as nomcat,c.color\n"
                    + "from plat p join categoria c on p.categoria=c.codi";
            plats = em.createNativeQuery(consultaPlats);
        }

        List<Object[]> p = plats.getResultList();
        llistaPlats = new ArrayList<Plat>();
        for (Object[] t : p) {
            Plat plat = new Plat((int) t[0], "" + t[1], "" + t[2], (BigDecimal) t[3], (byte[]) t[4], (Boolean) t[5],
                    "" + t[6], "" + t[7]);
            llistaPlats.add(plat);
        }
        ompliLlistaGUI();

    }

    private void mostrarGUI() {
        f = new JFrame("Gestió d''escandalls");
        JLabel txtCategories = new JLabel("Categories: ");
        JLabel txtDisponible = new JLabel("Disponibilitat: ");

        esquerra = new JPanel();
        esquerra.setLayout(new GridLayout(3, 1));
        if (listCategories != null) {
            Categoria arrayCategoria[] = listCategories.toArray(new Categoria[0]);
            categories = new JComboBox(arrayCategoria);
            categories.setSelectedIndex(-1);
        }
        JPanel combo = new JPanel();
        combo.add(txtCategories);
        combo.add(categories);
        JPanel comboEsquerra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboEsquerra.add(combo);
        esquerra.add(comboEsquerra);

        JPanel radios = new JPanel();
        radios.setLayout(new BoxLayout(radios, BoxLayout.X_AXIS));
        si = new JRadioButton("Si");
        no = new JRadioButton("No");
        totes = new JRadioButton("Totes");
        grupRadios = new ButtonGroup();
        grupRadios.add(si);
        grupRadios.add(no);
        grupRadios.add(totes);
        totes.setSelected(true);
        radios.add(txtDisponible);
        radios.add(si);
        radios.add(no);
        radios.add(totes);

        esquerra.add(radios);
        cercar = new JButton("Cercar");
        cercar.addActionListener(new GestioBoto());

        JPanel boto = new JPanel();
        boto.add(cercar);
        esquerra.add(boto);
        JLabel titol = new JLabel("GESTIÓ D'ESCANDALL", JLabel.CENTER);
        titol.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        llistaPlatsInterficie = new JList<>();
        llistaPlatsInterficie.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int index = llistaPlatsInterficie.getSelectedIndex();
                if (index != -1) {
                    Plat p = llistaPlatsInterficie.getSelectedValue();

                }
            }
        });
        ompliLlistaGUI();
        scroll = new JScrollPane(llistaPlatsInterficie);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                tancarApp();
            }
        });
        f.add(scroll, BorderLayout.CENTER);
        f.add(esquerra, BorderLayout.WEST);
        f.add(titol, BorderLayout.NORTH);
        f.setSize(550, 250);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);
    }

    private void ompliLlistaGUI() {
        if (llistaPlats != null) {
            modelLlista = new DefaultListModel();
            for (int i = 0; i < llistaPlats.size(); i++) {
                modelLlista.add(i, llistaPlats.get(i));
            }
            llistaPlatsInterficie.setModel(modelLlista);
        }
    }

    class GestioBoto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Boolean disponible = null;
            String cate = null;
            if (e.getSource().equals(cercar)) {
                if (si.isSelected()) {
                    disponible = true;
                } else if (no.isSelected()) {
                    disponible = false;
                }
                int seleccionat = 0;
                seleccionat = categories.getSelectedIndex();
                if (seleccionat != -1) {
                    cate = categories.getSelectedItem().toString();
                }
                omplirLlistaPlats(disponible, cate);
            }
        }
    }

    private void tancarApp() {
        if (em != null) {
            em.close();
            System.out.println("EntityManager tancat");
        }
        if (emf != null) {
            emf.close();
            System.out.println("EntityManagerFactory tancada");
        }
        f.dispose();
        System.exit(0);
    }
}

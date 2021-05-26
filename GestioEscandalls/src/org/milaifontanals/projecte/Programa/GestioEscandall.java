package org.milaifontanals.projecte.Programa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.milaifontanals.projecte.Categoria;
import org.milaifontanals.projecte.Ingredient;
import org.milaifontanals.projecte.LiniaEscandall;
import org.milaifontanals.projecte.Plat;
import org.milaifontanals.projecte.Unitat;

public class GestioEscandall extends JFrame {

    private String host, user, pass, driver, dialect, up;
    private JComboBox cmbCategories, cmbUnitats, cmbIngredients;
    private JFrame f, f2;
    private JButton btnCercar, btnInsert, btnDelete;
    private JRadioButton si, no, totes;
    private JPanel centre, esquerra, caracPlat, escandall, sotaGUI2;
    private List<Categoria> listCategories;
    private List<LiniaEscandall> ListllistaEscandall;
    private List<Plat> llistaPlats;
    private ButtonGroup grupRadios;
    private JList<Plat> llistaPlatsInterficie;
    private DefaultListModel modelLlista, modelLlistaEscandall;
    private JScrollPane scroll, scrollLiniaEscandall;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private JList<LiniaEscandall> llistaEscandall;
    private List<Unitat> llistaUnitats;
    private List<Ingredient> llistaIngredients;
    private JTextField qtatTxt;
    private Plat platSeleccionat;

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
            OmplirComboCategories();
            OmplirComboIngredients();
            OmplirComboUnitats();
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

    private void OmplirComboCategories() {
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
        JLabel titol = new JLabel("GESTIÓ D'ESCANDALL", JLabel.CENTER);
        titol.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        partEsquerraGUI();
        partCentreGUI();
        f.add(centre, BorderLayout.CENTER);
        f.add(esquerra, BorderLayout.WEST);
        f.add(titol, BorderLayout.NORTH);
        f.setSize(550, 250);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);
    }

    private void partEsquerraGUI() {
        JLabel txtCategories = new JLabel("Categories: ");
        JLabel txtDisponible = new JLabel("Disponibilitat: ");

        JLabel titolEsquerra = new JLabel("FILTRE&CERCA", JLabel.CENTER);
        titolEsquerra.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

        esquerra = new JPanel();
        esquerra.setLayout(new GridLayout(5, 1));
        esquerra.add(titolEsquerra);

        if (listCategories != null) {
            Categoria arrayCategoria[] = listCategories.toArray(new Categoria[0]);
            cmbCategories = new JComboBox(arrayCategoria);
            cmbCategories.setSelectedIndex(-1);
        }

        JPanel combo = new JPanel();
        combo.add(txtCategories);
        combo.add(cmbCategories);
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
        btnCercar = new JButton("Cercar");
        btnCercar.addActionListener(new GestioSelect());

        JPanel boto = new JPanel();
        boto.add(btnCercar);
        esquerra.add(boto);
    }

    private void partCentreGUI() {
        llistaPlatsInterficie = new JList<>();
        llistaPlatsInterficie.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int index = llistaPlatsInterficie.getSelectedIndex();
                if (index != -1) {
                    Plat p = llistaPlatsInterficie.getSelectedValue();
                    mostrarGUI2(p);
                }
            }
        });
        llistaPlatsInterficie.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Plat p = (Plat) value;
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Color color = saberColorCatagoria(p);
                setBackground(color);
                return c;
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

        JLabel titolCentre = new JLabel("LLISTA PLATS", JLabel.CENTER);
        titolCentre.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.add(titolCentre);
        centre.add(scroll);
        centre.setBorder(BorderFactory.createEmptyBorder(7, 10, 0, 0));
    }

    private Color saberColorCatagoria(Plat p) {
        String colorStr = p.getColor();
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
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

    class GestioSelect implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Boolean disponible = null;
            String cate = null;
            if (e.getSource().equals(btnCercar)) {
                if (si.isSelected()) {
                    disponible = true;
                } else if (no.isSelected()) {
                    disponible = false;
                }
                int seleccionat = 0;
                seleccionat = cmbCategories.getSelectedIndex();
                if (seleccionat != -1) {
                    cate = cmbCategories.getSelectedItem().toString();
                }
                omplirLlistaPlats(disponible, cate);
            }
        }
    }

    private void mostrarGUI2(Plat p) {
        JLabel titol = new JLabel("EDICIÓ DE L'ESCANDALL", JLabel.CENTER);
        titol.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        platSeleccionat = p;
        partEsquerraGUI2(p);
        partCentreGUI2(p);
        partAbaixGUI2(p);
        f2 = new JFrame("Gestió d''escandalls");
        f2.add(caracPlat, BorderLayout.WEST);
        f2.add(titol, BorderLayout.NORTH);
        f2.add(escandall, BorderLayout.EAST);
        f2.add(sotaGUI2, BorderLayout.SOUTH);
        f2.setSize(650, 350);
        f2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                tancarSubFinestra();
            }
        });
        f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f2.setResizable(false);
        f2.setVisible(true);

    }

    private void partEsquerraGUI2(Plat p) {
        caracPlat = new JPanel();
        caracPlat.setLayout(new GridLayout(6, 1));

        JLabel titolPlat = new JLabel("PLAT");
        titolPlat.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

        JLabel nom = new JLabel("Nom: ");
        JLabel nomPlat = new JLabel(p.getNom());
        JPanel nomPlatt = new JPanel();
        nomPlatt.setLayout(new BoxLayout(nomPlatt, BoxLayout.X_AXIS));
        nomPlatt.add(nom);
        nomPlatt.add(nomPlat);

        JLabel desc = new JLabel("Desc: ");
        JLabel descPlat = new JLabel(p.getDESCRIPCIO_MD());
        JPanel descPlatt = new JPanel();
        descPlatt.setLayout(new BoxLayout(descPlatt, BoxLayout.X_AXIS));
        descPlatt.add(desc);
        descPlatt.add(descPlat);

        JLabel preu = new JLabel("Preu: ");
        JLabel preuPlat = new JLabel("" + p.getPreu() + "€");
        JPanel preuPlatt = new JPanel();
        preuPlatt.setLayout(new BoxLayout(preuPlatt, BoxLayout.X_AXIS));
        preuPlatt.add(preu);
        preuPlatt.add(preuPlat);

        JLabel dis = new JLabel("Disponibilitat: ");
        JLabel disPlat = new JLabel((p.getDisponible() == true ? "Disponible" : "No Disponible"));
        JPanel disPlatt = new JPanel();
        disPlatt.setLayout(new BoxLayout(disPlatt, BoxLayout.X_AXIS));
        disPlatt.add(dis);
        disPlatt.add(disPlat);

        JLabel cat = new JLabel("Categoria: ");
        JLabel catPlat = new JLabel(p.getNomCat());
        JPanel catPlatt = new JPanel();
        catPlatt.setLayout(new BoxLayout(catPlatt, BoxLayout.X_AXIS));
        catPlatt.add(cat);
        catPlatt.add(catPlat);

        caracPlat.add(titolPlat);
        caracPlat.add(nomPlatt);
        caracPlat.add(descPlatt);
        caracPlat.add(preuPlatt);
        caracPlat.add(disPlatt);
        caracPlat.add(catPlatt);
    }

    private void partCentreGUI2(Plat p) {
        llistaEscandall = new JList<>();
        ompliLlistaEscandallGUI2(p);
        scrollLiniaEscandall = new JScrollPane(llistaEscandall);
        scrollLiniaEscandall.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLiniaEscandall.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        escandall = new JPanel();
        escandall.setLayout(new BoxLayout(escandall, BoxLayout.Y_AXIS));
        JLabel titolEscandall = new JLabel("ESCANDALL");
        titolEscandall.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        escandall.add(titolEscandall);
        escandall.add(scrollLiniaEscandall);
        escandall.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    }

    private void ompliLlistaEscandallGUI2(Plat p) {
        Query q;
        String consultaEscandall = "select plat,num,QUANTITAT, u.NOM as unitatt,i.NOM as ingredientt,le.UNITAT,le.INGREDIENT\n"
                + "from linia_escandall le join unitat u on le.unitat=u.codi\n"
                + "join ingredient i on le.ingredient=i.codi\n"
                + "where plat=:codi";
        q = em.createNativeQuery(consultaEscandall);
        q.setParameter("codi", p.getCodi());
        List<Object[]> escandall = q.getResultList();
        ListllistaEscandall = new ArrayList<LiniaEscandall>();
        for (Object[] t : escandall) {
            int plat=(int)t[0];
            int num=(short)t[1];
            short qtat = (short) t[2];
            String unitat = "" + t[3];
            String ingredient = "" + t[4];
            int codiUnitat = (int) t[5];
            int codiIngredient = (int) t[6];
            LiniaEscandall lEscandall = new LiniaEscandall(plat,(int)num,(int) qtat, codiUnitat, codiIngredient, unitat, ingredient);
            ListllistaEscandall.add(lEscandall);
        }
        if (ListllistaEscandall != null) {
            modelLlistaEscandall = new DefaultListModel();
            for (int i = 0; i < ListllistaEscandall.size(); i++) {
                modelLlistaEscandall.add(i, ListllistaEscandall.get(i));
            }
            llistaEscandall.setModel(modelLlistaEscandall);
        }
    }

    private void partAbaixGUI2(Plat p) {
        sotaGUI2 = new JPanel();
        sotaGUI2.setLayout(new GridLayout(1, 2));
        JPanel afegir = new JPanel();
        afegir.setLayout(new GridLayout(4, 1));
        //afegir.setLayout(new BoxLayout(afegir,BoxLayout.Y_AXIS));

        JLabel qtat = new JLabel("Quantitat: ");
        qtatTxt = new JTextField();

        JPanel qtatt = new JPanel();
        qtatt.setLayout(new BoxLayout(qtatt, BoxLayout.X_AXIS));
        qtatt.add(qtat);
        qtatt.add(qtatTxt);

        JPanel comboUnitats = new JPanel();
        JLabel txtUnitats = new JLabel("Unitat: ");
        comboUnitats.add(txtUnitats);
        if (llistaUnitats != null) {
            Unitat arrayUnitats[] = llistaUnitats.toArray(new Unitat[0]);
            cmbUnitats = new JComboBox(arrayUnitats);
            cmbUnitats.setSelectedIndex(-1);
        }

        JPanel comboIngredients = new JPanel();
        JLabel txtIngredients = new JLabel("Ingredient: ");
        comboIngredients.add(txtIngredients);
        if (llistaIngredients != null) {
            Ingredient arrayIngredients[] = llistaIngredients.toArray(new Ingredient[0]);
            cmbIngredients = new JComboBox(arrayIngredients);
            cmbIngredients.setSelectedIndex(-1);
        }

        comboUnitats.add(cmbUnitats);
        comboIngredients.add(cmbIngredients);
        btnInsert = new JButton("Inserir Linia");
        btnInsert.addActionListener(new GestioInsert());

        afegir.add(qtatt);
        afegir.add(comboUnitats);
        afegir.add(comboIngredients);
        afegir.add(btnInsert);

        JPanel afegirr = new JPanel();
        afegirr.add(afegir);

        JPanel panellDelete = new JPanel();
        btnDelete = new JButton("Borrar Linea");
        btnDelete.addActionListener(new GestioDelete());
        panellDelete.add(btnDelete);

        sotaGUI2.add(afegirr);
        sotaGUI2.add(panellDelete);

    }

    class GestioInsert implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int qtat = 0;
            boolean correcte = false;
            if (cmbUnitats.getSelectedIndex() != -1 && cmbIngredients.getSelectedIndex() != -1) {
                try {
                    qtat = Integer.parseInt(qtatTxt.getText());
                    correcte = true;
                } catch (NumberFormatException ex) {
                    qtatTxt.setText("");
                    JOptionPane.showMessageDialog(f2, "Introduir Numeric en el camp Quantitat", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if (correcte == true) {
                    int numLinia = getUltimaLiniaEscandall(platSeleccionat.getCodi());
                    Unitat uni = (Unitat) cmbUnitats.getSelectedItem();
                    Ingredient ing = (Ingredient) cmbIngredients.getSelectedItem();
                    LiniaEscandall linia = new LiniaEscandall(platSeleccionat.getCodi(), numLinia + 1, qtat, uni.getCodi(), ing.getCodi());
                    int insertt = insertLiniaEscandall(linia);
                    if (insertt == 1) {
                        JOptionPane.showMessageDialog(f2, "Inserció efectuada", "INSERT", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(f2, "No s'ha inserit la nova linia", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    netejarInsert();
                    ompliLlistaEscandallGUI2(platSeleccionat);
                }
            } else {
                JOptionPane.showMessageDialog(f2, "Introdueix totes les dades per poder efectuar la inserció de la linea"
                        + " de escandall correctament", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int insertLiniaEscandall(LiniaEscandall linia) {
        Query q;
        String insert = "insert into linia_escandall\n"
                + "values(:plat,:num,:qtat,:unitat,:ingredient)\n";
        q = em.createNativeQuery(insert);
        q.setParameter("plat", linia.getPlat());
        q.setParameter("num", linia.getNum());
        q.setParameter("qtat", linia.getQtat());
        q.setParameter("unitat", linia.getUnitat());
        q.setParameter("ingredient", linia.getIngredient());
        return q.executeUpdate();

    }

    private int getUltimaLiniaEscandall(int codi) {
        Query q;
        String consultaNum = "select count(*)\n"
                + "from linia_escandall\n"
                + "where plat=:codi";
        q = em.createNativeQuery(consultaNum);
        q.setParameter("codi", codi);
        return Integer.parseInt("" + q.getSingleResult());

    }

    private void netejarInsert() {
        qtatTxt.setText("");
        cmbIngredients.setSelectedIndex(-1);
        cmbUnitats.setSelectedIndex(-1);
    }

    class GestioDelete implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (llistaEscandall.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(f2, "Seleccionar una linea per poder borrar-la", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                LiniaEscandall linia = llistaEscandall.getSelectedValue();
                int qtat = deleteLiniaEscandall(linia);
                if (qtat == 1) {
                    JOptionPane.showMessageDialog(f2, "Eliminació efectuada", "DELETE", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(f2, "No s'ha borrat la linia", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                ompliLlistaEscandallGUI2(platSeleccionat);

            }
        }
    }

    private int deleteLiniaEscandall(LiniaEscandall linia) {
        Query q;
        String delete = "delete from linia_escandall\n"
                + "where plat=:plat && num=:num\n";
        q = em.createNativeQuery(delete);
        q.setParameter("plat", linia.getPlat());
        q.setParameter("num", linia.getNum());
        return q.executeUpdate();
    }

    private void OmplirComboUnitats() {
        String consultaUnitats = "select u from Unitat u";
        Query cat = em.createQuery(consultaUnitats);
        llistaUnitats = cat.getResultList();
    }

    private void OmplirComboIngredients() {
        String consultaIngredients = "select i from Ingredient i";
        Query cat = em.createQuery(consultaIngredients);
        llistaIngredients = cat.getResultList();
    }

    private void tancarSubFinestra() {
        f2.dispose();
    }

    private void tancarApp() {
        em.getTransaction().commit();
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

package org.milaifontanals.projecte;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GestióEscandalls {

    private static String host;
    private static String user;
    private static String pass;
    private static String driver;
    private static String dialect;
    private static String up;

    public static void main(String[] args) {
        obtenirPropietats();
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            em = null;
            emf = null;
            if(up==null || host==null || user==null || user==null || pass==null || driver==null || dialect==null){
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
            System.out.println();
            System.out.println("EntityManager creat");

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
                System.out.println("EntityManager tancat");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory tancada");
            }
        }

    }

    private static void obtenirPropietats() {
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
        up=props.getProperty("up");

    }

}

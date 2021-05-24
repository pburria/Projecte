
import java.io.Serializable;



public class Cambrer implements Serializable {
    private int codi;
    private String nom;
    private String cognom1;
    private String cognom2;
    private String user;
    private String password;
    

    public Cambrer(int codi, String nom, String cognom1, String cognom2, String user, String password) {
        this.codi = codi;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.user = user;
        this.password = password;
    }
    
        public Cambrer(int codi, String nom, String cognom1, String user, String password) {
        this.codi = codi;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.user = user;
        this.password = password;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.codi;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cambrer other = (Cambrer) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cambrer{" + "codi=" + codi + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", user=" + user + ", password=" + password + '}';
    }
    
    
    
    
    
    
}

package org.milaifontanals.projecte;

public class Unitat{
    private int codi;
    private String nom;
    
    protected Unitat(){
        
    }
    
    public Unitat(int codi, String nom) {
        this.codi = codi;
        this.nom = nom;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.codi;
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
        final Unitat other = (Unitat) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom;
    }
    
    
}

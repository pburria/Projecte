
package org.milaifontanals.projecte;

public class Unitat {
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
    
    
}

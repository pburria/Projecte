
package org.milaifontanals.projecte;


public class Ingredient {
    private int codi;
    private String nom;
    
    protected Ingredient(){
        
    }
    
    public Ingredient(int codi, String nom) {
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

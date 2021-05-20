
package org.milaifontanals.projecte;

public class Categoria {
    private int codi;
    private String nom;
    private String color;
    
    protected Categoria(){
        
    }

    public Categoria(int codi, String nom, String color) {
        this.codi = codi;
        this.nom = nom;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}

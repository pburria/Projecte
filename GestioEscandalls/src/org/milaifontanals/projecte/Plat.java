package org.milaifontanals.projecte;

import java.math.BigDecimal;

public class Plat {
    private int codi;
    private String nom;
    private String descripcio;
    private BigDecimal preu;
    private byte[] foto;
    private Boolean disponible;
    private int categoria;
    
    protected Plat(){
        
    }

    public Plat(int codi, String nom, String descripcio, BigDecimal preu, byte[] foto, Boolean disponible, int categoria) {
        this.codi = codi;
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
        this.foto = foto;
        this.disponible = disponible;
        this.categoria = categoria;
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

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public BigDecimal getPreu() {
        return preu;
    }

    public void setPreu(BigDecimal preu) {
        this.preu = preu;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
    
    
}

package org.milaifontanals.projecte;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Plat implements Serializable {
    @Id
    private int codi;
    private String nom;
    private String DESCRIPCIO_MD;
    private BigDecimal preu;
    private byte[] foto;
    private Boolean disponible;
    private int categoria;
    private String color;
    private String nomCat;
    
    protected Plat(){
        
    }

    public Plat(int codi, String nom, String DESCRIPCIO_MD, BigDecimal preu, byte[] foto, Boolean disponible, String categoria, String color) {
        this.codi = codi;
        this.nom = nom;
        this.DESCRIPCIO_MD = DESCRIPCIO_MD;
        this.preu = preu;
        this.foto = foto;
        this.disponible = disponible;
        this.nomCat = categoria;
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

    public String getDESCRIPCIO_MD() {
        return DESCRIPCIO_MD;
    }

    public void setDESCRIPCIO_MD(String DESCRIPCIO_MD) {
        this.DESCRIPCIO_MD = DESCRIPCIO_MD;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.codi;
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
        final Plat other = (Plat) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom ;
    }    
}

package org.milaifontanals.projecte;

import java.io.Serializable;


public class InfoTaula implements Serializable {
    private int num;
    private int codi_comanda;
    private boolean esMeva;
    private int platsTotals;
    private int platsPreparats;
    private String nomCambrer;

    public InfoTaula(int num, int codi_comanda, boolean esMeva, int platsTotals, int platsPreparats, String nomCambrer) {
        this.num = num;
        this.codi_comanda = codi_comanda;
        this.esMeva = esMeva;
        this.platsTotals = platsTotals;
        this.platsPreparats = platsPreparats;
        this.nomCambrer = nomCambrer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCodi_comanda() {
        return codi_comanda;
    }

    public void setCodi_comanda(int codi_comanda) {
        this.codi_comanda = codi_comanda;
    }

    public boolean isEsMeva() {
        return esMeva;
    }

    public void setEsMeva(boolean esMeva) {
        this.esMeva = esMeva;
    }

    public int getPlatsTotals() {
        return platsTotals;
    }

    public void setPlatsTotals(int platsTotals) {
        this.platsTotals = platsTotals;
    }

    public int getPlatsPreparats() {
        return platsPreparats;
    }

    public void setPlatsPreparats(int platsPreparats) {
        this.platsPreparats = platsPreparats;
    }

    public String getNomCambrer() {
        return nomCambrer;
    }

    public void setNomCambrer(String nomCambrer) {
        this.nomCambrer = nomCambrer;
    }

    @Override
    public String toString() {
        return "InfoTaula{" + "num=" + num + ", codi_comanda=" + codi_comanda + ", esMeva=" + esMeva + ", platsTotals=" + platsTotals + ", platsPreparats=" + platsPreparats + ", nomCambrer=" + nomCambrer + '}';
    }
    
    
    
    
}

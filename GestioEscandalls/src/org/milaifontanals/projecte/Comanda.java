package org.milaifontanals.projecte;

import java.util.Date;

public class Comanda {
    private int codi;
    private Date data;
    private int taula;
    private int cambrer;
    
    protected Comanda(){
        
    }

    public Comanda(int codi, Date data, int taula, int cambrer) {
        this.codi = codi;
        this.data = data;
        this.taula = taula;
        this.cambrer = cambrer;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getTaula() {
        return taula;
    }

    public void setTaula(int taula) {
        this.taula = taula;
    }

    public int getCambrer() {
        return cambrer;
    }

    public void setCambrer(int cambrer) {
        this.cambrer = cambrer;
    }
    
    
}

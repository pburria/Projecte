package org.milaifontanals.projecte;

import java.io.Serializable;
import java.util.Objects;


public class LiniaComandaId implements Serializable {
    private int comanda;
    private int num;
    
    protected LiniaComandaId(){
        
    }

    public LiniaComandaId(int comanda, int num) {
        this.comanda = comanda;
        this.num = num;
    }

    public int getComanda() {
        return comanda;
    }

    public void setComanda(int comanda) {
        this.comanda = comanda;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.comanda;
        hash = 71 * hash + this.num;
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
        final LiniaComandaId other = (LiniaComandaId) obj;
        if (this.comanda != other.comanda) {
            return false;
        }
        if (this.num != other.num) {
            return false;
        }
        return true;
    }

    
    
    

    
    
    
    
}

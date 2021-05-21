package org.milaifontanals.projecte;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;


public class LiniaEscandallId implements Serializable {
    private int plat;
    private int num;
    
    protected LiniaEscandallId(){
        
    }

    public LiniaEscandallId(int plat, int num) {
        this.plat = plat;
        this.num = num;
    }

    public int getPlat() {
        return plat;
    }

    public void setPlat(int plat) {
        this.plat = plat;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.plat;
        hash = 83 * hash + this.num;
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
        final LiniaEscandallId other = (LiniaEscandallId) obj;
        if (this.plat != other.plat) {
            return false;
        }
        if (this.num != other.num) {
            return false;
        }
        return true;
    }

    

    
    
    
}

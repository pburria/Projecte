package org.milaifontanals.projecte;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Taula implements Serializable {
    @Id
    private int codi;
    
    protected Taula(){
        
    }

    public Taula(int codi) {
        this.codi = codi;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.codi;
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
        final Taula other = (Taula) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taula{" + "codi=" + codi + '}';
    }
    
    
    
    
}

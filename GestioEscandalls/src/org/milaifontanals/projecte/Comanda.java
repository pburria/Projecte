package org.milaifontanals.projecte;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Comanda {
    @Id
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.codi;
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
        final Comanda other = (Comanda) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Comanda{" + "codi=" + codi + ", data=" + data + ", taula=" + taula + ", Codi cambrer=" + cambrer + '}';
    }

    

    
    
    
    
}

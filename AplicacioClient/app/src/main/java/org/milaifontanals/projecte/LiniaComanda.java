package org.milaifontanals.projecte;

import java.io.Serializable;

public class LiniaComanda implements Serializable {

    private int comanda;
    private int plat;
    private int num;
    private int qtat;
    private Boolean acabat;

    public LiniaComanda(int comanda, int plat, int num, int qtat, Boolean acabat) {
        this.comanda = comanda;
        this.plat = plat;
        this.num = num;
        this.qtat = qtat;
        this.acabat = acabat;
    }

    public LiniaComanda(int num, int qtat, int plat) {
        this.plat = plat;
        this.num = num;
        this.qtat = qtat;
    }

    public int getComanda() {
        return comanda;
    }

    public void setComanda(int comanda) {
        this.comanda = comanda;
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

    public int getQtat() {
        return qtat;
    }

    public void setQtat(int qtat) {
        this.qtat = qtat;
    }

    public Boolean getAcabat() {
        return acabat;
    }

    public void setAcabat(Boolean acabat) {
        this.acabat = acabat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.comanda;
        hash = 17 * hash + this.num;
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
        final LiniaComanda other = (LiniaComanda) obj;
        if (this.comanda != other.comanda) {
            return false;
        }
        if (this.num != other.num) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LiniaComanda{" + "Codi comanda=" + comanda + ", Codi plat=" + plat + ", num=" + num + ", qtat=" + qtat + ", acabat=" + acabat + '}';
    }

}

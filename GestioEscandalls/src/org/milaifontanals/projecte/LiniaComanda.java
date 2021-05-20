package org.milaifontanals.projecte;


public class LiniaComanda {
    private int comanda;
    private int plat;
    private int num;
    private int qtat;
    private Boolean acabat;
    
    protected LiniaComanda(){
        
    }

    public LiniaComanda(int comanda, int plat, int num, int qtat, Boolean acabat) {
        this.comanda = comanda;
        this.plat = plat;
        this.num = num;
        this.qtat = qtat;
        this.acabat = acabat;
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
}

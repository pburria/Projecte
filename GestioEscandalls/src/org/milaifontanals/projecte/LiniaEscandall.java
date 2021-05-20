package org.milaifontanals.projecte;

public class LiniaEscandall {
    private int plat;
    private int num;
    private int qtat;
    private int unitat;
    private int ingredient;
    
    protected LiniaEscandall(){
        
    }

    public LiniaEscandall(int plat, int num, int qtat, int unitat, int ingredient) {
        this.plat = plat;
        this.num = num;
        this.qtat = qtat;
        this.unitat = unitat;
        this.ingredient = ingredient;
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

    public int getUnitat() {
        return unitat;
    }

    public void setUnitat(int unitat) {
        this.unitat = unitat;
    }

    public int getIngredient() {
        return ingredient;
    }

    public void setIngredient(int ingredient) {
        this.ingredient = ingredient;
    }
    
    
}

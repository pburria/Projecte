package org.milaifontanals.projecte;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(LiniaEscandallId.class)
public class LiniaEscandall {
    @Id 
    private int plat;
    @Id
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.plat;
        hash = 67 * hash + this.num;
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
        final LiniaEscandall other = (LiniaEscandall) obj;
        if (this.plat != other.plat) {
            return false;
        }
        if (this.num != other.num) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LiniaEscandall{" + "Codi plat=" + plat + ", num=" + num + ", qtat=" + qtat + ", Codi unitat=" + unitat + ", Codi ingredient=" + ingredient + '}';
    }

    

    
    
    

    
    
    
}


package org.milaifontanals.projecte;

import java.io.Serializable;

public class Login implements Serializable {
    private int sessionId;
    private Cambrer cambrer;

    public Login(int sessionId, Cambrer cambrer) {
        this.sessionId = sessionId;
        this.cambrer = cambrer;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Cambrer getCambrer() {
        return cambrer;
    }

    public void setCambrer(Cambrer cambrer) {
        this.cambrer = cambrer;
    }
    
    
    
}

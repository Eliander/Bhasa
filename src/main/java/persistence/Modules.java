package persistence;

import java.util.Objects;

/**
 *
 * @author Elia
 */
public class Modules {
    
    private String label;
    private String valore;

    public Modules(String label, String valore) {
        this.label = label;
        this.valore = valore;
    }

    public Modules() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.label);
        hash = 59 * hash + Objects.hashCode(this.valore);
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Modules) {
            Modules otherModule = (Modules) other;
            return label == otherModule.label && valore == otherModule.valore;
        } else {
            return false;
        }
    }
    
    
    
}

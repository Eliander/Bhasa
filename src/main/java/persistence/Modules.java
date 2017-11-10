package persistence;

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
    
    
    
}

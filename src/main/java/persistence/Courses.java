package persistence;

import java.util.ArrayList;

/**
 *
 * @author Elia
 */
public class Courses {
    
    private String label;
    private String valore;
    private String pub_type;
    private ArrayList<Modules> elenco_anni;

    public Courses(String label, String valore, String pub_type, ArrayList<Modules> elenco_anni) {
        this.label = label;
        this.valore = valore;
        this.pub_type = pub_type;
        this.elenco_anni = elenco_anni;
    }
    
    public Courses(String label, String valore, String pub_type) {
        this.label = label;
        this.valore = valore;
        this.pub_type = pub_type;
    }

    public Courses() {
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

    public String getPub_type() {
        return pub_type;
    }

    public void setPub_type(String pub_type) {
        this.pub_type = pub_type;
    }

    public ArrayList<Modules> getElenco_anni() {
        return elenco_anni;
    }

    public void setElenco_anni(ArrayList<Modules> elenco_anni) {
        this.elenco_anni = elenco_anni;
    }
    
}

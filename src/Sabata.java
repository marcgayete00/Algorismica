public class Sabata {
    private String nom;
    private float preu;
    private int min_talla;
    private int max_talla;
    private int pes;
    private float punctuacio;

    private boolean utilitzat = false;

    public Sabata(String nom, float preu, int min_talla, int max_talla, int pes, float punctuacio, boolean utilitzat) {
        this.nom = nom;
        this.preu = preu;
        this.min_talla = min_talla;
        this.max_talla = max_talla;
        this.pes = pes;
        this.punctuacio = punctuacio;
        this.utilitzat = utilitzat;
    }

    public String getNom() {
        return nom;
    }

    public float getPreu() {
        return preu;
    }

    public int getMin_talla() {
        return min_talla;
    }

    public int getMax_talla() {
        return max_talla;
    }

    public int getPes() {
        return pes;
    }

    public float getPunctuacio() {
        return punctuacio;
    }

    public boolean getUtilitzat() {
        return utilitzat;
    }

    public void setUtilitzat(boolean utilitzat) {
        this.utilitzat = utilitzat;
    }
}

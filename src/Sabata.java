public class Sabata {
    private String nom;
    private float preu;
    private int min_talla;
    private int max_talla;
    private int pes;
    private float puntuacio;

    private boolean utilitzat = false;

    private float descompte = 0;


    public Sabata(String nom, float preu, int min_talla, int max_talla, int pes, float puntuacio, boolean utilitzat, float descompte) {
        this.nom = nom;
        this.preu = preu;
        this.min_talla = min_talla;
        this.max_talla = max_talla;
        this.pes = pes;
        this.puntuacio = puntuacio;
        this.utilitzat = utilitzat;
        this.descompte = descompte;
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

    public float getPuntuacio() {
        return puntuacio;
    }

    public boolean getUtilitzat() {
        return utilitzat;
    }

    public float getDescompte() {
        return descompte;
    }


    public void setUtilitzat(boolean utilitzat) {
        this.utilitzat = utilitzat;
    }

    public boolean isUtilitzat() {
        return utilitzat;
    }

    public void setDescompte(float descompte) {
        this.descompte = descompte;
    }

    public void setPreu(float noupreu) {
        this.preu = noupreu;
    }
}
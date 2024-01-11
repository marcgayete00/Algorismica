public class Sabata {
    private String nom;
    private String nomComplet;
    private float preu;
    private int minTalla;
    private int maxTalla;
    private int pes;
    private float puntuacio;
    private float descompte = 0;
    private boolean descompteDuplicat;
    private boolean descompteNens;
    private boolean incrementPS;
    private boolean descomptePI;

    public Sabata(String nom, String nomComplet, float preu, int minTalla, int maxTalla, int pes, float puntuacio, float descompte, boolean descompteDuplicat, boolean descompteNens, boolean incrementPS, boolean descomptePI) {
        this.nom = nom;
        this.nomComplet = nomComplet;
        this.preu = preu;
        this.minTalla = minTalla;
        this.maxTalla = maxTalla;
        this.pes = pes;
        this.puntuacio = puntuacio;
        this.descompte = descompte;
        this.descompteDuplicat = descompteDuplicat;
        this.descompteNens = descompteNens;
        this.incrementPS = incrementPS;
        this.descomptePI = descomptePI;
    }

    public String getNom() {
        return nom;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public float getPreu() {
        return preu;
    }

    public int getMinTalla() {
        return minTalla;
    }

    public int getMaxTalla() {
        return maxTalla;
    }

    public int getPes() {
        return pes;
    }

    public float getPuntuacio() {
        return puntuacio;
    }

    public float getDescompte() {
        return descompte;
    }

    public void setDescompte(float descompte) {
        this.descompte = descompte;
    }

    public void setPreu(float nouPreu) {
        this.preu = nouPreu;
    }

    public boolean isDescompteDuplicat() {
        return descompteDuplicat;
    }

    public boolean isDescompteNens() {
        return descompteNens;
    }

    public boolean isIncrementPS() {
        return incrementPS;
    }

    public boolean isDescomptePI() {
        return descomptePI;
    }

    public void setDescompteDuplicat(boolean descompteDuplicat) {
        this.descompteDuplicat = descompteDuplicat;
    }

    public void setDescompteNens(boolean descompteNens) {
        this.descompteNens = descompteNens;
    }

    public void setIncrementPS(boolean incrementPS) {
        this.incrementPS = incrementPS;
    }

    public void setDescomptePI(boolean descomptePI) {
        this.descomptePI = descomptePI;
    }
}
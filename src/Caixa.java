import java.util.ArrayList;

public class Caixa {
    private ArrayList<Sabata> sabates;
    private int[] numSabatesMarques;
    private int numSabatesNens;
    private int numSabatesPA;
    private int numSabatesPB;
    private int pes;
    private float preu;
    private boolean descompteDuplicat;
    private boolean descompteNens;
    private boolean incrementPS;
    private boolean descomptePI;

    public Caixa(int pes, float preu) {
        this.sabates = new ArrayList<Sabata>();
        this.numSabatesNens = 0;
        this.numSabatesPA = 0;
        this.numSabatesPB = 0;
        this.pes = pes;
        this.preu = preu;
        this.descompteDuplicat = false;
        this.descompteNens = false;
        this.incrementPS = false;
        this.descomptePI = false;
    }

    public ArrayList<Sabata> getSabates() {
        return sabates;
    }

    public int getPes() {
        return pes;
    }

    public float getPreu() {
        return preu;
    }

    public int getNumSabatesNens() {
        return numSabatesNens;
    }

    public int getNumSabatesPA() {
        return numSabatesPA;
    }

    public int getNumSabatesPB() {
        return numSabatesPB;
    }

    public int[] addNumSabatesMarques() {
        return numSabatesMarques;
    }

    public void setNumSabatesMarques(int[] numSabatesMarques) {
        this.numSabatesMarques = numSabatesMarques;
    }

    public void addNumSabatesNens() {
        this.numSabatesNens++;
    }

    public void addNumSabatesPA(int numSabatesPA) {
        this.numSabatesPA = numSabatesPA;
    }

    public void setSabates(Sabata sabata) {
        this.sabates.add(sabata);
    }

    public void setPes(int pes) {
        this.pes = pes;
    }

    public void setPreu(float preu) {
        this.preu = preu;
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
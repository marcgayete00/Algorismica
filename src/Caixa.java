import java.util.ArrayList;

public class Caixa {

    private ArrayList<Sabata> Sabates;

    private int[] numSabatesMarques;

    private int numSabatesNens;

    private int numSabatesPA;

    private int numSabatesPB;

    private int Pes;

    private float Preu;

    private boolean descompteDuplicat;
    private boolean descompteNens;
    private boolean incrementPS;
    private boolean descomptePI;

    public Caixa(int Pes, float Preu) {
        this.Sabates = new ArrayList<Sabata>();
        this.numSabatesNens = 0;
        this.numSabatesPA = 0;
        this.numSabatesPB = 0;
        this.Pes = Pes;
        this.Preu = Preu;
        this.descompteDuplicat = false;
        this.descompteNens = false;
        this.incrementPS = false;
        this.descomptePI = false;
    }

    /*public Caixa(Caixa caixa) {
        this.Sabates = new ArrayList<Sabata>(caixa.Sabates);
        this.numSabatesNens = caixa.numSabatesNens;
        this.numSabatesPA = caixa.numSabatesPA;
        this.numSabatesPB = caixa.numSabatesPB;
        this.Pes = caixa.Pes;
        this.Preu = caixa.Preu;
    }*/



    public ArrayList<Sabata> getSabates() {
        return Sabates;
    }

    public int getPes() {
        return Pes;
    }

    public float getPreu() {
        return Preu;
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


    public void setSabates(Sabata Sabata) {
        this.Sabates.add(Sabata);
    }

    public void setPes(int Pes) {
        this.Pes = Pes;
    }

    public void setPreu(float Preu) {
        this.Preu = Preu;
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
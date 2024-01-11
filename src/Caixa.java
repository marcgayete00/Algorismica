import java.util.ArrayList;

public class Caixa {
    private ArrayList<Sabata> sabates;
    private float preu;
    private boolean descompteDuplicat;
    private boolean descompteNens;
    private boolean descomptePI;

    public Caixa(float preu) {
        this.sabates = new ArrayList<Sabata>();
        this.preu = preu;
        this.descompteDuplicat = false;
        this.descompteNens = false;
        this.descomptePI = false;
    }

    public ArrayList<Sabata> getSabates() {
        return sabates;
    }

    public float getPreu() {
        return preu;
    }

    public void setSabates(Sabata sabata) {
        this.sabates.add(sabata);
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

    public boolean isDescomptePI() {
        return descomptePI;
    }

    public void setDescompteDuplicat(boolean descompteDuplicat) {
        this.descompteDuplicat = descompteDuplicat;
    }

    public void setDescompteNens(boolean descompteNens) {
        this.descompteNens = descompteNens;
    }

    public void setDescomptePI(boolean descomptePI) {
        this.descomptePI = descomptePI;
    }
}
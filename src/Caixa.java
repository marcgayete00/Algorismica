import java.util.ArrayList;

public class Caixa {

    private ArrayList<Sabata> Sabates;

    private int Pes;

    private float Preu;

    public Caixa(int Pes, float Preu) {
        this.Sabates = new ArrayList<Sabata>();
        this.Pes = Pes;
        this.Preu = Preu;
    }

    public ArrayList<Sabata> getSabates() {
        return Sabates;
    }

    public int getPes() {
        return Pes;
    }

    public float getPreu() {
        return Preu;
    }

    public int getTamany( ArrayList<Sabata> sabatas ){
        return sabatas.size();
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
}
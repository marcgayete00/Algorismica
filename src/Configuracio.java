import java.util.ArrayList;

public class Configuracio {
    ArrayList<Caixa> caixes;
    int prioritat;

    public Configuracio(ArrayList<Caixa> caixes, int prioritat) {
        this.caixes = caixes;
        this.prioritat = prioritat;
    }

    public ArrayList<Caixa> getCaixes() {
        return caixes;
    }

    public int getPrioritat() {
        return prioritat;
    }

    public void afegirCaixa(Caixa caixa) {
        caixes.add(caixa);
    }

    public void eliminarCaixa(int i) {
        caixes.remove(i);
    }
}
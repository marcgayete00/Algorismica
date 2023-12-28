import java.util.ArrayList;

public class CuaPrioritat {

    private static ArrayList<Caixa> cua;

    public CuaPrioritat(ArrayList<Caixa> cua) {
        this.cua = new ArrayList<Caixa>();
    }

    public static ArrayList<Caixa> getCua() {
        return cua;
    }

    public static void setCua(ArrayList<Caixa> cua) {
        CuaPrioritat.cua = cua;
    }


    public void afegir(ArrayList<Caixa> configuracio, int prioritat) {
        if (prioritat == 0) {
            cua.add(new Caixa(configuracio, prioritat));
        } else {
            int i = 0;
            while (i < cua.size() && cua.get(i). < prioritat) {
                i++;
            }
            cua.add(i, new Caixa(configuracio, prioritat));
        }
    }
}

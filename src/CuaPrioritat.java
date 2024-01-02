import java.util.ArrayList;
import java.util.Comparator;

public class CuaPrioritat {

    private static ArrayList<Configuracio> cua;


    public CuaPrioritat(Configuracio cua) {
        this.cua = new ArrayList<Configuracio>();
    }

    public static ArrayList<Configuracio> getCua() {
        return cua;
    }

    public static void setCua(ArrayList<Configuracio> cua) {
        CuaPrioritat.cua = cua;
    }


    public void afegir(Configuracio configuracio, int prioritat) {
        cua.add(configuracio);
    }

    public boolean isEmpty() {
        return cua.isEmpty();
    }

    public int treurelementprioritari() {
        int indice = 0;
        int prioritat = 10;
        for(int i = 0; i < cua.size(); i++) {
            if (cua.get(i).getPrioritat() < prioritat) {
                indice = i;
                prioritat = cua.get(i).getPrioritat();
            }
        }
        return indice;
    }

    public void eliminarelementprioritari(int indice) {
        cua.remove(indice);
    }

    public boolean comprovar(int size) {
        //cua.sort(Comparator.comparingDouble(Configuracio::getPrioritat).reversed());  // Ordenar de mayor a menor precio
        //System.out.println("Cola 0: " + cua.get(0).getPrioritat());
        boolean flag = false;
        for(int i = 0; i < cua.size(); i++) {
            if (cua.get(i).getPrioritat() < size) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void clear() {
        cua.clear();
    }
}

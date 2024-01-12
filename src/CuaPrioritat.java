import java.util.ArrayList;
import java.util.Comparator;

// TODO: Mirar perquè la variable cua és estàtica
public class CuaPrioritat {
    private static ArrayList<Configuracio> cua;

    public CuaPrioritat() {
        cua = new ArrayList<Configuracio>();
    }

    public static ArrayList<Configuracio> getCua() {
        return cua;
    }

    public void afegir(Configuracio configuracio) {
        cua.add(configuracio);
        cua.sort(Comparator.comparingDouble(Configuracio::getPrioritat).reversed());
    }

    public boolean isEmpty() {
        return cua.isEmpty();
    }

    public int treureElementPrioritari() {
        int index = 0;
        int prioritat = 10;
        int numerosabates = 0;
        for (int i = 0; i < cua.size(); i++) {
            if (cua.get(i).getPrioritat() <= prioritat) {
                int numerosabatesaux = 0;
                for (int j = 0; j < cua.get(i).getCaixes().size(); j++) {
                    numerosabatesaux += cua.get(i).getCaixes().get(j).getSabates().size();
                }
                if (numerosabatesaux > numerosabates) {
                    index = i;
                    prioritat = cua.get(i).getPrioritat();
                    numerosabates = numerosabatesaux;
                }
            }
        }
        return index;
    }

    public void eliminarElementPrioritari(int indiceprioritario) {
        cua.remove(indiceprioritario);
    }

    public boolean comprovar(int mida) {
        boolean flag = false;

        for (int i = 0; i < cua.size(); i++) {
            if (cua.get(i).getPrioritat() <= mida) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public void netejar() {
        cua.clear();
    }
}
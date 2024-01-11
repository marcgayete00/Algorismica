import java.util.ArrayList;

// TODO: S'ha de mirar aquesta classe. Hi ha funcions que reben variables que després no utilitzen
// TODO: Mirar perquè la variable cua és estàtica
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

    public int treureElementPrioritari() {
        int index = 0;
        int prioritat = 10;

        for (int i = 0; i < cua.size(); i++) {
            if (cua.get(i).getPrioritat() < prioritat) {
                index = i;
                prioritat = cua.get(i).getPrioritat();
            }
        }

        return index;
    }

    public void eliminarElementPrioritari(int index) {
        cua.remove(index);
    }

    public boolean comprovar(int mida) {
        boolean flag = false;

        for (int i = 0; i < cua.size(); i++) {
            if (cua.get(i).getPrioritat() < mida) {
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
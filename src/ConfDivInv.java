import java.util.ArrayList;

public class ConfDivInv {
    private ArrayList<Sabata> inventari1;
    private ArrayList<Sabata> inventari2;
    private double preuInvetari1;
    private double preuInvetari2;

    public ConfDivInv(ArrayList<Sabata> inventari1, ArrayList<Sabata> inventari2, float preuInvetari1, float preuInvetari2) {
        this.inventari1 = inventari1;
        this.inventari2 = inventari2;
        this.preuInvetari1 = preuInvetari1;
        this.preuInvetari2 = preuInvetari2;
    }

    public ArrayList<Sabata> getInventari1() {
        return inventari1;
    }

    public ArrayList<Sabata> getInventari2() {
        return inventari2;
    }

    public double getPreuInvetari1() {
        return preuInvetari1;
    }

    public double getPreuInvetari2() {
        return preuInvetari2;
    }

    public void setInventari1(Sabata sabata) {
        this.inventari1.add(sabata);
    }

    public void setInventari2(Sabata sabata) {
        this.inventari2.add(sabata);
    }

    public void setPreuInvetari1(double preuInvetari1) {
        this.preuInvetari1 = preuInvetari1;
    }

    public void setPreuInvetari2(double preuInvetari2) {
        this.preuInvetari2 = preuInvetari2;
    }
}

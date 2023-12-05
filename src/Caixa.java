public class Caixa {
    private double pes;
    private double preu;
    private Sabata[] sabates;

    public Caixa(double pes, double preu, Sabata[] sabates) {
        this.pes = pes;
        this.preu = preu;
        this.sabates = sabates;
    }

    public double getPes() {
        return pes;
    }

    public double getPreu() {
        return preu;
    }

    public Sabata[] getSabates() {
        return sabates;
    }


}

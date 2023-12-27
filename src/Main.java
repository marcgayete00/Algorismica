import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static int nIteracions;
    static int nSabatesFitxer = 0;
    static int cajastotales = 1;

    static boolean esPrimera = false;

    static ArrayList<Caixa> configuraciooptima = new ArrayList<Caixa>();

    public static Sabata[] lecturaFitxer(){
        try {
            File myObj = new File("Datasets/datasetXS.txt");
            Scanner myReader = new Scanner(myObj);

            nSabatesFitxer = Integer.parseInt(myReader.nextLine());

            Sabata[] sabatesArray = new Sabata[nSabatesFitxer];
            int index = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(";");
                String[] parts2 = parts[0].split(" ");
                Sabata sabata = new Sabata(
                        parts2[0],
                        Float.parseFloat(parts[1].replace(",", ".")),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Float.parseFloat(parts[5].replace(",", ".")),
                        0,
                        false,
                        false,
                        false,
                        false
                );

                sabatesArray[index] = sabata;
                index++;
            }
            myReader.close();
            //mostrarDades( sabatesArray);
            return sabatesArray;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    private static void mostrarDades(ArrayList<Caixa> configuracio){
        cajastotales = 0;
        for(int i= 0;i<configuracio.toArray().length;i++){
            System.out.println("Caja "+i);
            cajastotales++;
            for(int j = 0; j<configuracio.get(i).getSabates().size();j++){
                System.out.print("Sabata "+configuracio.get(i).getSabates().get(j).getNom() + " ");
                System.out.println(configuracio.get(i).getSabates().get(j).getPreu() + " | " +configuracio.get(i).getSabates().get(j).getDescompte());
            }
            System.out.println("Preu "+configuracio.get(i).getPreu());
        }
        System.out.println("----------------------");
    }

    private static void mostrarDadesInventari(ConfDivInv configuracioInv) {
        System.out.println("----------------------");
        System.out.println("Inventari 1: ");
        for(int i = 0; i < configuracioInv.getInventari1().size(); i++) {
            System.out.println("Sabata " + configuracioInv.getInventari1().get(i).getNom() + " " + configuracioInv.getInventari1().get(i).getPreu());
        }
        System.out.println("Preu Inventari 1: " + configuracioInv.getPreuInvetari1());

        System.out.println("Inventari 2: ");
        for(int i = 0; i < configuracioInv.getInventari2().size(); i++) {
            System.out.println("Sabata " + configuracioInv.getInventari2().get(i).getNom() + " " + configuracioInv.getInventari2().get(i).getPreu());
        }
        System.out.println("Preu Inventari 2: " + configuracioInv.getPreuInvetari2());
        System.out.println("----------------------");
    }

    private static void CalcularDescomptes(ArrayList<Caixa> configuracio) {
        ArrayList<TipusDescompte> tipusDescomptes = new ArrayList<>();


        //Descompte 20% marca duplicada
        for (int i = 0; i<configuracio.size(); i++){
            ArrayList<TipusDescompte> tipusDescomptesNens = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntInf = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntSup = new ArrayList<>();

                for(int j = 0; j<configuracio.get(i).getSabates().size(); j++) {

                    //System.out.println("Sabata" +j+ ": de caja "+ i +" " +configuracio.get(i).getSabates().get(j).getNom() + " " + configuracio.get(i).getSabates().get(j).getPreu() + " " + configuracio.get(i).getSabates().get(j).getDescompte());

                    for (int k = 0; k < configuracio.get(i).getSabates().size(); k++) {

                        if (configuracio.get(i).getSabates().get(j).getNom().equals(configuracio.get(i).getSabates().get(k).getNom()) && j != k && configuracio.get(i).getSabates().get(j).getDescompte() == 0) {
                            if (!configuracio.get(i).getSabates().get(j).isDescompteDuplicat()) {
                                configuracio.get(i).getSabates().get(j).setDescompte(configuracio.get(i).getSabates().get(j).getDescompte() + (configuracio.get(i).getSabates().get(j).getPreu() * 0.2f));
                                configuracio.get(i).getSabates().get(j).setDescompteDuplicat(true);
                                configuracio.get(i).setDescompteDuplicat(true);
                            }

                        }
                    }
                    //Descompte 35% sabates nens
                    if (configuracio.get(i).getSabates().get(j).getMax_talla() < 35 ) {
                        tipusDescomptesNens.add(new TipusDescompte(i, j, 1));
                    }

                    if (configuracio.get(i).getSabates().get(j).getPuntuacio() < 5) {
                        //System.out.println("shoe punt if"+ configuracio.get(i).getSabates().get(j).getNom() + " " + configuracio.get(i).getSabates().get(j).getPuntuacio());
                        tipusDescomptesPuntInf.add(new TipusDescompte(i, j, 2));
                    }

                    if (configuracio.get(i).getSabates().get(j).getPuntuacio() > 8) {
                        tipusDescomptesPuntSup.add(new TipusDescompte(i, j, 3));
                    }
                }

                if (tipusDescomptesNens.size() >= 2){
                    for (int j = 0; j<tipusDescomptesNens.size(); j++){
                        tipusDescomptes.add(tipusDescomptesNens.get(j));
                    }
                }
                if (tipusDescomptesPuntInf.size() >= 3){
                    for (int j = 0; j<tipusDescomptesPuntInf.size(); j++){
                        tipusDescomptes.add(tipusDescomptesPuntInf.get(j));
                    }
                }
                if (tipusDescomptesPuntSup.size() >= 3){
                    for (int j = 0; j<tipusDescomptesPuntSup.size(); j++){
                        tipusDescomptes.add(tipusDescomptesPuntSup.get(j));
                    }
                }
        }

        //Descompte 35% sabates nens
        for(int i = 0; i<tipusDescomptes.size(); i++){
            TipusDescompte tipusDescompte = tipusDescomptes.get(i);
            int j = tipusDescompte.getCaixa();
            int n = tipusDescompte.getSabata();
            int m = tipusDescompte.getTipusDescompte();

            switch (m){
                case 1:
                    if (!configuracio.get(j).getSabates().get(n).isDescompteNens()) {
                        configuracio.get(j).getSabates().get(n).setDescompte(configuracio.get(j).getSabates().get(n).getDescompte() + (configuracio.get(j).getSabates().get(n).getPreu() * 0.35f));
                        configuracio.get(j).getSabates().get(n).setDescompteNens(true);
                        configuracio.get(j).setDescompteNens(true);
                    }
                    break;

                case 2:
                    if (!configuracio.get(j).getSabates().get(n).isDescomptePI()) {
                        configuracio.get(j).getSabates().get(n).setDescompte(configuracio.get(j).getSabates().get(n).getDescompte() + (configuracio.get(j).getSabates().get(n).getPreu() * 0.4f));
                        configuracio.get(j).getSabates().get(n).setDescomptePI(true);
                        configuracio.get(j).setDescomptePI(true);
                    }
                    break;

                case 3:
                    if (!configuracio.get(j).getSabates().get(n).isIncrementPS()) {
                        configuracio.get(j).getSabates().get(n).setDescompte(configuracio.get(j).getSabates().get(n).getDescompte() - (configuracio.get(j).getSabates().get(n).getPreu() * 0.2f));
                        configuracio.get(j).getSabates().get(n).setIncrementPS(true);
                        configuracio.get(j).setIncrementPS(true);
                    }
                    break;
            }
        }
    }

    private static void calcularpreuCaixa(Sabata[] sabatesArray,ArrayList<Caixa> configuracio) {
        boolean preuexces = false;
        for(int i = 0;i<configuracio.size();i++){
            for(int j = 0;j<configuracio.get(i).getSabates().size();j++){
                configuracio.get(i).setPreu(configuracio.get(i).getPreu() + (configuracio.get(i).getSabates().get(j).getPreu() - configuracio.get(i).getSabates().get(j).getDescompte()));
            }
        }
        for(int i = 0;i<configuracio.size();i++){
            if (configuracio.get(i).getPreu() > 1000) {
                preuexces = true;
                break;
            }
        }
        //System.out.println("Size1: " + configuracio.size() + " Size2: " + configuraciooptima.size());
        if(configuraciooptima.size() == 0 && !preuexces){
            copiaconfiguracio(configuracio);
        }
        if(configuracio.size() < configuraciooptima.size() && !preuexces){
            configuraciooptima.clear();
            copiaconfiguracio(configuracio);

        }
        //mostrarDades(configuracio);
        if(configuracio.size() == sabatesArray.length){
            System.out.println(configuracio.size());
            mostrarDades(configuraciooptima);
        }
    }

    private static boolean calcularpreuCaixaBacktracking(Sabata[] sabatesArray,ArrayList<Caixa> configuracio) {

        boolean preuexces = false;
        for(int i = 0;i<configuracio.size();i++){
            for(int j = 0;j<configuracio.get(i).getSabates().size();j++){
                configuracio.get(i).setPreu(configuracio.get(i).getPreu() + (configuracio.get(i).getSabates().get(j).getPreu() - configuracio.get(i).getSabates().get(j).getDescompte()));
            }
            //mostrarDades(configuracio);
            if (configuracio.get(i).isDescompteDuplicat() && configuracio.get(i).isDescompteNens() && configuracio.get(i).isDescomptePI() && configuracio.get(i).getPreu() > 1000) {
                System.out.println("Descartada: " + configuracio.get(i).getPreu());
                ReseteigDades(configuracio);
                return true;
            }

            if (configuracio.get(i).getPreu() > 1000) {
                return true;

            }

        }
        return false;
    }

    private static void copiaconfiguracio(ArrayList<Caixa> configuracio) {
        Caixa nuevaCaixa = null;
        Sabata nuevaSabata = null;
        for (int i = 0; i < configuracio.size(); i++) {
            nuevaCaixa = new Caixa(0, configuracio.get(i).getPreu());
            for (int j = 0; j < configuracio.get(i).getSabates().size(); j++) {
                nuevaSabata = new Sabata(configuracio.get(i).getSabates().get(j).getNom(),
                                         configuracio.get(i).getSabates().get(j).getPreu(),
                                         configuracio.get(i).getSabates().get(j).getMin_talla(),
                                         configuracio.get(i).getSabates().get(j).getMax_talla(),
                                         configuracio.get(i).getSabates().get(j).getPes(),
                                         configuracio.get(i).getSabates().get(j).getPuntuacio(),
                                         configuracio.get(i).getSabates().get(j).getDescompte(),
                                         configuracio.get(i).getSabates().get(j).isDescompteDuplicat(),
                                         configuracio.get(i).getSabates().get(j).isDescompteNens(),
                                         configuracio.get(i).getSabates().get(j).isIncrementPS(),
                                         configuracio.get(i).getSabates().get(j).isDescomptePI());
                nuevaCaixa.setSabates(nuevaSabata);
            }
            configuraciooptima.add(nuevaCaixa);
        }
    }

    private static void ReseteigDades(ArrayList<Caixa> configuracio) {
        for(int i = 0;i<configuracio.size();i++){
            configuracio.get(i).setPreu(0);
            for(int j = 0;j<configuracio.get(i).getSabates().size();j++){
                configuracio.get(i).getSabates().get(j).setDescompte(0);
                configuracio.get(i).getSabates().get(j).setDescompteDuplicat(false);
                configuracio.get(i).getSabates().get(j).setDescompteNens(false);
                configuracio.get(i).getSabates().get(j).setDescomptePI(false);
                configuracio.get(i).getSabates().get(j).setIncrementPS(false);
            }
        }
    }

    public static void enviamentCaixesForcaBruta(Sabata[] sabatesArray, int ordre, ArrayList<Caixa> configuracio) {
        if (ordre == sabatesArray.length) {
            CalcularDescomptes(configuracio);
            calcularpreuCaixa(sabatesArray,configuracio);
            //mostrarDades(configuracio);
            ReseteigDades(configuracio);
            return;
        }
        // Bucle para manejar el resto de las cajas
        for (int i = 0; i < configuracio.size(); i++) {
            if (configuracio.get(i).getSabates().size() < 6){
                configuracio.get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;
                enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
                configuracio.get(i).getSabates().remove(sabatesArray[ordre]);
            }

        }

        Caixa nuevaCaixa = new Caixa(0, 0);
        nuevaCaixa.setSabates(sabatesArray[ordre]);
        //nuevaCaixa.setPreu(sabatesArray[ordre].getPreu());
        configuracio.add(nuevaCaixa);
        nIteracions++;
        enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
        configuracio.remove(configuracio.size() - 1);
    }

    private static void enviamentCaixesBacktracking(Sabata[] sabatesArray, int ordre, ArrayList<Caixa> configuracio) {

        if (ordre == sabatesArray.length) {
            ReseteigDades(configuracio);
            CalcularDescomptes(configuracio);
            calcularpreuCaixaBacktracking(sabatesArray,configuracio);
            comprovarSolucio(configuracio, sabatesArray);
            //ReseteigDades(configuracio);
            return;
        }
        // Bucle para manejar el resto de las cajas
        for (int i = 0; i < configuracio.size(); i++) {
            if (configuracio.get(i).getSabates().size() < 6){
                configuracio.get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;

                enviamentCaixesBacktracking(sabatesArray, ordre + 1, configuracio);
                configuracio.get(i).getSabates().remove(sabatesArray[ordre]);
            }

        }

        Caixa nuevaCaixa = new Caixa(0, 0);
        nuevaCaixa.setSabates(sabatesArray[ordre]);
        //nuevaCaixa.setPreu(sabatesArray[ordre].getPreu());
        configuracio.add(nuevaCaixa);
        nIteracions++;
        CalcularDescomptes(configuracio);

        if (calcularpreuCaixaBacktracking(sabatesArray,configuracio)){
            ReseteigDades(configuracio);
            configuracio.remove(configuracio.size() - 1);
            return;
        }

        enviamentCaixesBacktracking(sabatesArray, ordre + 1, configuracio);
        configuracio.remove(configuracio.size() - 1);
    }

    private static void comprovarSolucio(ArrayList<Caixa> configuracio, Sabata[] sabatesArray) {
        if(configuraciooptima.size() == 0){
            copiaconfiguracio(configuracio);
        }
        if(configuracio.size() < configuraciooptima.size()){
            configuraciooptima.clear();
            copiaconfiguracio(configuracio);
        }

        if(configuracio.size() == sabatesArray.length){
            System.out.println(configuracio.size());
            mostrarDades(configuraciooptima);
        }
    }

    private static void enviamentCaixesBranchAndBound(Sabata[] sabatesArray, int ordre, ArrayList<Caixa> configuracio) {


    }


    /*
    private static void divisioInventariForcaBruta(Sabata[] sabatesArray) {

        ArrayList<Sabata> inventari1 = new ArrayList<>();
        ArrayList<Sabata> inventari2 = new ArrayList<>();

        int costInvetari1 = 0;
        int costInvetari2 = 0;

        for (int i = 0; i < sabatesArray.length; i++){
            if (costInvetari1 <= costInvetari2) {
                inventari1.add(sabatesArray[i]);
                costInvetari1 += sabatesArray[i].getPreu();
            } else {
                inventari2.add(sabatesArray[i]);
                costInvetari2 += sabatesArray[i].getPreu();
            }
        }
        System.out.println("Inventari 1: " + costInvetari1);
        System.out.println("Inventari 2: " + costInvetari2);
    }*/


    private static void divisioInventariForcaBruta(Sabata[] sabatesArray, int index, ConfDivInv configuracioInv, ConfDivInv configuracioOptima) {

        if (index == sabatesArray.length) {
            seleccionarConfiguracioOptima(configuracioInv, configuracioOptima);
            return;
        }
        configuracioInv.getInventari1().add(sabatesArray[index]);
        configuracioInv.setPreuInvetari1(configuracioInv.getPreuInvetari1() + sabatesArray[index].getPreu());
        divisioInventariForcaBruta(sabatesArray, index + 1, configuracioInv, configuracioOptima);
        configuracioInv.getInventari1().remove(sabatesArray[index]);
        configuracioInv.setPreuInvetari1(configuracioInv.getPreuInvetari1() - sabatesArray[index].getPreu());

        configuracioInv.getInventari2().add(sabatesArray[index]);
        configuracioInv.setPreuInvetari2(configuracioInv.getPreuInvetari2() + sabatesArray[index].getPreu());
        divisioInventariForcaBruta(sabatesArray, index + 1, configuracioInv, configuracioOptima);
        configuracioInv.getInventari2().remove(sabatesArray[index]);
        configuracioInv.setPreuInvetari2(configuracioInv.getPreuInvetari2() - sabatesArray[index].getPreu());
    }

    private static void seleccionarConfiguracioOptima(ConfDivInv configuracioInv, ConfDivInv configuracioOptima) {
        if (!esPrimera) {
            for (int i = 0; i < configuracioInv.getInventari1().size(); i++) {
                configuracioOptima.getInventari1().add(configuracioInv.getInventari1().get(i));
            }

            for (int i = 0; i < configuracioInv.getInventari2().size(); i++) {
                configuracioOptima.getInventari2().add(configuracioInv.getInventari2().get(i));
            }

            configuracioOptima.setPreuInvetari1(configuracioInv.getPreuInvetari1());
            configuracioOptima.setPreuInvetari2(configuracioInv.getPreuInvetari2());
            esPrimera = true;

        } else {
            double resultOptim = configuracioOptima.getPreuInvetari1() - configuracioOptima.getPreuInvetari2();
            double resultActual = configuracioInv.getPreuInvetari1() - configuracioInv.getPreuInvetari2();

            if (Math.abs(resultActual) < Math.abs(resultOptim)) {
                configuracioOptima.getInventari1().clear();
                configuracioOptima.getInventari2().clear();

                for (Sabata sabata : configuracioInv.getInventari1()) {
                    configuracioOptima.getInventari1().add(sabata);
                }

                for (Sabata sabata : configuracioInv.getInventari2()) {
                    configuracioOptima.getInventari2().add(sabata);
                }

                configuracioOptima.setPreuInvetari1(configuracioInv.getPreuInvetari1());
                configuracioOptima.setPreuInvetari2(configuracioInv.getPreuInvetari2());
            }
        }
    }

    private static void divisioInventariGreedy(Sabata[] sabatesArray) {
        List<Sabata> sabatesList = Arrays.asList(sabatesArray);

        sabatesList.sort(Comparator.comparingDouble(Sabata::getPreu).reversed());  // Ordenar de mayor a menor precio

        ArrayList<Sabata> inventari1 = new ArrayList<>();
        ArrayList<Sabata> inventari2 = new ArrayList<>();
        int costInvetari1 = 0;
        int costInvetari2 = 0;

        for (int i = 0; i < sabatesArray.length; i++){
            if (costInvetari1 <= costInvetari2) {
                inventari1.add(sabatesArray[i]);
                costInvetari1 += sabatesArray[i].getPreu();
            } else {
                inventari2.add(sabatesArray[i]);
                costInvetari2 += sabatesArray[i].getPreu();
            }
        }

        for(int i = 0; i<inventari1.size(); i++) {
            System.out.println("Sabata " + inventari1.get(i).getNom() + " " + inventari1.get(i).getPreu());
        }
        System.out.println("Inventari 1: " + costInvetari1);
        for(int i = 0; i<inventari2.size(); i++) {
            System.out.println("Sabata " + inventari2.get(i).getNom() + " " + inventari2.get(i).getPreu());
        }
        System.out.println("Inventari 2: " + costInvetari2);


    }

    private static List<List<Sabata>> divisioInventariGreedy2(List<Sabata> sabatesList, int numInvetaris){
        sabatesList.sort(Comparator.comparingDouble(Sabata::getPreu).reversed());

        List<List<Sabata>> inventarios = new ArrayList<>();
        for (int i = 0; i < numInvetaris; i++) {
            inventarios.add(new ArrayList<>());
        }

        for (Sabata sabata : sabatesList) {
            int indiceInventario = obtindreMenorCostInventari(inventarios);
            inventarios.get(indiceInventario).add(sabata);
        }
        return inventarios;
    }

    private static int obtindreMenorCostInventari(List<List<Sabata>> inventarios) {
        int indiceInventario = 0;
        double menorCosto = calcularCosto(inventarios.get(0));

        for (int i = 1; i < inventarios.size(); i++) {
            double costoInventario = calcularCosto(inventarios.get(i));
            if (costoInventario < menorCosto) {
                menorCosto = costoInventario;
                indiceInventario = i;
            }
        }

        return indiceInventario;
    }

    private static double calcularCosto(List<Sabata> sabatas) {
        int costo = 0;
        for (Sabata sabata : sabatas) {
            costo += sabata.getPreu();
        }
        return costo;
    }

    public static void main(String[] args) {

        Sabata[] sabatesArray = lecturaFitxer();

        //mostrarDades(sabatesArray);

        while (true) {
            System.out.print("Benvingut a la sabateria zaballos\n");
            System.out.print("---- Escolliu un problema a resoldre ----\n");
            System.out.print("1. Enviament de caixes\n");
            System.out.print("2. Divisió d'inventari\n");

            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            int option2 = 0, option3 = 0;

            ArrayList<Caixa> configuracio = new ArrayList<Caixa>();
            ConfDivInv configuracioInv = new ConfDivInv(new ArrayList<Sabata>(), new ArrayList<Sabata>(), 0, 0);
            ConfDivInv configuracioOptima = new ConfDivInv(new ArrayList<Sabata>(), new ArrayList<Sabata>(), 0, 0);
            //classificarSabates(sabatesArray);
            switch (option){
                case 1:
                    while(option2 != 4){
                        System.out.print("---- Escolliu un algorisme ----\n");
                        System.out.print("1. Força bruta\n");
                        System.out.print("2. Backtracking\n");
                        System.out.print("3. Programació dinàmica\n");
                        System.out.print("4. Tornar\n");

                        option2 = sc.nextInt();

                        switch (option2){
                            case 1:
                                System.out.print("Has escollit l'algorisme de força bruta\n");

                                configuracio = new ArrayList<Caixa>();
                                configuracio.add(new Caixa(0, 0));
                                configuracio.get(0).setSabates(sabatesArray[0]);
                                //configuracio.get(0).setPreu(sabatesArray[0].getPreu());
                                enviamentCaixesForcaBruta(sabatesArray, 1, configuracio);
                                //System.out.println("Numero iteracions: " + nIteracions);
                                System.out.println("La configuración con menos cajas tiene: " + cajastotales + " cajas");
                                nIteracions = 0;
                                cajastotales = 0;

                                break;
                            case 2:
                                System.out.print("Has escollit l'algorisme de backtracking\n");
                                configuracio = new ArrayList<Caixa>();
                                configuracio.add(new Caixa(0, 0));
                                configuracio.get(0).setSabates(sabatesArray[0]);
                                //configuracio.get(0).setPreu(sabatesArray[0].getPreu());
                                enviamentCaixesBacktracking(sabatesArray, 1, configuracio);
                                //System.out.println("Numero iteracions: " + nIteracions);
                                System.out.println("La configuración con menos cajas tiene: " + cajastotales + " cajas");
                                nIteracions = 0;
                                cajastotales = 0;

                                break;
                            case 3:
                                System.out.print("Has escollit l'algorisme de branch and bound\n");
                                configuracio = new ArrayList<Caixa>();
                                configuracio.add(new Caixa(0, 0));
                                configuracio.get(0).setSabates(sabatesArray[0]);
                                //configuracio.get(0).setPreu(sabatesArray[0].getPreu());
                                enviamentCaixesBranchAndBound(sabatesArray, 1, configuracio);
                                //System.out.println("Numero iteracions: " + nIteracions);
                                System.out.println("La configuración con menos cajas tiene: " + cajastotales + " cajas");

                                nIteracions = 0;
                                cajastotales = 0;
                                break;
                            case 4:
                                break;
                            default:
                                System.out.print("Opció incorrecta\n");
                                break;
                        }
                    }
                case 2:
                    System.out.print("Has escollit l'opció 2\n");
                    while(option3 != 4){
                        System.out.print("---- Escolliu un algorisme ----\n");
                        System.out.print("1. Força bruta\n");
                        System.out.print("2. Greedy\n");
                        System.out.print("3. Greedy2\n");
                        System.out.print("4. Tornar\n");

                        option3 = sc.nextInt();

                        switch (option3){
                            case 1:
                                System.out.print("Has escollit l'algorisme de força bruta\n");
                                int index = 0;
                                configuracioInv = new ConfDivInv(new ArrayList<Sabata>(), new ArrayList<Sabata>(), 0, 0);
                                configuracioOptima = new ConfDivInv(new ArrayList<Sabata>(), new ArrayList<Sabata>(), 0, 0);

                                divisioInventariForcaBruta(sabatesArray, index, configuracioInv, configuracioOptima);
                                mostrarDadesInventari(configuracioOptima);

                                break;
                            case 2:
                                System.out.print("Has escollit l'algorisme greedy\n");
                                divisioInventariGreedy(sabatesArray);
                                break;
                            case 3:
                                System.out.print("Has escollit l'algorisme de greedy 2\n");
                                System.out.println("Tria el numero d'inventaris");
                                int numInvetaris = sc.nextInt();
                                List<Sabata> sabatesList = Arrays.asList(sabatesArray);
                                List<List<Sabata>> inventarios = divisioInventariGreedy2(sabatesList, numInvetaris);

                                for (int i = 0; i < inventarios.size(); i++) {
                                    System.out.println("Inventario " + (i + 1) + ": " + calcularCosto(inventarios.get(i)));
                                }

                                break;
                            case 4:
                                break;
                            default:
                                System.out.print("Opció incorrecta\n");
                                break;
                        }
                    }
                    break;
                default:
                    System.out.print("Opció incorrecta\n");
                    break;
            }
        }

    }


}
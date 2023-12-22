import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static int nIteracions;
    static int nSabatesFitxer = 0;
    static int cajastotales = 1;

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
                        false,
                        0
                );

                sabatesArray[index] = sabata;
                index++;
            }
            myReader.close();
            return sabatesArray;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
    private static void CalcularDescomptes(ArrayList<Caixa> configuracio) {
        ArrayList<TipusDescompte> tipusDescomptes = new ArrayList<>();


        //Descompte 20% marca duplicada
        for (int i = 0; i<configuracio.size(); i++){
            ArrayList<TipusDescompte> tipusDescomptesNens = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntInf = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntSup = new ArrayList<>();

                for(int j = 0; j<configuracio.get(i).getSabates().size(); j++) {


                    for (int k = 0; k < configuracio.get(i).getSabates().size(); k++) {

                        if (configuracio.get(i).getSabates().get(j).getNom().equals(configuracio.get(i).getSabates().get(k).getNom()) && j != k && configuracio.get(i).getSabates().get(j).getDescompte() == 0) {

                            configuracio.get(i).getSabates().get(j).setDescompte(configuracio.get(i).getSabates().get(j).getDescompte() + (configuracio.get(i).getSabates().get(j).getPreu() * 0.2f));

                        }
                    }
                    //Descompte 35% sabates nens
                    if (configuracio.get(i).getSabates().get(j).getMax_talla() < 35 ) {
                        tipusDescomptesNens.add(new TipusDescompte(i, j, 1));
                    }

                    if (configuracio.get(i).getSabates().get(j).getPuntuacio() < 5) {
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
                    configuracio.get(j).getSabates().get(n).setDescompte(configuracio.get(j).getSabates().get(n).getDescompte() + (configuracio.get(j).getSabates().get(n).getPreu() * 0.35f));
                    break;

                case 2:
                    configuracio.get(j).getSabates().get(n).setDescompte(configuracio.get(j).getSabates().get(n).getDescompte() + (configuracio.get(j).getSabates().get(n).getPreu() * 0.4f));
                    break;

                case 3:
                    configuracio.get(j).getSabates().get(n).setDescompte(configuracio.get(j).getSabates().get(n).getDescompte() - (configuracio.get(j).getSabates().get(n).getPreu() * 0.2f));
                    break;
            }

        }
    }


    public static void enviamentCaixesForcaBruta(Sabata[] sabatesArray, int ordre, ArrayList<Caixa> configuracio) {
        if (ordre == sabatesArray.length) {

            CalcularDescomptes(configuracio);
            calcularpreuCaixa(sabatesArray,configuracio);

            return;
        }
        for (int i = 0; i < configuracio.size(); i++) {
            if (configuracio.get(i).getSabates().size() < 6) {
                configuracio.get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;
                enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
                configuracio.get(i).getSabates().remove(sabatesArray[ordre]);
            }
        }

        Caixa nuevaCaixa = new Caixa(0, 0);
        nuevaCaixa.setSabates(sabatesArray[ordre]);
        configuracio.add(nuevaCaixa);
        nIteracions++;
        enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
        configuracio.remove(configuracio.size() - 1);
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
        if(configuraciooptima.size() == 0 && !preuexces){
            copiaconfiguracio(configuracio);
        }
        if(configuracio.size() < configuraciooptima.size() && !preuexces){
            configuraciooptima.clear();
            copiaconfiguracio(configuracio);
        }
        if(configuracio.size() == sabatesArray.length){
            System.out.println(configuracio.size());
            mostrarDades(configuraciooptima);
        }
        for(int i = 0;i<configuracio.size();i++){
            configuracio.get(i).setPreu(0);
            for(int j = 0;j<configuracio.get(i).getSabates().size();j++){
                configuracio.get(i).getSabates().get(j).setDescompte(0);
            }
        }
    }

    private static void copiaconfiguracio(ArrayList<Caixa> configuracio) {
        Caixa nuevaCaixa;
        Sabata nuevaSabata;
        for (int i = 0; i < configuracio.size(); i++) {
            nuevaCaixa = new Caixa(0, configuracio.get(i).getPreu());
            for (int j = 0; j < configuracio.get(i).getSabates().size(); j++) {
                nuevaSabata = new Sabata(configuracio.get(i).getSabates().get(j).getNom(), configuracio.get(i).getSabates().get(j).getPreu(), configuracio.get(i).getSabates().get(j).getMin_talla(), configuracio.get(i).getSabates().get(j).getMax_talla(), configuracio.get(i).getSabates().get(j).getPes(), configuracio.get(i).getSabates().get(j).getPuntuacio(), configuracio.get(i).getSabates().get(j).isUtilitzat(), configuracio.get(i).getSabates().get(j).getDescompte());
                nuevaCaixa.setSabates(nuevaSabata);
            }
            configuraciooptima.add(nuevaCaixa);
        }
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

    private static void enviamentCaixesBacktracking(Sabata[] sabatesArray, int ordre, float totalpreu, Sabata[] configuracio, int sabataNensIndex) {
        /*
        if (ordre == 6 || sabataNensIndex == sabatesNens.size()) {
            totalpreu = CalcularDescomptes(totalpreu, configuracio);
            mostrarDades( configuracio, totalpreu, ordre);

            if (nSabatesContar != nSabatesFitxer) {
                configuracio = new Sabata[6];
                enviamentCaixesBacktracking(sabatesArray, 0, 0, configuracio, 0); //Empezar desde el inicio
            }


            return;
        }

        if (sabataNensIndex != sabatesNens.size()){
            totalpreu += sabatesArray[sabatesNens.get(sabataNensIndex)].getPreu();
            configuracio[ordre] = sabatesArray[sabatesNens.get(sabataNensIndex)];
            nSabatesContar++;
            nIteracions++;
            sabataNensIndex++;
            enviamentCaixesBacktracking(sabatesArray, ordre + 1, totalpreu, configuracio, sabataNensIndex);
        }
        */

    }

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
    }

    private static void divisioInventariGreedy(Sabata[] sabatesArray) {
        List<Sabata> sabatesList = Arrays.asList(sabatesArray);

        sabatesList.sort(Comparator.comparingDouble(Sabata::getPreu).reversed());  // Ordenar de mayor a menor precio
        /*
        System.out.println("Sabates ordenades: ");
        for (Sabata sabata : sabatesList) {
            System.out.println(sabata.getNom() + " " + sabata.getPreu());
        }
         */
        ArrayList<Sabata> inventari1 = new ArrayList<>();
        ArrayList<Sabata> inventari2 = new ArrayList<>();
        int costInvetari1 = 0;
        int costInvetari2 = 0;

        // Asignar los zapatos a los inventarios de acuerdo con la estrategia codiciosa
        for (Sabata sabata : sabatesArray) {
            if (costInvetari1 <= costInvetari2) {
                inventari1.add(sabata);
                costInvetari1 += sabata.getPreu();
            } else {
                inventari2.add(sabata);
                costInvetari2 += sabata.getPreu();
            }
        }

        // Imprimir los inventarios
        System.out.println("Inventari 1: " + costInvetari1);
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
            ArrayList<Caixa> configuraciooptima = new ArrayList<Caixa>();
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
                                //System.out.println("Numero random: " + contadorRandom);
                                nIteracions = 0;
                                cajastotales = 0;

                                break;
                            case 2:
                                System.out.print("Has escollit l'algorisme de backtracking\n");
                                break;
                            case 3:
                                System.out.print("Has escollit l'algorisme de programació dinàmica\n");
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
                                divisioInventariForcaBruta(sabatesArray);
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
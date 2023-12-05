import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static int nIteracions;
    static int nSabatesContar = 0;
    static int nSabatesFitxer = 0;
    static int cajastotales = 0;
    static int flag = 0;
    static int contadorRandom = 0;


    public static void mostrarDades(Sabata[] sabatesArray){
        for (int i = 0; i<sabatesArray.length; i++) {
            System.out.println("Nom: "+sabatesArray[i].getNom());
            System.out.print("preu: ");
            System.out.println(sabatesArray[i].getPreu());
            System.out.println("\nmin talla: "+sabatesArray[i].getMin_talla());
            System.out.println("max talla: "+sabatesArray[i].getMax_talla());
            System.out.println("pes: "+sabatesArray[i].getPes());
            System.out.print("punctuacio: ");
            System.out.println(sabatesArray[i].getPuntuacio());
            System.out.println("\n");
        }
    }

    public static Sabata[] lecturaFitxer(){
        try {
            File myObj = new File("Datasets/datasetL.txt");
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

    private static void mostrarDades(Sabata configuracio[], float totalpreu){
        cajastotales++;
        System.out.println("--------------------------------------");
        for (int i = 0; i < 6; i++) {
            System.out.println(configuracio[i].getNom());
            System.out.println(configuracio[i].getPreu());
        }
        System.out.println("Total precio: " + totalpreu);
        System.out.println("--------------------------------------");
    }

    private static float CalcularDescomptes(float totalpreu, Sabata[] configuracio) {
        ArrayList<Integer> sabatesNens = new ArrayList<Integer>();
        ArrayList<Integer> sabatesPuntInferior = new ArrayList<Integer>();
        ArrayList<Integer> sabatesPuntSuperior = new ArrayList<Integer>();

        //Descompte 20% marca duplicada
        for (int i = 0; i<configuracio.length; i++){

            for(int j = 0; j<configuracio.length; j++){
                if (configuracio[i].getNom().equals(configuracio[j].getNom()) && i != j){
                    float preuSabata = configuracio[i].getPreu();
                    float descompte = preuSabata * 0.2f;

                    //System.out.println("Descompte de: " + descompte + " a la sabata: " + configuracio[i].getNom());
                    System.out.println("Antes TotalPreu"+totalpreu);
                    totalpreu -= descompte;
                    System.out.println("Despues TotalPreu"+totalpreu);

                }
            }

            //System.out.println("Sabata "+ configuracio[i].getNom() + configuracio[i].getPuntuacio());
            if (configuracio[i].getMax_talla() < 35 ){
                sabatesNens.add(i);
            }

            if (configuracio[i].getPuntuacio() < 5){
                sabatesPuntInferior.add(i);
            }

            if (configuracio[i].getPuntuacio() > 8){
                sabatesPuntSuperior.add(i);
            }
        }

        //Descompte 35% sabates nens
        if (sabatesNens.size() > 1){
            float descompte = 0;
            for (int i = 0; i<sabatesNens.size(); i++){
                    descompte += configuracio[sabatesNens.get(i)].getPreu() * 0.35f;
                    //System.out.println("Descompte nens de: " + descompte + " a la sabata: " + configuracio[sabatesNens.get(i)].getNom());
            }
            System.out.println("Antes nens TotalPreu"+totalpreu);
            totalpreu -= descompte;
            System.out.println("Despues nens TotalPreu"+totalpreu);
        }

        //3 sabates puntuacio menor a 5 40%
        if (sabatesPuntInferior.size() > 2){
            System.out.println("Sabates punt inferior: " + sabatesPuntInferior.size());
            float descompte = 0;

            for (int i = 0; i<sabatesPuntInferior.size(); i++){
                    descompte += configuracio[sabatesPuntInferior.get(i)].getPreu() * 0.4f;
                    //System.out.println("Descompte puntuacio inferior  de: " + descompte + " a la sabata: " + configuracio[sabatesPuntInferior.get(i)].getNom());
            }
            System.out.println("Antes puntuacio inf TotalPreu"+totalpreu);
            totalpreu -= descompte;
            System.out.println("Despues puntuacio inf TotalPreu"+totalpreu);
        }

        //3 sabates puntuacio major a 8 20%
        if (sabatesPuntSuperior.size() > 2){
            float descompte = 0;

            for (int i = 0; i<sabatesPuntSuperior.size(); i++){
                    descompte += configuracio[sabatesPuntSuperior.get(i)].getPreu() * 0.2f;
                    System.out.println("Descompte puntuacio superior de: " + descompte + " a la sabata: " + configuracio[sabatesPuntSuperior.get(i)].getNom());
            }
            System.out.println("Antes puntuacio sup TotalPreu"+totalpreu);
            totalpreu -= descompte;
            System.out.println("Despues puntuacio sup TotalPreu"+totalpreu);
        }
        return totalpreu;
    }

    //Enviament caixes algoritmes
    public static void enviamentCaixesForcaBruta(Sabata[] sabatesArray, int ordre, float totalpreu, ArrayList<Sabata> configuracio, ArrayList<Caixa> caixes) {

        if (ordre == 6) {
            //totalpreu = CalcularDescomptes(totalpreu, configuracio);
            if (totalpreu > 1000){
                return;
            }
            cajastotales++;
            caixes.add(new Caixa(0, totalpreu, configuracio.toArray(new Sabata[configuracio.size()])));
            return;
        }

        for (int i = 0; i < sabatesArray.length; i++) {
            totalpreu += sabatesArray[i].getPreu();
            configuracio.add(sabatesArray[i]);

            nIteracions++;
            enviamentCaixesForcaBruta(sabatesArray, ordre + 1, totalpreu, configuracio, caixes);
            totalpreu -= sabatesArray[i].getPreu();
            configuracio.remove(sabatesArray[i]);
        }
    }

    //Divisio inventari Algoritmes:
    private static void divisioInventariForcaBruta(Sabata[] sabatesArray) {

        ArrayList<Sabata> inventari1 = new ArrayList<Sabata>();
        ArrayList<Sabata> inventari2 = new ArrayList<Sabata>();

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

        Collections.sort(sabatesList, Comparator.comparingDouble(Sabata::getPreu).reversed());  // Ordenar de mayor a menor precio
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
        Collections.sort(sabatesList, Comparator.comparingDouble(Sabata::getPreu).reversed());

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
            System.out.printf("Benvingut a la sabateria zaballos\n");
            System.out.printf("---- Escolliu un problema a resoldre ----\n");
            System.out.printf("1. Enviament de caixes\n");
            System.out.printf("2. Divisió d'inventari\n");

            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            int option2 = 0, option3 = 0;
            int ordre = 0;
            int totalpreu = 0;

            //classificarSabates(sabatesArray);
            switch (option){
                case 1:
                    while(option2 != 4){
                        System.out.printf("---- Escolliu un algorisme ----\n");
                        System.out.printf("1. Força bruta\n");
                        System.out.printf("2. Backtracking\n");
                        System.out.printf("3. Programació dinàmica\n");
                        System.out.printf("4. Tornar\n");

                        option2 = sc.nextInt();

                        switch (option2){
                            case 1:
                                System.out.printf("Has escollit l'algorisme de força bruta\n");

                                ArrayList<Sabata> configuracio = new ArrayList<Sabata>();
                                ArrayList<Caixa> caixes = new ArrayList<Caixa>();

                                enviamentCaixesForcaBruta(sabatesArray, 0, 0, configuracio, caixes);

                                //System.out.println("Numero iteracions: " + nIteracions);
                                System.out.println("Numero cajas: " + cajastotales);
                                //System.out.println("Numero random: " + contadorRandom);
                                nIteracions = 0;
                                cajastotales = 0;

                                break;
                            case 2:
                                System.out.printf("Has escollit l'algorisme de backtracking\n");
                                break;
                            case 3:
                                System.out.printf("Has escollit l'algorisme de programació dinàmica\n");
                                break;
                            case 4:
                                break;
                            default:
                                System.out.printf("Opció incorrecta\n");
                                break;
                        }
                    }
                case 2:
                    System.out.printf("Has escollit l'opció 2\n");
                    while(option3 != 4){
                        System.out.printf("---- Escolliu un algorisme ----\n");
                        System.out.printf("1. Força bruta\n");
                        System.out.printf("2. Greedy\n");
                        System.out.printf("3. Greedy2\n");
                        System.out.printf("4. Tornar\n");

                        option3 = sc.nextInt();

                        switch (option3){
                            case 1:
                                System.out.printf("Has escollit l'algorisme de força bruta\n");
                                divisioInventariForcaBruta(sabatesArray);
                                break;
                            case 2:
                                System.out.printf("Has escollit l'algorisme greedy\n");
                                divisioInventariGreedy(sabatesArray);
                                break;
                            case 3:
                                System.out.printf("Has escollit l'algorisme de greedy 2\n");
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
                                System.out.printf("Opció incorrecta\n");
                                break;
                        }
                    }
                    break;
                default:
                    System.out.printf("Opció incorrecta\n");
                    break;
            }
        }

    }




}
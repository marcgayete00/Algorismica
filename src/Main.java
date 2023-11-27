import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static int nIteracions;

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
            File myObj = new File("sabates.txt");
            Scanner myReader = new Scanner(myObj);

            int Nsabates = Integer.parseInt(myReader.nextLine());

            Sabata[] sabatesArray = new Sabata[Nsabates];
            int index = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(";");

                Sabata sabata = new Sabata(
                        parts[0],
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

                        System.out.println("Descompte de: " + descompte + " a la sabata: " + configuracio[i].getNom());
                        System.out.println("Antes TotalPreu"+totalpreu);
                        totalpreu -= descompte;
                        System.out.println("Despues TotalPreu"+totalpreu);

                    }
                }
                if (configuracio[i].getMax_talla() < 35){
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
                    if (sabatesNens.get(i) != -1){
                        descompte += configuracio[sabatesNens.get(i)].getPreu() * 0.35f;
                        System.out.println("Descompte nens de: " + descompte + " a la sabata: " + configuracio[sabatesNens.get(i)].getNom());

                    }
                }
                System.out.println("Antes nens TotalPreu"+totalpreu);
                totalpreu -= descompte;
                System.out.println("Despues nens TotalPreu"+totalpreu);
            }

            //3 sabates puntuacio menor a 5 40%
            if (sabatesPuntInferior.size() > 2){
                float descompte = 0;

                for (int i = 0; i<sabatesPuntInferior.size(); i++){
                    if (sabatesPuntInferior.get(i) != -1){
                        descompte += configuracio[sabatesPuntInferior.get(i)].getPreu() * 0.4f;
                        System.out.println("Descompte puntuacio de: " + descompte + " a la sabata: " + configuracio[sabatesPuntInferior.get(i)].getNom());
                    }

                }
                System.out.println("Antes puntuacio TotalPreu"+totalpreu);
                totalpreu -= descompte;
                System.out.println("Despues puntuacio TotalPreu"+totalpreu);
            }

            //3 sabates puntuacio major a 8 20%
            if (sabatesPuntSuperior.size() > 2){
                float descompte = 0;

                for (int i = 0; i<sabatesPuntSuperior.size(); i++){
                    if (sabatesPuntSuperior.get(i) != -1){
                        descompte += configuracio[sabatesPuntSuperior.get(i)].getPreu() * 0.2f;
                        System.out.println("Descompte puntuacio de: " + descompte + " a la sabata: " + configuracio[sabatesPuntSuperior.get(i)].getNom());
                    }

                }
                System.out.println("Antes puntuacio TotalPreu"+totalpreu);
                totalpreu -= descompte;
                System.out.println("Despues puntuacio TotalPreu"+totalpreu);
            }
    return totalpreu;
    }

    private static float CalcularDescomptesBacktracking(float totalpreu, Sabata[] configuracio) {
        ArrayList<Integer> sabatesNens = new ArrayList<Integer>();
        ArrayList<Integer> sabatesPuntInferior = new ArrayList<Integer>();
        ArrayList<Integer> sabatesPuntSuperior = new ArrayList<Integer>();
        //Descompte 20% marca duplicada
        for (int i = 0; i<configuracio.length; i++){
            if(configuracio[i] != null) {
                for (int j = 0; j < configuracio.length; j++) {
                    if (configuracio[j] != null && configuracio[i].getNom().equals(configuracio[j].getNom()) && i != j && !configuracio[i].getDescomptat() && !configuracio[j].getDescomptat()) {
                        float preuSabata = configuracio[i].getPreu();
                        float descompte = preuSabata * 0.2f;
                        configuracio[i].setDescomptat(true);
                        configuracio[j].setDescomptat(true);
                        System.out.println("Descompte de: " + descompte + " a la sabata: " + configuracio[i].getNom());
                        System.out.println("Antes TotalPreu" + totalpreu);
                        totalpreu -= descompte;
                        System.out.println("Despues TotalPreu" + totalpreu);
                    }
                    /*if (configuracio[i].getMax_talla() < 35) {
                        sabatesNens.add(i);
                    }

                    if (configuracio[i].getPuntuacio() < 5) {
                        sabatesPuntInferior.add(i);
                    }

                    if (configuracio[i].getPuntuacio() > 8) {
                        sabatesPuntSuperior.add(i);
                    }*/
                }
            }
        }
        /*
        //Descompte 35% sabates nens
        if (sabatesNens.size() > 1){
            float descompte = 0;
            for (int i = 0; i<sabatesNens.size(); i++){
                if (sabatesNens.get(i) != -1){
                    descompte += configuracio[sabatesNens.get(i)].getPreu() * 0.35f;
                    System.out.println("Descompte nens de: " + descompte + " a la sabata: " + configuracio[sabatesNens.get(i)].getNom());

                }
            }
            System.out.println("Antes nens TotalPreu"+totalpreu);
            totalpreu -= descompte;
            System.out.println("Despues nens TotalPreu"+totalpreu);
        }

        //3 sabates puntuacio menor a 5 40%
        if (sabatesPuntInferior.size() > 2){
            float descompte = 0;

            for (int i = 0; i<sabatesPuntInferior.size(); i++){
                if (sabatesPuntInferior.get(i) != -1){
                    descompte += configuracio[sabatesPuntInferior.get(i)].getPreu() * 0.4f;
                    System.out.println("Descompte puntuacio de: " + descompte + " a la sabata: " + configuracio[sabatesPuntInferior.get(i)].getNom());
                }

            }
            System.out.println("Antes puntuacio TotalPreu"+totalpreu);
            totalpreu -= descompte;
            System.out.println("Despues puntuacio TotalPreu"+totalpreu);
        }

        //3 sabates puntuacio major a 8 20%
        if (sabatesPuntSuperior.size() > 2){
            float descompte = 0;

            for (int i = 0; i<sabatesPuntSuperior.size(); i++){
                if (sabatesPuntSuperior.get(i) != -1){
                    descompte += configuracio[sabatesPuntSuperior.get(i)].getPreu() * 0.2f;
                    System.out.println("Descompte puntuacio de: " + descompte + " a la sabata: " + configuracio[sabatesPuntSuperior.get(i)].getNom());
                }

            }
            System.out.println("Antes puntuacio TotalPreu"+totalpreu);
            totalpreu -= descompte;
            System.out.println("Despues puntuacio TotalPreu"+totalpreu);
        }*/
        return totalpreu;

    }
    public static void enviamentCaixesForcaBruta(Sabata[] sabatesArray, int ordre, float totalpreu, Sabata[] configuracio) {
        if (ordre == 6) {

            totalpreu = CalcularDescomptes(totalpreu, configuracio);
            if (totalpreu > 1000){
                return;
            }

            System.out.println("--------------------------------------");
            for (int i = 0; i < 6; i++) {
                System.out.println(configuracio[i].getNom());
                System.out.println(configuracio[i].getPreu());
            }
            System.out.println("Total precio: " + totalpreu);
            System.out.println("--------------------------------------");
            return;
        }
        for (int i = 0; i < sabatesArray.length; i++) {
            if (!sabatesArray[i].getUtilitzat()) {

                totalpreu += sabatesArray[i].getPreu();
                configuracio[ordre] = sabatesArray[i];

                sabatesArray[i].setUtilitzat(true);

                nIteracions++;
                enviamentCaixesForcaBruta(sabatesArray, ordre + 1, totalpreu, configuracio);
                totalpreu -= sabatesArray[i].getPreu();
                sabatesArray[i].setUtilitzat(false);
            }
        }
    }

    private static void enviamentCaixesBacktracking(Sabata[] sabatesArray, int ordre, float totalpreu, Sabata[] configuracio) {

        if (ordre == 6) {
            System.out.println("--------------------------------------");
            for (int i = 0; i < 6; i++) {
                System.out.println(configuracio[i].getNom());
                System.out.println(configuracio[i].getPreu());
            }
            System.out.println("Total precio: " + totalpreu);
            System.out.println("--------------------------------------");
            return;
        }

        for (int i = 0; i < sabatesArray.length; i++) {
            if (!sabatesArray[i].getUtilitzat()) {
                totalpreu += sabatesArray[i].getPreu();
                configuracio[ordre] = sabatesArray[i];
                totalpreu = CalcularDescomptesBacktracking(totalpreu, configuracio);
                if(totalpreu >= 1000){
                    return;
                }
                sabatesArray[i].setUtilitzat(true);
                nIteracions++;
                enviamentCaixesBacktracking(sabatesArray, ordre + 1, totalpreu, configuracio);
                totalpreu -= sabatesArray[i].getPreu();
                sabatesArray[i].setUtilitzat(false);
            }
        }
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
            int option2 = 0;
            int ordre = 0;
            int totalpreu = 0;
            Sabata[] configuracio = new Sabata[6];
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

                                configuracio = new Sabata[6];

                                enviamentCaixesForcaBruta(sabatesArray, 0, 0, configuracio);
                                System.out.println("Numero iteracions: " + nIteracions);
                                nIteracions = 0;

                                break;
                            case 2:
                                System.out.printf("Has escollit l'algorisme de backtracking\n");
                                configuracio = new Sabata[6];
                                enviamentCaixesBacktracking(sabatesArray, 0, 0, configuracio);
                                System.out.println("Numero iteracions: " + nIteracions);
                                nIteracions = 0;
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
                    break;
                default:
                    System.out.printf("Opció incorrecta\n");
                    break;
            }
        }

    }


}
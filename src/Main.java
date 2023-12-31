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

    static Configuracio configuraciooptima = new Configuracio(new ArrayList<Caixa>(),0);

    public static Sabata[] lecturaFitxer(){
        try {
            File myObj = new File("Datasets/datasetXXS.txt");
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
            return sabatesArray;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    private static void mostrarDades(Configuracio configuracio){
        cajastotales = 0;
        for(int i= 0;i<configuracio.getCaixes().size();i++){
            System.out.println("Caja "+i);
            cajastotales++;
            for(int j = 0; j<configuracio.getCaixes().get(i).getSabates().size();j++){
                System.out.print("Sabata "+configuracio.getCaixes().get(i).getSabates().get(j).getNom() + " ");
                System.out.println(configuracio.getCaixes().get(i).getSabates().get(j).getPreu() + " | " +configuracio.getCaixes().get(i).getSabates().get(j).getDescompte());
            }
            System.out.println("Preu "+configuracio.getCaixes().get(i).getPreu());
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

    private static void CalcularDescomptes(Configuracio configuracio) {
        ArrayList<TipusDescompte> tipusDescomptes = new ArrayList<>();


        //Descompte 20% marca duplicada
        for (int i = 0; i<configuracio.getCaixes().size(); i++){
            ArrayList<TipusDescompte> tipusDescomptesNens = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntInf = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntSup = new ArrayList<>();

                for(int j = 0; j<configuracio.getCaixes().get(i).getSabates().size(); j++) {


                    for (int k = 0; k < configuracio.getCaixes().get(i).getSabates().size(); k++) {

                        if (configuracio.getCaixes().get(i).getSabates().get(j).getNom().equals(configuracio.getCaixes().get(i).getSabates().get(k).getNom()) && j != k && configuracio.getCaixes().get(i).getSabates().get(j).getDescompte() == 0) {
                            if (!configuracio.getCaixes().get(i).getSabates().get(j).isDescompteDuplicat()) {
                                configuracio.getCaixes().get(i).getSabates().get(j).setDescompte(configuracio.getCaixes().get(i).getSabates().get(j).getDescompte() + (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() * 0.2f));
                                configuracio.getCaixes().get(i).getSabates().get(j).setDescompteDuplicat(true);
                                configuracio.getCaixes().get(i).setDescompteDuplicat(true);
                            }

                        }
                    }
                    //Descompte 35% sabates nens
                    if (configuracio.getCaixes().get(i).getSabates().get(j).getMax_talla() < 35 ) {
                        tipusDescomptesNens.add(new TipusDescompte(i, j, 1));
                    }

                    if (configuracio.getCaixes().get(i).getSabates().get(j).getPuntuacio() < 5) {
                        tipusDescomptesPuntInf.add(new TipusDescompte(i, j, 2));
                    }

                    if (configuracio.getCaixes().get(i).getSabates().get(j).getPuntuacio() > 8) {
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
                    if (!configuracio.getCaixes().get(j).getSabates().get(n).isDescompteNens()) {
                        configuracio.getCaixes().get(j).getSabates().get(n).setDescompte(configuracio.getCaixes().get(j).getSabates().get(n).getDescompte() + (configuracio.getCaixes().get(j).getSabates().get(n).getPreu() * 0.35f));
                        configuracio.getCaixes().get(j).getSabates().get(n).setDescompteNens(true);
                        configuracio.getCaixes().get(j).setDescompteNens(true);
                    }
                    break;

                case 2:
                    if (!configuracio.getCaixes().get(j).getSabates().get(n).isDescomptePI()) {
                        configuracio.getCaixes().get(j).getSabates().get(n).setDescompte(configuracio.getCaixes().get(j).getSabates().get(n).getDescompte() + (configuracio.getCaixes().get(j).getSabates().get(n).getPreu() * 0.4f));
                        configuracio.getCaixes().get(j).getSabates().get(n).setDescomptePI(true);
                        configuracio.getCaixes().get(j).setDescomptePI(true);
                    }
                    break;

                case 3:
                    if (!configuracio.getCaixes().get(j).getSabates().get(n).isIncrementPS()) {
                        configuracio.getCaixes().get(j).getSabates().get(n).setDescompte(configuracio.getCaixes().get(j).getSabates().get(n).getDescompte() - (configuracio.getCaixes().get(j).getSabates().get(n).getPreu() * 0.2f));
                        configuracio.getCaixes().get(j).getSabates().get(n).setIncrementPS(true);
                        configuracio.getCaixes().get(j).setIncrementPS(true);
                    }
                    break;
            }
        }
    }
    private static void calcularpreuCaixa(Sabata[] sabatesArray,Configuracio configuracio) {
        boolean preuexces = false;
        for(int i = 0;i<configuracio.getCaixes().size();i++){
            for(int j = 0;j<configuracio.getCaixes().get(i).getSabates().size();j++){
                configuracio.getCaixes().get(i).setPreu(configuracio.getCaixes().get(i).getPreu() + (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() - configuracio.getCaixes().get(i).getSabates().get(j).getDescompte()));
            }
        }
        for(int i = 0;i<configuracio.getCaixes().size();i++){
            if (configuracio.getCaixes().get(i).getPreu()  > 1000) {
                preuexces = true;
                break;
            }
        }
        if(configuraciooptima.getCaixes().size() == 0 && !preuexces){
            copiaconfiguracio(configuracio);
        }
        if(configuracio.getCaixes().size() < configuraciooptima.getCaixes().size() && !preuexces){
            configuraciooptima.getCaixes().clear();
            copiaconfiguracio(configuracio);

        }
        //mostrarDades(configuracio);
        if(configuracio.getCaixes().size() == sabatesArray.length){
            System.out.println(configuracio.getCaixes().size());
            mostrarDades(configuraciooptima);
        }
    }

    private static boolean calcularpreuCaixaBacktracking(Sabata[] sabatesArray,Configuracio configuracio) {
        for(int i = 0;i<configuracio.getCaixes().size();i++){
            for(int j = 0;j<configuracio.getCaixes().get(i).getSabates().size();j++){
                configuracio.getCaixes().get(i).setPreu(configuracio.getCaixes().get(i).getPreu() + (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() - configuracio.getCaixes().get(i).getSabates().get(j).getDescompte()));
            }
            //mostrarDades(configuracio);

            /*if (configuracio.get(i).getPreu() > 1000){
                return true;
            }*/

            if (configuracio.getCaixes().get(i).isDescompteDuplicat() && configuracio.getCaixes().get(i).isDescompteNens() && configuracio.getCaixes().get(i).isDescomptePI() && configuracio.getCaixes().get(i).getPreu() > 1000) {
                //mostrarDades(configuracio);
                //System.out.println("Descartada: " + configuracio.get(i).getPreu());
                return true;
            }else if((configuracio.getCaixes().get(i).getPreu() > 1000) && configuracio.getCaixes().get(i).getSabates().size() >= 4){
                //mostrarDades(configuracio);
                //System.out.println("Descartada2: " + configuracio.get(i).getPreu());
                return true;
            }



        }
        return false;
    }

    private static void copiaconfiguracio(Configuracio configuracio) {
        Caixa nuevaCaixa;
        Sabata nuevaSabata;
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            nuevaCaixa = new Caixa(0, configuracio.getCaixes().get(i).getPreu());
            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                nuevaSabata = new Sabata(configuracio.getCaixes().get(i).getSabates().get(j).getNom(),
                                         configuracio.getCaixes().get(i).getSabates().get(j).getPreu(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getMin_talla(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getMax_talla(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getPes(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getPuntuacio(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getDescompte(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isDescompteDuplicat(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isDescompteNens(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isIncrementPS(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isDescomptePI());
                nuevaCaixa.setSabates(nuevaSabata);
            }
            configuraciooptima.afegirCaixa(nuevaCaixa);
        }
    }

    private static void ReseteigDades(Configuracio configuracio) {
        for(int i = 0;i<configuracio.getCaixes().size();i++){
            for(int j = 0;j<configuracio.getCaixes().get(i).getSabates().size();j++){
                configuracio.getCaixes().get(i).getSabates().get(j).setDescompte(0);
                configuracio.getCaixes().get(i).getSabates().get(j).setDescompteDuplicat(false);
                configuracio.getCaixes().get(i).getSabates().get(j).setDescompteNens(false);
                configuracio.getCaixes().get(i).getSabates().get(j).setDescomptePI(false);
                configuracio.getCaixes().get(i).getSabates().get(j).setIncrementPS(false);
            }
            configuracio.getCaixes().get(i).setPreu(0);
            configuracio.getCaixes().get(i).setDescompteDuplicat(false);
            configuracio.getCaixes().get(i).setDescompteNens(false);
            configuracio.getCaixes().get(i).setDescomptePI(false);
            configuracio.getCaixes().get(i).setIncrementPS(false);
        }
    }

    public static void enviamentCaixesForcaBruta(Sabata[] sabatesArray, int ordre, Configuracio configuracio) {
        if (ordre == sabatesArray.length) {
            CalcularDescomptes(configuracio);
            calcularpreuCaixa(sabatesArray,configuracio);
            //mostrarDades(configuracio);
            ReseteigDades(configuracio);
            return;
        }
        // Bucle para manejar el resto de las cajas
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            if (configuracio.getCaixes().get(i).getSabates().size() < 6){
                configuracio.getCaixes().get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;
                enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
                configuracio.getCaixes().get(i).getSabates().remove(sabatesArray[ordre]);
            }
        }
        Caixa nuevaCaixa = new Caixa(0, 0);
        nuevaCaixa.setSabates(sabatesArray[ordre]);
        //nuevaCaixa.setPreu(sabatesArray[ordre].getPreu());
        configuracio.afegirCaixa(nuevaCaixa);
        nIteracions++;
        System.out.println(nIteracions);
        enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
        configuracio.eliminarCaixa(configuracio.getCaixes().size() - 1);
    }

    //NO SE PUEDE HACER DE FORMA PRECISA UNA PODA, COMENTARLO EN LA MEMORIA
    private static void enviamentCaixesBacktracking(Sabata[] sabatesArray, int ordre, Configuracio configuracio) {

        if (ordre == sabatesArray.length) {
            //ReseteigDades(configuracio);
            CalcularDescomptes(configuracio);
            if(!calcularpreuCaixaBacktracking(sabatesArray,configuracio)){
                comprovarSolucio(configuracio, sabatesArray);
            }
            ReseteigDades(configuracio);
            return;
        }
        // Bucle para manejar el resto de las cajas
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            if (configuracio.getCaixes().get(i).getSabates().size() < 6){
                configuracio.getCaixes().get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;
                enviamentCaixesBacktracking(sabatesArray, ordre + 1, configuracio);
                configuracio.getCaixes().get(i).getSabates().remove(sabatesArray[ordre]);
            }

        }

        Caixa nuevaCaixa = new Caixa(0, 0);
        nuevaCaixa.setSabates(sabatesArray[ordre]);
        //nuevaCaixa.setPreu(sabatesArray[ordre].getPreu());
        configuracio.afegirCaixa(nuevaCaixa);
        CalcularDescomptes(configuracio);
        if (calcularpreuCaixaBacktracking(sabatesArray,configuracio)){
            ReseteigDades(configuracio);
            configuracio.eliminarCaixa(configuracio.getCaixes().size() - 1);
            return;
        }
        nIteracions++;
        System.out.println(nIteracions);
        enviamentCaixesBacktracking(sabatesArray, ordre + 1, configuracio);
        configuracio.eliminarCaixa(configuracio.getCaixes().size() - 1);
    }

    private static Configuracio enviamentCaixesBranchAndBound(Sabata[] sabatesArray, Configuracio configuracio, CuaPrioritat cua) {
        cua.afegir(configuracio,0);
        ArrayList<Configuracio> llistaelements;
        int indiceprioritario;
        float minim = 4;
        Configuracio configuracioactual = new Configuracio(new ArrayList<>(),0);
        while(!cua.isEmpty()){
            indiceprioritario = cua.treurelementprioritari();
            copiaconfiguracioBB(indiceprioritario, configuracioactual);  //configuracioActual tiene los datos de la configuracion con mayor prioridad
            cua.eliminarelementprioritari(indiceprioritario);
            //mostrarDades(configuracioactual);
            llistaelements = expandir(configuracioactual,sabatesArray); //Crear los hijos de esa configuracion

            /*for (int i = 0; i < llistaelements.size(); i++) {
                System.out.println("-----------------------");
                for(int j = 0;j<llistaelements.get(i).getCaixes().size();j++){
                    System.out.println("Caja "+j);
                    for (int k = 0; k < llistaelements.get(i).getCaixes().get(j).getSabates().size(); k++) {
                        System.out.print("Sabata "+llistaelements.get(i).getCaixes().get(j).getSabates().get(k).getNom() + " ");
                        System.out.println(llistaelements.get(i).getCaixes().get(j).getSabates().get(k).getPreu() + " | " +llistaelements.get(i).getCaixes().get(j).getSabates().get(k).getDescompte());
                    }
                }
            }*/

            for(int i = 0;i<llistaelements.size();i++){
                if(configuracioComplerta(llistaelements.get(i),sabatesArray)){
                    if(llistaelements.get(i).getCaixes().size() < minim){
                        copiaconfiguracio(llistaelements.get(i));
                        minim = llistaelements.get(i).getCaixes().size();
                    }
                }else{
                    if(estimacio(llistaelements.get(i),sabatesArray) < minim){
                        cua.afegir(llistaelements.get(i),estimacio(llistaelements.get(i),sabatesArray));
                    }
                }
            }
            System.out.println("Tamaño de la cola: " + cua.getCua().size());
            llistaelements.clear();
        }
        return configuracioactual;
    }

    private static int estimacio(Configuracio configuracionActual, Sabata[] sabatas) {
        int zapatosRestantes = 0;

        // Calcula la cantidad total de zapatos que quedan por asignar
        for (Sabata sabata : sabatas) {
            if (!configuracionActual.caixes.contains(sabata)) {
                zapatosRestantes++;
            }
        }
        // Calcula la cantidad de cajas necesarias para los zapatos restantes
        int cajasNecesarias = zapatosRestantes / 6;
        System.out.println("Estimacion: " + configuracionActual.getCaixes().size() + cajasNecesarias);
        // Estimación: Cantidad actual de cajas más cajas necesarias para los zapatos restantes
        return configuracionActual.getCaixes().size() + cajasNecesarias;
    }

    private static boolean configuracioComplerta(Configuracio configuracio, Sabata[] sabatesArray) {
        int contador = 0;
        for(int i = 0;i<configuracio.getCaixes().size();i++){
            contador += configuracio.getCaixes().get(i).getSabates().size();
        }
        if(contador == sabatesArray.length){
            return true;
        }
        return false;
    }

    private static Configuracio copiaconfiguracioBB(int indiceprioritario, Configuracio configuracio) {
        configuracio.getCaixes().clear();
        Caixa nuevaCaixa;
        Sabata nuevaSabata;
        //System.out.println("Indice prioritario: " + indiceprioritario);
        //System.out.println("Tamaño de la cola: " + CuaPrioritat.getCua().size());
        //System.out.println("Tamaño de la cola2: " + CuaPrioritat.getCua().get(indiceprioritario).getCaixes().size());
        for (int i = 0; i < CuaPrioritat.getCua().get(indiceprioritario).getCaixes().size(); i++) {
            nuevaCaixa = new Caixa(0, CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getPreu());
            for (int j = 0; j < CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().size(); j++) {
                nuevaSabata = new Sabata(CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getNom(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getPreu(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getMin_talla(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getMax_talla(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getPes(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getPuntuacio(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).getDescompte(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).isDescompteDuplicat(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).isDescompteNens(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).isIncrementPS(),
                        CuaPrioritat.getCua().get(indiceprioritario).getCaixes().get(i).getSabates().get(j).isDescomptePI());
                nuevaCaixa.setSabates(nuevaSabata);
            }
            configuracio.afegirCaixa(nuevaCaixa);
        }
        return configuracio;
    }

    private static ArrayList<Configuracio> expandir(Configuracio configuracioActual, Sabata[] sabatesArray) {
        ArrayList<Configuracio> llistaelements = new ArrayList<>();
        Configuracio novaconfiguracio = copiaNovaConfiguracio(configuracioActual);
        //System.out.println("Cajas Nova: " + novaconfiguracio.getCaixes().size());
        for (int i = 0; i < configuracioActual.getCaixes().size(); i++) {
            System.out.println("Caja Nova: " + i);

            for (int j = 0; j < sabatesArray.length; j++) {
                if (!estaEnCajas(sabatesArray[j], configuracioActual)) {
                    if (configuracioActual.getCaixes().get(i).getSabates().size() < 6) {
                        novaconfiguracio.getCaixes().get(i).setSabates(sabatesArray[j]);
                        //mostrarDades(novaconfiguracio);
                        llistaelements.add(novaconfiguracio);
                    } else {
                        Caixa nuevaCaixa = new Caixa(0, 0);
                        novaconfiguracio.afegirCaixa(nuevaCaixa);
                        //mostrarDades(novaconfiguracio);
                        llistaelements.add(novaconfiguracio);
                        break;
                    }
                }
            }
        }
        return llistaelements;
    }

    private static Configuracio copiaNovaConfiguracio(Configuracio configuracioActual) {
        Configuracio novaconfiguracio = new Configuracio(new ArrayList<>(), 0);
        Caixa nuevaCaixa;
        Sabata nuevaSabata;
        for (int i = 0; i < configuracioActual.getCaixes().size(); i++) {
            nuevaCaixa = new Caixa(0, configuracioActual.getCaixes().get(i).getPreu());
            for (int j = 0; j < configuracioActual.getCaixes().get(i).getSabates().size(); j++) {
                nuevaSabata = new Sabata(configuracioActual.getCaixes().get(i).getSabates().get(j).getNom(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getPreu(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getMin_talla(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getMax_talla(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getPes(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getPuntuacio(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getDescompte(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isDescompteDuplicat(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isDescompteNens(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isIncrementPS(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isDescomptePI());
                nuevaCaixa.setSabates(nuevaSabata);
            }
            novaconfiguracio.afegirCaixa(nuevaCaixa);
        }
        return novaconfiguracio;
    }

    // Verifica si un zapato está presente en alguna caja de la configuración
    private static boolean estaEnCajas(Sabata sabata, Configuracio configuracio) {
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                if (configuracio.getCaixes().get(i).getSabates().get(j).equals(sabata)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void comprovarSolucio(Configuracio configuracio, Sabata[] sabatesArray) {
        if(configuraciooptima.getCaixes().size() == 0){
            copiaconfiguracio(configuracio);
        }
        if(configuracio.getCaixes().size() < configuraciooptima.getCaixes().size()){
            configuraciooptima.getCaixes().clear();
            copiaconfiguracio(configuracio);
        }

        if(configuracio.getCaixes().size() == sabatesArray.length){
            System.out.println(configuracio.getCaixes().size());
            mostrarDades(configuraciooptima);
        }
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
            Configuracio configuracio = new Configuracio(new ArrayList<Caixa>(), 0);

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

                                configuracio = new Configuracio(new ArrayList<Caixa>(), 0);
                                configuracio.getCaixes().add(new Caixa(0, 0));
                                configuracio.getCaixes().get(0).setSabates(sabatesArray[0]);
                                //configuracio.get(0).setPreu(sabatesArray[0].getPreu());
                                enviamentCaixesForcaBruta(sabatesArray, 1, configuracio);
                                //System.out.println("Numero iteracions: " + nIteracions);
                                System.out.println("La configuración con menos cajas tiene: " + cajastotales + " cajas");
                                System.out.println("Numero iteracions: " + nIteracions);
                                nIteracions = 0;
                                cajastotales = 0;

                                break;
                            case 2:
                                System.out.print("Has escollit l'algorisme de backtracking\n");
                                configuracio = new Configuracio(new ArrayList<Caixa>(), 0);
                                configuracio.getCaixes().add(new Caixa(0, 0));
                                configuracio.getCaixes().get(0).setSabates(sabatesArray[0]);
                                //configuracio.get(0).setPreu(sabatesArray[0].getPreu());
                                enviamentCaixesBacktracking(sabatesArray, 1, configuracio);
                                //System.out.println("Numero iteracions: " + nIteracions);
                                System.out.println("La configuración con menos cajas tiene: " + cajastotales + " cajas");
                                System.out.println("Numero iteracions: " + nIteracions);
                                nIteracions = 0;
                                cajastotales = 0;

                                break;
                            case 3:
                                System.out.print("Has escollit l'algorisme de branch and bound\n");
                                configuracio = new Configuracio(new ArrayList<Caixa>(), 0);
                                configuracio.getCaixes().add(new Caixa(0, 0));
                                configuracio.getCaixes().get(0).setSabates(sabatesArray[0]);
                                //configuracio.get(0).setPreu(sabatesArray[0].getPreu());
                                CuaPrioritat cua = new CuaPrioritat(configuracio);

                                enviamentCaixesBranchAndBound(sabatesArray, configuracio,cua);
                                mostrarDades(configuraciooptima);
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
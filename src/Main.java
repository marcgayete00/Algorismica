import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static int nIteracions;
    static int nSabatesFitxer = 0;
    static int caixesTotals = 1;
    static boolean esPrimera = false;
    static Configuracio configuracioOptima = new Configuracio(new ArrayList<Caixa>(),0);

    public static Sabata[] lecturaFitxer() {
        try {
            File elMeuObjecte = new File("Datasets/datasetS.txt");
            Scanner elMeuLector = new Scanner(elMeuObjecte);

            nSabatesFitxer = Integer.parseInt(elMeuLector.nextLine());

            Sabata[] sabatesArray = new Sabata[nSabatesFitxer];
            int index = 0;

            while (elMeuLector.hasNextLine()) {
                String data = elMeuLector.nextLine();
                String[] parts = data.split(";");
                String[] parts2 = parts[0].split(" ");
                Sabata sabata = new Sabata(
                        parts2[0],
                        parts2[0] + " " + parts2[1],
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
            elMeuLector.close();
            return sabatesArray;

        } catch (FileNotFoundException e) {
            System.out.println("Hi ha hagut un error");
            e.printStackTrace();
        }

        return null;
    }

    private static void mostrarDades(Configuracio configuracio) {
        caixesTotals = 0;

        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            System.out.println("Caixa " + i);
            caixesTotals++;

            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                System.out.print("Sabata " + configuracio.getCaixes().get(i).getSabates().get(j).getNom() + " ");
                System.out.println(configuracio.getCaixes().get(i).getSabates().get(j).getPreu() + " | " + configuracio.getCaixes().get(i).getSabates().get(j).getDescompte());
            }
            System.out.println("Preu " + configuracio.getCaixes().get(i).getPreu());
        }
        System.out.println("----------------------");
    }

    private static void mostrarDadesInventari(ConfDivInv configuracioInv) {
        System.out.println("----------------------");
        System.out.println("Inventari 1: ");
        for (int i = 0; i < configuracioInv.getInventari1().size(); i++) {
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

    private static void calcularDescomptes(Configuracio configuracio) {
        ArrayList<TipusDescompte> tipusDescomptes = new ArrayList<>();

        //Descompte 20% marca duplicada
        for (int i = 0; i<configuracio.getCaixes().size(); i++){
            ArrayList<TipusDescompte> tipusDescomptesNens = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntInf = new ArrayList<>();
            ArrayList<TipusDescompte> tipusDescomptesPuntSup = new ArrayList<>();

            for(int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                for (int k = 0; k < configuracio.getCaixes().get(i).getSabates().size(); k++) {
                    if (configuracio.getCaixes().get(i).getSabates().get(j).getNom().equals(
                            configuracio.getCaixes().get(i).getSabates().get(k).getNom()) && j != k &&
                            configuracio.getCaixes().get(i).getSabates().get(j).getDescompte() == 0) {
                        if (!configuracio.getCaixes().get(i).getSabates().get(j).isDescompteDuplicat()) {
                            configuracio.getCaixes().get(i).getSabates().get(j).setDescompte(
                                    configuracio.getCaixes().get(i).getSabates().get(j).getDescompte() +
                                            (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() * 0.2f));
                            configuracio.getCaixes().get(i).getSabates().get(j).setDescompteDuplicat(true);
                            configuracio.getCaixes().get(i).setDescompteDuplicat(true);
                        }
                    }
                }

                //Descompte 35% sabates nens
                if (configuracio.getCaixes().get(i).getSabates().get(j).getMaxTalla() < 35 ) {
                    tipusDescomptesNens.add(new TipusDescompte(i, j, 1));
                }

                if (configuracio.getCaixes().get(i).getSabates().get(j).getPuntuacio() < 5) {
                    tipusDescomptesPuntInf.add(new TipusDescompte(i, j, 2));
                }

                if (configuracio.getCaixes().get(i).getSabates().get(j).getPuntuacio() > 8) {
                    tipusDescomptesPuntSup.add(new TipusDescompte(i, j, 3));
                }
            }

            if (tipusDescomptesNens.size() >= 2) {
                for (int j = 0; j < tipusDescomptesNens.size(); j++) {
                    tipusDescomptes.add(tipusDescomptesNens.get(j));
                }
            }

            if (tipusDescomptesPuntInf.size() >= 3) {
                for (int j = 0; j < tipusDescomptesPuntInf.size(); j++) {
                    tipusDescomptes.add(tipusDescomptesPuntInf.get(j));
                }
            }

            if (tipusDescomptesPuntSup.size() >= 3) {
                for (int j = 0; j < tipusDescomptesPuntSup.size(); j++) {
                    tipusDescomptes.add(tipusDescomptesPuntSup.get(j));
                }
            }
        }

        //Descompte 35% sabates nens
        for (int i = 0; i < tipusDescomptes.size(); i++) {
            TipusDescompte tipusDescompte = tipusDescomptes.get(i);
            int j = tipusDescompte.getCaixa();
            int n = tipusDescompte.getSabata();
            int m = tipusDescompte.getTipusDescompte();

            switch (m) {
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
                    }
                    break;
            }
        }
    }

    private static void calcularPreuCaixa(Sabata[] sabatesArray, Configuracio configuracio) {
        boolean preuExces = false;

        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                configuracio.getCaixes().get(i).setPreu(configuracio.getCaixes().get(i).getPreu() + (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() - configuracio.getCaixes().get(i).getSabates().get(j).getDescompte()));
            }
        }

        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            if (configuracio.getCaixes().get(i).getPreu()  > 1000) {
                preuExces = true;
                break;
            }
        }

        if (configuracioOptima.getCaixes().size() == 0 && !preuExces) {
            copiaConfiguracio(configuracio);
        }

        if (configuracio.getCaixes().size() < configuracioOptima.getCaixes().size() && !preuExces) {
            configuracioOptima.getCaixes().clear();
            copiaConfiguracio(configuracio);
        }

        if (configuracio.getCaixes().size() == sabatesArray.length){
            System.out.println(configuracio.getCaixes().size());
            mostrarDades(configuracioOptima);
        }
    }

    private static boolean calcularPreuCaixaBacktracking(Configuracio configuracio) {
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            for (int j = 0; j<configuracio.getCaixes().get(i).getSabates().size(); j++) {
                configuracio.getCaixes().get(i).setPreu(configuracio.getCaixes().get(i).getPreu() + (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() - configuracio.getCaixes().get(i).getSabates().get(j).getDescompte()));
            }

            if (configuracio.getCaixes().get(i).isDescompteDuplicat() && configuracio.getCaixes().get(i).isDescompteNens() && configuracio.getCaixes().get(i).isDescomptePI() && configuracio.getCaixes().get(i).getPreu() > 1000) {
                return true;
            }
        }

        return false;
    }

    //COMENTAR CON 4 HACE 55000 ITERACIONES Y CON 3 HACE 27374
    private static boolean calcularPreuCaixaBB(Configuracio configuracio) {
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                configuracio.getCaixes().get(i).setPreu(configuracio.getCaixes().get(i).getPreu() + (configuracio.getCaixes().get(i).getSabates().get(j).getPreu() - configuracio.getCaixes().get(i).getSabates().get(j).getDescompte()));
            }

            if (configuracio.getCaixes().get(i).isDescompteDuplicat() && configuracio.getCaixes().get(i).isDescompteNens() && configuracio.getCaixes().get(i).isDescomptePI() && configuracio.getCaixes().get(i).getPreu() > 1000) {
                return true;
            } else if((configuracio.getCaixes().get(i).getPreu() > 1000) && configuracio.getCaixes().get(i).getSabates().size() >= 3) {
                return true;
            }
        }

        return false;
    }

    private static void copiaConfiguracio(Configuracio configuracio) {
        Caixa novaCaixa;
        Sabata novaSabata;

        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            novaCaixa = new Caixa(configuracio.getCaixes().get(i).getPreu());

            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                novaSabata = new Sabata(configuracio.getCaixes().get(i).getSabates().get(j).getNom(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getNomComplet(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getPreu(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getMinTalla(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getMaxTalla(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getPes(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getPuntuacio(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).getDescompte(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isDescompteDuplicat(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isDescompteNens(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isIncrementPS(),
                                        configuracio.getCaixes().get(i).getSabates().get(j).isDescomptePI());
                novaCaixa.setSabates(novaSabata);
            }
            configuracioOptima.afegirCaixa(novaCaixa);
        }
    }

    private static void reseteigDades(Configuracio configuracio) {
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
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
        }
    }

    public static void enviamentCaixesForcaBruta(Sabata[] sabatesArray, int ordre, Configuracio configuracio) {
        if (ordre == sabatesArray.length) {
            calcularDescomptes(configuracio);
            calcularPreuCaixa(sabatesArray,configuracio);
            reseteigDades(configuracio);
            return;
        }

        // Bucle para gestionar la resta de les caixes
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            if (configuracio.getCaixes().get(i).getSabates().size() < 6) {
                configuracio.getCaixes().get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;
                enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
                configuracio.getCaixes().get(i).getSabates().remove(sabatesArray[ordre]);
            }
        }

        Caixa novaCaixa = new Caixa(0);
        novaCaixa.setSabates(sabatesArray[ordre]);
        configuracio.afegirCaixa(novaCaixa);
        nIteracions++;
        System.out.println(nIteracions);
        enviamentCaixesForcaBruta(sabatesArray, ordre + 1, configuracio);
        configuracio.eliminarCaixa(configuracio.getCaixes().size() - 1);
    }

    //NO ES POT FER UNA PODA DE FORMA PRECISA. S'HA DE COMENTAR A LA MEMÒRIA
    private static void enviamentCaixesBacktracking(Sabata[] sabatesArray, int ordre, Configuracio configuracio) {
        if (ordre == sabatesArray.length) {
            calcularDescomptes(configuracio);
            calcularPreuCaixa(sabatesArray,configuracio);
            reseteigDades(configuracio);
            return;
        }

        // Bucle per gestionar la resta de les caixes
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            if (configuracio.getCaixes().get(i).getSabates().size() < 6) {
                configuracio.getCaixes().get(i).setSabates(sabatesArray[ordre]);
                nIteracions++;
                enviamentCaixesBacktracking(sabatesArray, ordre + 1, configuracio);
                configuracio.getCaixes().get(i).getSabates().remove(sabatesArray[ordre]);
            }
        }

        Caixa novaCaixa = new Caixa(0);
        novaCaixa.setSabates(sabatesArray[ordre]);
        configuracio.afegirCaixa(novaCaixa);
        calcularDescomptes(configuracio);

        if (calcularPreuCaixaBacktracking(configuracio)) {
            reseteigDades(configuracio);
            configuracio.eliminarCaixa(configuracio.getCaixes().size() - 1);
            return;
        }

        reseteigDades(configuracio);
        nIteracions++;
        System.out.println(nIteracions);
        enviamentCaixesBacktracking(sabatesArray, ordre + 1, configuracio);
        configuracio.eliminarCaixa(configuracio.getCaixes().size() - 1);
    }

    private static Configuracio enviamentCaixesBranchAndBound(Sabata[] sabatesArray, Configuracio configuracio, CuaPrioritat cua) {
        cua.afegir(configuracio);
        ArrayList<Configuracio> llistaElements;
        int indexPrioritari;
        int minim = (int)Math.ceil(((double)sabatesArray.length / 6) + 2);
        Configuracio configuracioActual = new Configuracio(new ArrayList<>(), 0);

        while (!cua.isEmpty()) {
            nIteracions++;
            indexPrioritari = cua.treureElementPrioritari();
            copiaConfiguracioBB(indexPrioritari, configuracioActual);  //configuracioActual té les dades de la configuració amb major prioritat
            cua.eliminarElementPrioritari(indexPrioritari);
            llistaElements = expandir(configuracioActual, sabatesArray); //Crear els fills d'aquella configuració

            for (int i = 0; i < llistaElements.size(); i++) {
                if (configuracioCompleta(llistaElements.get(i),sabatesArray)) {
                    if (llistaElements.get(i).getCaixes().size() < minim) {
                        calcularDescomptes(llistaElements.get(i));

                        if (!calcularPreuCaixaBB(llistaElements.get(i))) {
                            copiaConfiguracio(llistaElements.get(i));
                            if (cua.comprovar(llistaElements.get(i).getCaixes().size())) {
                                cua.netejar();
                            }
                            minim = llistaElements.get(i).getCaixes().size();
                        }
                    }
                } else {
                    if (estimacio(llistaElements.get(i), sabatesArray) < minim) {
                        cua.afegir(llistaElements.get(i));
                    }
                }
            }

            System.out.println("Mida de la cua: " + cua.getCua().size());
            llistaElements.clear();
        }

        return configuracioActual;
    }

    private static int estimacio(Configuracio configuracioActual, Sabata[] sabates) {
        // Calcular descomptes i preus de les caixes
        calcularDescomptes(configuracioActual);

        // Verificar si el preu de les caixes supera el límit
        if (!calcularPreuCaixaBB(configuracioActual)) {
            reseteigDades(configuracioActual);

            // Contar zapatos restantes
            int sabatesRestants = 0;

            for (Sabata sabata : sabates) {
                if (!configuracioActual.caixes.contains(sabata)) {
                    sabatesRestants++;
                }
            }
            int caixesNecessaries = sabatesRestants / 6;

            if (configuracioActual.getCaixes().get(configuracioActual.getCaixes().size() - 1).getPreu() > 800 && configuracioActual.getCaixes().get(configuracioActual.getCaixes().size() - 1).getSabates().size() > 4) {
                caixesNecessaries++;
            }

            System.out.println("Sabates restants: " + sabatesRestants);
            System.out.println("Caixes necessàries: " + caixesNecessaries);
            System.out.println("Estimació: " + (configuracioActual.getCaixes().size() + caixesNecessaries));

            return (configuracioActual.getCaixes().size() + caixesNecessaries);
        } else {
            return 100000;
        }
    }

    private static boolean configuracioCompleta(Configuracio configuracio, Sabata[] sabatesArray) {
        int comptador = 0;

        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            comptador += configuracio.getCaixes().get(i).getSabates().size();
        }

        if (comptador == sabatesArray.length) {
            return true;
        }
        return false;
    }

    private static Configuracio copiaConfiguracioBB(int indexPrioritari, Configuracio configuracio) {
        configuracio.getCaixes().clear();
        Caixa novaCaixa;
        Sabata novaSabata;

        for (int i = 0; i < CuaPrioritat.getCua().get(indexPrioritari).getCaixes().size(); i++) {
            novaCaixa = new Caixa(CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getPreu());

            for (int j = 0; j < CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().size(); j++) {
                novaSabata = new Sabata(CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getNom(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getNomComplet(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getPreu(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getMinTalla(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getMaxTalla(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getPes(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getPuntuacio(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).getDescompte(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).isDescompteDuplicat(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).isDescompteNens(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).isIncrementPS(),
                        CuaPrioritat.getCua().get(indexPrioritari).getCaixes().get(i).getSabates().get(j).isDescomptePI());
                novaCaixa.setSabates(novaSabata);
            }

            configuracio.afegirCaixa(novaCaixa);
        }

        return configuracio;
    }

    private static ArrayList<Configuracio> expandir(Configuracio configuracioInicial, Sabata[] sabatesArray) {
        ArrayList<Configuracio> llistaElements = new ArrayList<>();
        int index = 0;

        if (configuracioInicial.getCaixes().size() != 0) {
            index = configuracioInicial.getCaixes().size() - 1;
        }

        for (int i = index; i < configuracioInicial.getCaixes().size(); i++) {
            for (int j = 0; j < sabatesArray.length; j++) {
                if (!esACaixes(sabatesArray[j], configuracioInicial)) {
                    if (configuracioInicial.getCaixes().get(i).getSabates().size() < 6) {
                        Configuracio novaconfiguracio = copiaNovaConfiguracio(configuracioInicial);
                        novaconfiguracio.getCaixes().get(i).setSabates(sabatesArray[j]);
                        calcularDescomptes(novaconfiguracio);
                        if (calcularPreuCaixaBB(novaconfiguracio)) {
                            novaconfiguracio.getCaixes().get(i).getSabates().remove(sabatesArray[j]);
                            Caixa novaCaixa = new Caixa(0);
                            novaCaixa.setSabates(sabatesArray[j]);
                            configuracioInicial.afegirCaixa(novaCaixa);
                            llistaElements.add(configuracioInicial);
                            break;
                        } else {
                            reseteigDades(novaconfiguracio);
                            llistaElements.add(novaconfiguracio);
                        }
                    } else {
                        Caixa novaCaixa = new Caixa(0);
                        novaCaixa.setSabates(sabatesArray[j]);
                        configuracioInicial.afegirCaixa(novaCaixa);
                        llistaElements.add(configuracioInicial);
                        break;
                    }
                }
            }
        }

        return llistaElements;
    }

    private static Configuracio copiaNovaConfiguracio(Configuracio configuracioActual) {
        Configuracio novaconfiguracio = new Configuracio(new ArrayList<>(), 0);
        Caixa novaCaixa;
        Sabata novaSabata;
        for (int i = 0; i < configuracioActual.getCaixes().size(); i++) {
            novaCaixa = new Caixa(configuracioActual.getCaixes().get(i).getPreu());

            for (int j = 0; j < configuracioActual.getCaixes().get(i).getSabates().size(); j++) {
                novaSabata = new Sabata(configuracioActual.getCaixes().get(i).getSabates().get(j).getNom(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getNomComplet(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getPreu(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getMinTalla(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getMaxTalla(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getPes(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getPuntuacio(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).getDescompte(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isDescompteDuplicat(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isDescompteNens(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isIncrementPS(),
                        configuracioActual.getCaixes().get(i).getSabates().get(j).isDescomptePI());
                novaCaixa.setSabates(novaSabata);
            }

            novaconfiguracio.afegirCaixa(novaCaixa);
        }

        return novaconfiguracio;
    }

    // Verifica si una sabata ja es present a alguna caixa de la configuració
    private static boolean esACaixes(Sabata sabata, Configuracio configuracio) {
        for (int i = 0; i < configuracio.getCaixes().size(); i++) {
            for (int j = 0; j < configuracio.getCaixes().get(i).getSabates().size(); j++) {
                if (configuracio.getCaixes().get(i).getSabates().get(j).getNomComplet().equals(sabata.getNomComplet())) {
                    return true;
                }
            }
        }

        return false;
    }

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
            double resultatOptim = configuracioOptima.getPreuInvetari1() - configuracioOptima.getPreuInvetari2();
            double resultatActual = configuracioInv.getPreuInvetari1() - configuracioInv.getPreuInvetari2();

            if (Math.abs(resultatActual) < Math.abs(resultatOptim)) {
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
        List<Sabata> sabatesLlista = Arrays.asList(sabatesArray);
        sabatesLlista.sort(Comparator.comparingDouble(Sabata::getPreu).reversed());  // Ordenar de major a menor preu

        ArrayList<Sabata> inventari1 = new ArrayList<>();
        ArrayList<Sabata> inventari2 = new ArrayList<>();
        int costInvetari1 = 0;
        int costInvetari2 = 0;

        for (int i = 0; i < sabatesArray.length; i++) {
            if (costInvetari1 <= costInvetari2) {
                inventari1.add(sabatesArray[i]);
                costInvetari1 += sabatesArray[i].getPreu();
            } else {
                inventari2.add(sabatesArray[i]);
                costInvetari2 += sabatesArray[i].getPreu();
            }
        }

        for (int i = 0; i < inventari1.size(); i++) {
            System.out.println("Sabata " + inventari1.get(i).getNom() + " " + inventari1.get(i).getPreu());
        }
        System.out.println("Inventari 1: " + costInvetari1);

        for (int i = 0; i < inventari2.size(); i++) {
            System.out.println("Sabata " + inventari2.get(i).getNom() + " " + inventari2.get(i).getPreu());
        }
        System.out.println("Inventari 2: " + costInvetari2);
    }

    private static List<List<Sabata>> divisioInventariGreedy2(List<Sabata> sabatesLlista, int numInvetaris){
        sabatesLlista.sort(Comparator.comparingDouble(Sabata::getPreu).reversed());

        List<List<Sabata>> inventaris = new ArrayList<>();
        for (int i = 0; i < numInvetaris; i++) {
            inventaris.add(new ArrayList<>());
        }

        for (Sabata sabata : sabatesLlista) {
            int indexInventari = obtindreMenorCostInventari(inventaris);
            inventaris.get(indexInventari).add(sabata);
        }

        return inventaris;
    }

    private static int obtindreMenorCostInventari(List<List<Sabata>> inventaris) {
        int indexInventari = 0;
        double menorCost = calcularCost(inventaris.get(0));

        for (int i = 1; i < inventaris.size(); i++) {
            double costInventari = calcularCost(inventaris.get(i));
            if (costInventari < menorCost) {
                menorCost = costInventari;
                indexInventari = i;
            }
        }

        return indexInventari;
    }

    private static double calcularCost(List<Sabata> sabates) {
        int cost = 0;

        for (Sabata sabata : sabates) {
            cost += sabata.getPreu();
        }

        return cost;
    }

    public static void main(String[] args) {
        Sabata[] sabatesArray = lecturaFitxer();

        while (true) {
            System.out.print("Benvingut a la sabateria zaballos\n");
            System.out.print("---- Escolliu un problema a resoldre ----\n");
            System.out.print("1. Enviament de caixes\n");
            System.out.print("2. Divisió d'inventari\n");

            Scanner escanejador = new Scanner(System.in);
            int opcio = escanejador.nextInt();
            int opcio2 = 0, opcio3 = 0;
            Configuracio configuracio = new Configuracio(new ArrayList<Caixa>(), 0);

            ConfDivInv configuracioInv = new ConfDivInv(new ArrayList<Sabata>(), new ArrayList<Sabata>(), 0, 0);
            ConfDivInv configuracioOptima = new ConfDivInv(new ArrayList<Sabata>(), new ArrayList<Sabata>(), 0, 0);

            switch (opcio) {
                case 1:
                    while (opcio2 != 4) {
                        System.out.print("---- Escolliu un algorisme ----\n");
                        System.out.print("1. Força bruta\n");
                        System.out.print("2. Backtracking\n");
                        System.out.print("3. Programació dinàmica\n");
                        System.out.print("4. Tornar\n");

                        opcio2 = escanejador.nextInt();

                        switch (opcio2) {
                            case 1:
                                System.out.print("Has escollit l'algorisme de força bruta\n");
                                configuracio = new Configuracio(new ArrayList<Caixa>(), 0);
                                configuracio.getCaixes().add(new Caixa(0));
                                configuracio.getCaixes().get(0).setSabates(sabatesArray[0]);
                                enviamentCaixesForcaBruta(sabatesArray, 1, configuracio);
                                Main.configuracioOptima = new Configuracio(new ArrayList<Caixa>(), 0);
                                System.out.println("La configuració amb menys caixes té: " + caixesTotals + " caixes");
                                System.out.println("Nombre d'iteracions: " + nIteracions);
                                nIteracions = 0;
                                caixesTotals = 0;
                                break;
                            case 2:
                                System.out.print("Has escollit l'algorisme de backtracking\n");
                                configuracio = new Configuracio(new ArrayList<Caixa>(), 0);
                                configuracio.getCaixes().add(new Caixa(0));
                                configuracio.getCaixes().get(0).setSabates(sabatesArray[0]);
                                enviamentCaixesBacktracking(sabatesArray, 1, configuracio);
                                Main.configuracioOptima = new Configuracio(new ArrayList<Caixa>(), 0);
                                System.out.println("La configuració amb menys caixes té: " + caixesTotals + " caixes");
                                System.out.println("Nombre d'iteracions: " + nIteracions);
                                nIteracions = 0;
                                caixesTotals = 0;
                                break;
                            case 3:
                                System.out.print("Has escollit l'algorisme de branch and bound\n");
                                configuracio = new Configuracio(new ArrayList<Caixa>(), 0);
                                configuracio.getCaixes().add(new Caixa(0));
                                configuracio.getCaixes().get(0).setSabates(sabatesArray[0]);
                                CuaPrioritat cua = new CuaPrioritat();
                                enviamentCaixesBranchAndBound(sabatesArray, configuracio, cua);
                                mostrarDades(Main.configuracioOptima);
                                Main.configuracioOptima = new Configuracio(new ArrayList<Caixa>(), 0);
                                System.out.println("La configuració amb menys caixes té: " + caixesTotals + " caixes");
                                System.out.println("Nombre d'iteracions: " + nIteracions);
                                nIteracions = 0;
                                caixesTotals = 0;
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

                    while (opcio3 != 4) {
                        System.out.print("---- Escolliu un algorisme ----\n");
                        System.out.print("1. Força bruta\n");
                        System.out.print("2. Greedy\n");
                        System.out.print("3. Greedy2\n");
                        System.out.print("4. Tornar\n");

                        opcio3 = escanejador.nextInt();

                        switch (opcio3) {
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
                                System.out.println("Tria el nombre d'inventaris");
                                int numInvetaris = escanejador.nextInt();
                                List<Sabata> sabatesLlista = Arrays.asList(sabatesArray);
                                List<List<Sabata>> inventaris = divisioInventariGreedy2(sabatesLlista, numInvetaris);

                                for (int i = 0; i < inventaris.size(); i++) {
                                    System.out.println("Inventari " + (i + 1) + ": " + calcularCost(inventaris.get(i)));
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
package MaximumFunctionFinding;

import java.util.*;

public class FunctionMaximumFinding {

    private static final int SIZE = 6;

    private static int a, b, c, d;
    private static double pk, pm;

    private static int n = 0;
    private static int globalTotalRoulette = 0;

    private static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static Random random = new Random();

    private static List<Integer> chr = new ArrayList<>();

    public static void main(String[] args){

        int iteration = 0;

        System.out.print("Wprowadz wartosci funkcji ax^3 + bx^2 + cx + d w kolejnosci (a, b, c, d): ");
        a = scanner.nextInt();
        b = scanner.nextInt();
        c = scanner.nextInt();
        d = scanner.nextInt();

        System.out.print("Wprowadz wspolczynnik krzyzowania Pk i mutacje Pm: (Pk, Pm): ");
        pk = scanner.nextDouble();
        pm = scanner.nextDouble();

        /*
            a = 0;
            b = 0;
            c = 2;
            d = 1;
            pk = 0.8;
            pm = 0.2;
        */

        for(int i = 0; i < SIZE; i++)
            chr.add(random.nextInt(32));

        System.out.println("Pula poczatkowych chromosomow: " + chr);


        while (true){
            selection();

            if(n == 10)
                break;

            crucifixion();
            mutation();

            iteration++;
        }

        System.out.println("Maksymalna wartosc funkcji w przedziale <0,31>: " + functionResult(chr.get(0)));
        System.out.println("Dla x: " + Integer.toBinaryString(chr.get(0)) + " " + chr.get(0));
        System.out.println("Liczba iteracji: " + iteration);

        chr.clear();
    }

    private static void selection(){
        int rouletteSelection = 0;
        double roulettePart = 0;
        int totalRoulette = 0;

        List<Integer> newChr = new ArrayList<>();
        List<Integer> chrResult = new ArrayList<>();
        List<Integer> chrProcentRoulette = new ArrayList<>();

        for(int i = 0; i < SIZE; i++){
            chrResult.add(functionResult(chr.get(i)));
            totalRoulette += chrResult.get(i);
        }

        if(globalTotalRoulette < totalRoulette){
            globalTotalRoulette = totalRoulette;
            n = 0;
        }else{
            if(globalTotalRoulette == totalRoulette)
                ++n;
            if(n == 10)
                return;
        }

        for(int i = 0; i < SIZE; i++){
            roulettePart += ((double) chrResult.get(i) / totalRoulette) * 100;
            chrProcentRoulette.add((int) Math.round(roulettePart));
        }

        for(int i = 0; i < SIZE; i++){
            rouletteSelection = random.nextInt(100)+1;

            if(rouletteSelection < chrProcentRoulette.get(0)){
                newChr.add(chr.get(0));
            }else if(rouletteSelection <= chrProcentRoulette.get(1)){
                newChr.add(chr.get(1));
            }else if(rouletteSelection <= chrProcentRoulette.get(2)){
                newChr.add(chr.get(2));
            }else if(rouletteSelection <= chrProcentRoulette.get(3)){
                newChr.add(chr.get(3));
            }else if(rouletteSelection <= chrProcentRoulette.get(4)) {
                newChr.add(chr.get(4));
            }else
                newChr.add(chr.get(5));

        }

        chr.clear();
        chr.addAll(newChr);
    }

    private static void crucifixion(){
        int placeOfCrossing = 0;
        double tempPk = 0;
        Set<Integer> setOfIndex = new TreeSet<>();

        for(int i = 0; i < SIZE; i++)
            setOfIndex.add(i);

        while (!setOfIndex.isEmpty()){
            int a = random.nextInt(6);
            int b = random.nextInt(6);
            if(setOfIndex.contains(a) && setOfIndex.contains(b) && a != b){
                setOfIndex.remove(a);
                setOfIndex.remove(b);

                tempPk = random.nextDouble();
                if(Double.compare(tempPk, pk) < 0 && !chr.get(a).equals(chr.get(b))){
                    //zamiana bitow
                    placeOfCrossing = random.nextInt(4) + 1;

                    String parent = Integer.toBinaryString(chr.get(a));
                    String child = Integer.toBinaryString(chr.get(b));

                    parent = String.format("%5s", parent).replaceAll(" ", "0");
                    child = String.format("%5s", child).replaceAll(" ", "0");

                    String parentResult = parent.substring(0, placeOfCrossing) + child.substring(placeOfCrossing, child.length());
                    String childResult = child.substring(0, placeOfCrossing) + parent.substring(placeOfCrossing, parent.length());

                    chr.set(a, Integer.parseInt(parentResult, 2));
                    chr.set(b, Integer.parseInt(childResult, 2));
                }
            }
        }
    }

    private static void mutation(){
        int tempX = 0;
        double tempPm = 0;
        int placeOfCrossover = 0;

        for(int i = 0; i < SIZE; i++){
            tempPm = random.nextDouble();
            if(Double.compare(tempPm, pm) < 0){
                tempX = 32;
                placeOfCrossover = random.nextInt(5)+1;
                tempX >>= placeOfCrossover;
                int result = chr.get(i) ^ tempX;
                chr.set(i, result);
            }
        }
    }

    private static int functionResult(int x){
        return (int) Math.round( (a * Math.pow(x,3)) + (b*Math.pow(x, 2)) + (c*x) + d);
    }
}

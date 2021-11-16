package dijkstra;

import java.util.*;

/* Lukasz Sakwa 6537 */

public class Dijkstra {

    //Mapa wszystkich wierzcholkow
    private static Map<Character, Point> pointsMap;
    //Set wierzcholkow ktorych algorytm jeszcze nie odwiedzil
    private static Set<Character> setOfAvailablePoints;
    //Klasa umozliwiajaca uzytkownikowi wpisywanie wspolrzednych wierzcholkow
    private static Scanner scanner;

    public static void main(String[] args){

        //index wierzcholka startowego
        char mainIndex = ' ';
        //index wierzcholka
        char z = ' ';
        //zmienne (x, y) wierzcholka
        int x = -1;
        int y = -1;
        //inicjalizacja potrzebnych zmiennych
        scanner = new Scanner(System.in);
        pointsMap = new HashMap<>();
        setOfAvailablePoints = new TreeSet<>();

        //Przypisywanie wierzcholka startowego
        System.out.print("Start point ('Index', x, y) ex. B 4 5: ");
        mainIndex = z = scanner.next().charAt(0);
        x = scanner.nextInt();
        y = scanner.nextInt();
        pointsMap.put(z, new Point(x, y));
        setOfAvailablePoints.add(z);

        //Przypisywanie wszystkich wierzcholkow
        System.out.println("Rest of points ('Index', x, y) ex. A 2 3: ");
        for(int i = 1; i < 5; i++){
            z = scanner.next().charAt(0);
            x = scanner.nextInt();
            y = scanner.nextInt();
            pointsMap.put(z, new Point(x, y));
            setOfAvailablePoints.add(z);
        }

        //przypisywanie do zmiennej, indexu wierzcholka startowego
        char neighIndex = mainIndex;
        //zmienna zawierajaca koncowa sume odleglosci pomiedzy wierzcholkami
        double fullRoute = 0;
        //zmienne pomocnicze
        char index = ' ';
        double neighRoute = 0;

        //Petla bedzie sie wykonywac do momentu,
        //kiedy algorytm przejdzie po wszystkich wierzcholkach
        while (!setOfAvailablePoints.isEmpty()){
            //wybor wierzcholka dla ktorego algorytm bedzie szukac najblizszego sasiada
            //i usuwanie go z setu wierzcholkow po ktorych algorytm jeszcze nie przeszedl
            Point mainPoint = pointsMap.get(neighIndex);
            setOfAvailablePoints.remove(neighIndex);

            //wybieranie najblizszego sasiada opierajac sie na:
            //-> poprzednim wierzcholku
            //-> pozostalych dostepnych wierzcholkach
            //-> najkrotszej odleglosci
            Map.Entry<Character, Point> p = pointsMap.entrySet().stream()
                    .filter(characterPointEntry -> setOfAvailablePoints.contains(characterPointEntry.getKey()))
                    .min(Comparator.comparingDouble(c -> c.getValue().routeLength(mainPoint)))
                    .orElse(null);

            //walidacja potrzebna do poprawnego dzialania algorytmu
            if(p != null){
                neighRoute = p.getValue().routeLength(mainPoint);
                index = p.getKey();
            }else{
                neighRoute = pointsMap.get(neighIndex).routeLength(mainPoint);
            }

            System.out.print(neighIndex + " -> ");

            //dodajemy najkrotsza trase do zmiennej sumujacej wszystkie odleglosci po ktorych przeszedl algorytm
            //zamieniamy sasiada na wierzcholek dla ktorego bedziemy szukac sasiadow
            fullRoute += neighRoute;
            neighIndex = index;

        }

        //Obliczanie odleglosci pomiedzy wierzcholkiem koncowym, a wierzcholkiem startowym
        neighRoute = pointsMap.get(mainIndex).routeLength(pointsMap.get(neighIndex));
        System.out.print(mainIndex);
        fullRoute += neighRoute;

        System.out.println("\n"+ Math.round(fullRoute * 100.0) / 100.0);
    }
}
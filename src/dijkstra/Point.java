package dijkstra;

//klasa odzwierciedlajaca wierzcholek w ukladzie wspolrzednych (x,y)
public class Point{
    private Integer x;
    private Integer y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double routeLength(Point point){
        double result = Math.pow((point.x - this.x), 2) + Math.pow((point.y - this.y), 2);
        return Math.sqrt(result);
    }
}

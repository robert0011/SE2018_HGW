import java.util.Hashtable;

public class MyPair {

    private final Hashtable<Integer, Dijkstravertex> visitedHash;
    private final Hashtable<Integer, Dijkstravertex> unvisitedHash;

    public static MyPair getPair(Hashtable<Integer, Dijkstravertex> visitedHash, Hashtable<Integer, Dijkstravertex> unvisitedHash) {
        return new MyPair(visitedHash, unvisitedHash);
    }

    public MyPair(Hashtable<Integer, Dijkstravertex> visitedHash, Hashtable<Integer, Dijkstravertex> unvisitedHash) {
        this.unvisitedHash = unvisitedHash;
        this.visitedHash = visitedHash;
    }

    public Hashtable<Integer, Dijkstravertex> getVisitedHash() {
        return visitedHash;
    }

    public Hashtable<Integer, Dijkstravertex> getUnvisited() {
        return unvisitedHash;
    }

}
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by brian on 3/4/15
 *
 * Creates random graph of Vertex and applies Flood algorithm on it.
 */
public class RandomTest {
    public static void main(String[] args) {
        Graph graphified = new Graph();

        // RNG
        Random RNJesus = new Random();

        //Create random amount of vertex with random value for state and add them to graph
        Integer constrict = 10000;
        Integer NumOfVertex = Math.abs(RNJesus.nextInt(constrict));
        int MaxElementsInRow = 10;

        System.out.println(NumOfVertex);
        for (Integer i = 0; i < NumOfVertex; i++) {
            Point2D.Float coordinate = new Point2D.Float((float)((i%MaxElementsInRow)*30), (float)(i/MaxElementsInRow));
            Vertex vertex = new Vertex(i.toString(), Math.abs(RNJesus.nextInt(constrict)), coordinate);
            graphified.addVertex(vertex);
        }
        // Now we randomly connect the vertices...

        for (Integer i = 0; i < Math.abs(RNJesus.nextInt(constrict)); i++) {
            String firstVertex = ((Integer)(Math.abs(RNJesus.nextInt(NumOfVertex)))).toString();
            String secondVertex = ((Integer)(Math.abs(RNJesus.nextInt(NumOfVertex)))).toString();
            graphified.connectVertices(firstVertex, secondVertex);
        }

        System.out.println("Initial Graph:\n"+graphified.toString());
        ForcingSet i = new ForcingSet();
        Graph finished = i.FloodVertex(graphified);
        System.out.println("Finished Graph:\n"+finished.toString());
        new GUI();
    }
}
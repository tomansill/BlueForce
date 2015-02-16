import java.util.ArrayList;
public class test_Vertex{
	public static void main(String[] args){
		System.out.println("== Test Constructors ==");
		System.out.println("Constructing Vertex A");
		ArrayList<String> neighbors = new ArrayList<String>();
		neighbors.add("B");
		neighbors.add("C");
		neighbors.add("D");
		Vertex vertexA = new Vertex("A", neighbors); 
		System.out.println("First Constructor successful,\n"
						+ "created Vertex A with default state 0,\n"
						+ "Coordinate is default at 0,0,0\n"
						+ "Vertex A has neighbors of B,C,D\n");

		System.out.println("Constructing Vertex B");
		neighbors = new ArrayList<String>();
		neighbors.add("A");
		neighbors.add("C");
		Vertex vertexB = new Vertex("B", (short)2, neighbors); 
		System.out.println("Second Constructor successful,\n"
						+ "created Vertex B with state 2,\n"
						+ "Coordinate is default at 0,0,0\n"
						+ "Vertex B has neighbors of A and C\n");

		System.out.println("Constructing Vertex C");
		neighbors = new ArrayList<String>();
		neighbors.add("A");
		neighbors.add("B");
		neighbors.add("D");
		int[] coords = {1, 3, 0};
		Vertex vertexC = new Vertex("C", neighbors, coords); 
		System.out.println("Third Constructor successful,\n"
						+ "created Vertex B with default state 0,\n"
						+ "Coordinate are 1, 3, 0\n"
						+ "Vertex C has neighbors of A, B and D\n");

		System.out.println("Constructing Vertex D");
		neighbors = new ArrayList<String>();
		neighbors.add("A");
		neighbors.add("C");
		coords[0] = 1;
		coords[1] = -3;
		coords[2] = 0;
		Vertex vertexD = new Vertex("D", (short)1, neighbors, coords); 
		System.out.println("Fourth Constructor successful,\n"
						+ "created Vertex B with state 1,\n"
						+ "Coordinate are 1, -3, 0\n"
						+ "Vertex D has neighbors of A and C\n");
		System.out.println("\n== Test Accessors ==");
	}//End of main function 
}//End of class

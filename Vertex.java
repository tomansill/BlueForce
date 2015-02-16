/**
 * @author Thomas B. Ansill
 * @author Brian T. Podlisny
 * @date February 16, 2015	
 * University: Rochester Institute of Technology
 * 			
 *			This program is to be used only for research purposes
 */

import java.util.ArrayList;

public class Vertex{
	/** Value for vertex label */
	private String label;
	/** Value for vertex's neighbors list */
	private ArrayList<String> neighbors;
	/** Coordinate point for a given vertex */
	private int[] coordinate = {0, 0, 0};
	/** Value for vertex's state */
	private short state = 0;

	/** Parametrized Constructor for Vertex Object
	 * @param label Vertex Label
	 * @param neighbors List of vertex's neighbors
	 */
	public Vertex(String label, ArrayList<String> neighbors){
		this.label = label;
		this.neighbors = neighbors;
	}//End of constructor

	/** Parametrized Constructor for Vertex Object
	 * @param label Vertex Label
	 * @param state Vertex's state
	 * @param neighbors List of vertex's neighbors
	 */
	public Vertex(String label, short state, ArrayList<String> neighbors){
		this.label = label;
		this.state = state;
		this.neighbors = neighbors;
	}//End of constructor

	/** Parametrized Constructor for Vertex Object
	 * @param label Vertex Label
	 * @param neighbors List of vertex's neighbors
	 * @param coordinate coordinate of vertex
	 */
	public Vertex(String label, ArrayList<String> neighbors, int[] coordinate){
		this.label = label;
		this.neighbors = neighbors;
		this.coordinate = coordinate;		

	}//End of constructor

	/** Parametrized Constructor for Vertex Object
	 * @param label Vertex Label
	 * @param state Vertex's state
	 * @param neighbors List of vertex's neighbors
	 * @param coordinate coordinate of vertex
	 */
	public Vertex(String label, short state, ArrayList<String> neighbors, int[] coordinate){
		this.label = label;
		this.state = state;
		this.neighbors = neighbors;
		this.coordinate = coordinate;
	}//End of constructor
	
	/** Accessor for vertex's label
     * @return label
	 */
	public String getLabel(){return label;}
	/** Accessor for vertex's list of neighbors
     * @return list of neighbors
	 */
	public ArrayList<String> getNeighbors(){return neighbors;}
	/** Acessor for vertex's coordinate
	* @return coordinate
	*/
	public int[] getCoordinate(){ return coordinate;}
	/** Accessor for vertex's filled status
     * @return Vertex's state
	 */
	public short getState(){return this.state;}

	/** Hashing Method - Not sure if necessary anymore */
	public int hashCode(){return label.hashCode();}

	/** forces the vertex to a specific number
	 * @param state state of force 
 	 */
	public void force(short state){ this.state = state; }

	/** Forces the vertex */
	public void force(){ this.state += 1; }

	/** Reverse of force() */
	public void unforce(){ this.state -= 1; }

	/** Zeroes the vertex */
	public void blank(){ this.state = 0; }
	
}//End of class

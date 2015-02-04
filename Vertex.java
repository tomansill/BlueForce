/**
 * @author Thomas B. Ansill
 * @date April 08, 2014	
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
	/** Value for vertex's filled and unfilled status */
	private boolean filled = false;
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
	 * @param filled Vertex's status of filled or unfilled
	 * @param neighbors List of vertex's neighbors
	 */
	public Vertex(String label, boolean filled, ArrayList<String> neighbors){
		this.label = label;
		this.filled = filled;
		this.neighbors = neighbors;
	}//End of constructor
	
	/** Parametrized Constructor for Vertex Object
	 * @param label Vertex Label
	 * @param neighbors List of vertex's neighbors
	 * @param filled Vertex's status of filled or unfilled
	 */
	public Vertex(String label, ArrayList<String> neighbors, boolean filled){
		this.label = label;
		this.filled = filled;
		this.neighbors = neighbors;
	}//End of constructor
	
	/** Accessor for vertex's label
     * @return label
	 */
	public String getLabel(){return label;}
	/** Accessor for vertex's list of neighbors
     * @return list of neighbors
	 */
	public ArrayList<String> getNeighbors(){return neighbors;}
	/** Accessor for vertex's filled status
     * @return filled or unfilled
	 */
	public boolean isFilled(){return filled;}
	/** Hashing Method - Not sure if necessary anymore */
	public int hashCode(){return label.hashCode();}
	/** Fills the vertex */
	public void fill(){ this.filled = true; }
	/** Unfills the vertex */	
	public void unfill(){ this.filled = false; }
	
}//End of class
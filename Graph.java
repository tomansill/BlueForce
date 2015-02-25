/**
 * @author Thomas B. Ansill
 * @date April 08, 2014	
 * University: Rochester Institute of Technology
 * 			
 *			This program is to be used only for research purposes
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

public class Graph{
	/** Value for cardinality of graph */
	private int size = 0;
	/** Treemap of states of vertices */
	private TreeMap<Short, Integer> stateCount;
	/** Adjacency List in form of TreeMap */
	private TreeMap<String, Vertex> adjList;

	/** Parametrized Constructor for Graph object
	 *  @param Graph object to be deep-copied
	 */
	public Graph(Graph graph){
		//Attempt to deepcopy
		adjList = new TreeMap<String, Vertex>();
		for(String label : graph.getVertices()){
			Vertex vertex = getVertex(label);
			short state = vertex.getState();
			if(stateCount.containsKey(vertex.getState())){
				int count = stateCount.get(vertex.getState());
				count++;
				stateCount.put(vertex.getState(), count);
			}
			ArrayList<String> neighbors = graph.getNeighbors(label);
			//deepcopy array
			int[] coordinate = new int[3]; 
			int[] old = vertex.getCoordinate();
			coordinate[0] = old[0];
			coordinate[1] = old[1];
			coordinate[2] = old[2];
			adjList.put(label, new Vertex(label, state, neighbors, coordinate));
		}//End of for loop
		size = adjList.size();
	}//End of constructor

	/** Parametrized Constructor for Graph object
	 *  @param Graph object to be deep-copied
	 */
	public Graph(TreeMap<String, Vertex> list){
		//Attempt to deepcopy
		adjList = list;
		size = adjList.size();
	}//End of constructor
	
	/** Parametrized Constructor for Graph object
	 *  @param filename of text file to be parsed and converted to Graph object
	 */
	 /*
	public Graph(File filename){
		FileReader fr = null;
		adjList = new TreeMap<String, Vertex>();
		try{
			fr = new FileReader(filename);
			char current;
			boolean parenthense = false;
			while((current = fr.read()) != -1){
				if(current == '(') parenthense = true;
			}//End of loop
		}catch(IOException ioe){
			System.err.println("error reading the file!");
		}finally{
			try{fr.close()
			}catch(IOException ioe) System.err.println("Error closing the file!");
		}
		size = adjList.size();
	}//End of constructor
	*/
	/** Accessor for cardinality of the graph
	 *  @return cardinality
	 */
	public int getSize(){ return size; }
	
	/** Accessor for list of vertices in the graph
	 *  @return list of vertices in the graph
	 */

    /** Accessor for fill status of a vertex
     *  @return fill status
     */
    public short getState(String vertex, short n){
        if(adjList.containsKey(vertex)) return adjList.get(vertex).getState();
        return -1;
    }//End isFilled method

	public ArrayList<String> getVertices(){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			list.add((String)entry.getKey());
		}//End of for loop
		return list;
	}//End of getVertices method
	
	/** Accessor for list of N vertices in the graph
	 *  @return list of N vertices in the graph
	 */
	public ArrayList<String> getNVertices(short n){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			String label = (String)entry.getKey();
			if(getVertex(label).getState() == n) list.add(label);
		}//End of for loop
		return list;
	}//End of getNVertices method
	
	/** Accessor for list of neighbors in the graph
	 *  @param vertex name
	 *  @return list of neighbors
	 */
	public ArrayList<String> getNeighbors(String vertex){
		if(!adjList.containsKey(vertex)) return null;
		return adjList.get(vertex).getNeighbors();
	}//End of getNeighbor method
	
	/** Accessor for list of vertex's n neighbors in the graph
	 *  @param vertex name
	 *  @return list of n vertices neighbors
	 */
	public ArrayList<String> getNNeighbors(String vertex, Short n){
		if(!adjList.containsKey(vertex)) return null;
		ArrayList<String> list = new ArrayList<String>();
		for(String neighbor : adjList.get(vertex).getNeighbors()){
			if(adjList.get(neighbor).getState()==n) list.add(neighbor);
		}//End of for loop
		return list;
	}//End of getNNeighbors method

	
	/** Accessor for number of vertex's unfilled neighbors in the graph
	 *  @param vertex name
	 *  @return number of neighbors
	 */
	public int getNumOfNNeighbors(String vertex, Short n){
		if(!adjList.containsKey(vertex)) return -1;
		int count = 0;
		for(String neighbor : adjList.get(vertex).getNeighbors()){
			if(adjList.get(neighbor).getState()!=n) count++;
		}//End of for loop
		return count;
	}//End of getNumOfUnfilledNeighbors method

	public int getNumOfNVertices(short n){
		if(stateCount.containsKey(n)) return stateCount.get(n);
		return 0; 
	}//End of getNumOfNVertices method


	/** Change vertex's state number in a graph
	 *  @param label name
     *  @param n desired value for vertex's state
	 */
	public void changeVertex(String label, short n){
		if(adjList.containsKey(label)){
			Vertex vertex = getVertex(label);
			if(vertex.getState() != n){
				int count = stateCount.get(vertex.getState());
				count--;
				stateCount.put(vertex.getState(), count);
				adjList.get(vertex).force(n);
				count = stateCount.get(vertex.getState());
				count++;
				stateCount.put(vertex.getState(), count);
			}
		}
	}//End of fillVertex method

	/** Formats the graph in string form
	 * @return String form of Graph 
	 */
	public String toString(short n){
		String toString = "";
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			Vertex vertex = (Vertex)entry.getValue();
			toString += vertex.getLabel();
			toString += vertex.getState();
			toString += ": ";
			for(String neighbor : vertex.getNeighbors()){
				toString += " " + neighbor;
			}//End of for loop
			for(int i = 0; i < vertex.getNeighbors().size(); i++){
				toString += vertex.getNeighbors().get(i);
				if(i != vertex.getNeighbors().size()-1){
					toString += ", ";
				}
			}//End of for loop
			toString += "\n";
		}
		return toString;
	}//End of toString method

	public Vertex getVertex(String label){
		if(adjList.containsKey(label)) return adjList.get(label);
		else return null;	
	}//End of getVertex method

	public int hashCode(Short n){
		int hash = this.size * 3;
		for(String label : this.getVertices()){
			if(getVertex(label).getState() == n){
				hash += label.hashCode() * 7;
			}else{
				hash -= label.hashCode() * 3;
			}
		}//End of for loop
		hash = hash * 17;
		return hash;
	}//End of hashCode method

	public boolean equals(Object graph, Short n){
		if(graph instanceof Graph){
			Graph ngraph = (Graph)graph;
			if(this.size != ngraph.getSize()) return false;
			if(this.getNumOfNVertices(n) != ngraph.getNumOfNVertices(n)) return false;
			for(String vertex : this.getVertices()){
				if(!ngraph.contains(vertex)) return false;
				if(this.getState(vertex, n) != ngraph.getState(vertex, n)) return false;
				if(this.getNeighbors(vertex).size() != ngraph.getNeighbors(vertex).size()) return false;
			}//End of for loop
			return true;
		}
		return false;
	}//End of equals method
	
	public boolean contains(String vertex){
		if(adjList.containsKey(vertex)) return true;
		return false;
	}//End of contains method
	
}//End of class

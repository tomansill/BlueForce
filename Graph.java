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
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

public class Graph{
	/** Value for cardinality of graph */
	private int size = 0;
	/** Value for number of unfilled vertices in the graph */
	private int unfilledVertices = 0;
	/** Adjacency List in form of TreeMap */
	private TreeMap<String, Vertex> adjList;
	/** Parametrized Constructor for Graph object
	 *  @param Graph object to be deep-copied
	 */
	public Graph(Graph graph){
		//Attempt to deepcopy
		adjList = new TreeMap<String, Vertex>();
		for(String vertex : graph.getVertices()){
			String label = vertex;
			boolean filled = graph.isFilled(vertex);
			if(!filled) unfilledVertices++;
			ArrayList<String> neighbors = graph.getNeighbors(vertex);
			adjList.put(label, new Vertex(label, filled, neighbors));
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
	public Graph(File filename){
		BufferedReader br = null;
		adjList = new TreeMap<String, Vertex>();
		try{
			String cLine;
			br = new BufferedReader(new FileReader(filename));
			while((cLine = br.readLine()) != null){
				String[] pieces = cLine.split("\\s+");
				String label;
				boolean filled;
				if(pieces[0].charAt(pieces[0].length()-1) == '#'){
					label = pieces[0].substring(0, pieces[0].length()-1);
					filled = true;
				}else{
					label = pieces[0];
					filled = false;
					unfilledVertices++;
				}
				ArrayList<String> neighbors = new ArrayList<String>();
				for(int i = 1; i < pieces.length; i++){
					neighbors.add(pieces[i]);
				}//End of for loop
				adjList.put(label, new Vertex(label, filled, neighbors));
			}//End of while loop
		}catch(IOException ioe){
			System.err.println("error reading the file!");
		}
		size = adjList.size();
	}//End of constructor
	
	/** Accessor for cardinality of the graph
	 *  @return cardinality
	 */
	public int getSize(){ return size; }
	
	/** Accessor for number of unfilled vertices in the graph
	 *  @return number of unfilled vertices
	 */
	public int getNumOfUnfilledVertices(){ return unfilledVertices; }
	
	/** Accessor for number of filled vertices in the graph
	 *  @return number of filled vertices
	 */
	public int getNumOfFilledVertices(){ return this.size - unfilledVertices; }
	
	/** Accessor for fill status of a vertex
	 *  @return fill status
	 */
	public boolean isFilled(String vertex){
		if(adjList.containsKey(vertex)) return adjList.get(vertex).isFilled();
		return false;
	}//End isFilled method
	
	/** Accessor for list of vertices in the graph
	 *  @return list of vertices in the graph
	 */
	public ArrayList<String> getVertices(){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			list.add((String)entry.getKey());
		}//End of for loop
		return list;
	}//End of getVertices method
	
	/** Accessor for list of filled vertices in the graph
	 *  @return list of vertices in the graph
	 */
	public ArrayList<String> getFilledVertices(){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			String label = (String)entry.getKey();
			if(isFilled(label)) list.add(label);
		}//End of for loop
		return list;
	}//End of getFilledVertices method
	
	/** Accessor for list of unfilled vertices in the graph
	 *  @return list of vertices in the graph
	 */
	public ArrayList<String> getUnfilledVertices(){
		ArrayList<String> list = new ArrayList<String>();
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			String label = (String)entry.getKey();
			if(!isFilled(label)) list.add(label);
		}//End of for loop
		return list;
	}//End of getUnfilledVertices method
	
	/** Accessor for list of neighbors in the graph
	 *  @param vertex name
	 *  @return list of neighbors
	 */
	public ArrayList<String> getNeighbors(String vertex){
		if(!adjList.containsKey(vertex)) return null;
		return adjList.get(vertex).getNeighbors();
	}//End of getNeighbor method
	
	/** Accessor for list of vertex's unfilled neighbors in the graph
	 *  @param vertex name
	 *  @return list of unfilled neighbors
	 */
	public ArrayList<String> getUnfilledNeighbors(String vertex){
		if(!adjList.containsKey(vertex)) return null;
		ArrayList<String> list = new ArrayList<String>();
		for(String neighbor : adjList.get(vertex).getNeighbors()){
			if(!adjList.get(neighbor).isFilled()) list.add(neighbor);
		}//End of for loop
		return list;
	}//End of getUnfilledNeighbors method
	
	/** Accessor for number of vertex's filled neighbors in the graph
	 *  @param vertex name
	 *  @return list of filled neighbors
	 */
	public ArrayList<String> getFilledNeighbors(String vertex){
		if(!adjList.containsKey(vertex)) return null;
		ArrayList<String> list = new ArrayList<String>();
		for(String neighbor : adjList.get(vertex).getNeighbors()){
			if(adjList.get(neighbor).isFilled()) list.add(neighbor);
		}//End of for loop
		return list;
	}//End of getFilledNeighbors method
	
	/** Accessor for number of vertex's unfilled neighbors in the graph
	 *  @param vertex name
	 *  @return number of neighbors
	 */
	public int getNumOfUnfilledNeighbors(String vertex){
		if(!adjList.containsKey(vertex)) return -1;
		int count = 0;
		for(String neighbor : adjList.get(vertex).getNeighbors()){
			if(!adjList.get(neighbor).isFilled()) count++;
		}//End of for loop
		return count;
	}//End of getNumOfUnfilledNeighbors method
	
	/** Accessor for number of vertex's filled neighbors in the graph
	 *  @param vertex name
	 *  @return number of neighbors
	 */
	public int getNumOfFilledNeighbors(String vertex){
		if(!adjList.containsKey(vertex)) return -1;
		int count = 0;
		for(String neighbor : adjList.get(vertex).getNeighbors()){
			if(adjList.get(neighbor).isFilled()) count++;
		}//End of for loop
		return count;
	}//End of getNumOfFilledNeighbors method
	
	/** Fill a vertex in the graph
	 *  @param vertex name
	 */
	public void fillVertex(String vertex){
		if(adjList.containsKey(vertex)){
			if(!adjList.get(vertex).isFilled()){
				adjList.get(vertex).fill();
				unfilledVertices--;
			}
		}
	}//End of fillVertex method
	
	/** Unfill a vertex in the graph
	 *  @param vertex name
	 */
	public void unfillVertex(String vertex){
		if(adjList.containsKey(vertex)){
			if(adjList.get(vertex).isFilled()){
				adjList.get(vertex).unfill();
				unfilledVertices++;
			}
		}
	}//End of unfillVertex method
	
	/** Formats the graph in string form
	 * @return String form of Graph 
	 */
	public String toString(){
		String toString = "";
		for(Map.Entry<String, Vertex> entry: adjList.entrySet()){
			Vertex vertex = (Vertex)entry.getValue();
			toString += vertex.getLabel();
			if(vertex.isFilled()){
				toString += "#";
			}else{
				toString += " ";
			}
			toString += ": ";
			/*for(String neighbor : vertex.getNeighbors()){
				toString += " " + neighbor;
			}//End of for loop*/
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
	
	@Override
	public int hashCode(){
		int hash = this.size * 3;
		for(String vertex : this.getVertices()){
			if(isFilled(vertex)){
				hash += vertex.hashCode() * 7;
			}else{
				hash -= vertex.hashCode() * 3;
			}
		}//End of for loop
		hash = hash * 17;
		return hash;
	}//End of hashCode method
	
	@Override
	public boolean equals(Object graph){
		if(graph instanceof Graph){
			Graph ngraph = (Graph)graph;
			if(this.size != ngraph.getSize()) return false;
			if(this.getNumOfFilledVertices() != ngraph.getNumOfFilledVertices()) return false;
			for(String vertex : this.getVertices()){
				if(!ngraph.contains(vertex)) return false;
				if(this.isFilled(vertex) != ngraph.isFilled(vertex)) return false;
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
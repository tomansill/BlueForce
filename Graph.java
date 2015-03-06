/**
 * @author Thomas B. Ansill
 * @date March 4, 2015	
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
import java.util.Collection;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map;

enum Operator{
	LESS_THAN, LESS_THAN_OR_EQUAL, EQUAL, MORE_THAN_OR_EQUAL, MORE_THAN, NOT_EQUAL
}

public class Graph{
	/** Record of number of states in the graph */
	private TreeMap<Short, Integer> stateCount;
	/** Adjacency List in form of TreeMap */
	private TreeMap<Vertex, HashSet<Vertex>> adjList;
	/** List of vertex labels */
	private TreeMap<String, Vertex> vertexList;

	/** Creates a empty graph */
	public Graph(){
		this.stateCount = new TreeMap<Short, Integer>();
		this.adjList = new TreeMap<Vertex, HashSet<Vertex>>();
		this.vertexList = new TreeMap<String, Vertex>();
	}//End of constructor

	/** Accessor for cardinality of the graph
	 *  @return cardinality
	 */
	public int getSize(){ return vertexList.size(); }

	/** Adds a vertex to the graph
	 *	@param vertex Vertex to be added into the graph
	 */
	public void addVertex(Vertex vertex){
		vertexList.put(vertex.getLabel(), vertex);	
		adjList.put(vertex, new HashSet<Vertex>());
		if(stateCount.containsKey(vertex.getState())){
			int count = stateCount.get(vertex.getState());
			count++;
			stateCount.put(vertex.getState(), count);	
		}else{
			stateCount.put(vertex.getState(), 1);
		}
	}//End of addVertex method

	/** Connects the vertices
	 *	@param vertex1 Vertex one
	 *	@param vertex2 Vertex two
	 *	@throws Exception Exception will be thrown if any of vertices doesnt exist in the graph
	 */
	public void connectVertices(Vertex vertex1, Vertex vertex2) throws Exception{
		if(!vertexList.containsKey(vertex1.getLabel())){
			throw new Exception("Cannot connect vertices! Vertex in first argument does not exist in the graph!"); 	
		}else if(vertexList.containsKey(vertex2.getLabel())){
			throw new Exception("Cannot connect vertices! Vertex in second argument does not exist in the graph!"); 	
		}

		//Retrieve both vertices' arraylist
		HashSet<Vertex> neighbors1 = this.adjList.get(vertex1);
		HashSet<Vertex> neighbors2 = this.adjList.get(vertex2);

		//Assign both vertices in both's neighbors list
		if(!neighbors1.contains(vertex2)) neighbors1.add(vertex2);
		if(!neighbors2.contains(vertex1)) neighbors2.add(vertex1);
	}//End of connectVertices method

	/** Removes the vertex from the graph and severs any edges connected to it
	 *	@param vertexLabel Label of vertex to be removed from the graph 
	 *	@throws Exception Exception will be thrown if vertex doesnt exist in the graph
	 */
	public void removeVertex(String vertexLabel) throws Exception{
		if(!vertexList.containsKey(vertexLabel)) throw new Exception("Vertex doesn't exist in the graph!");
		this.removeVertex(vertexList.get(vertexLabel));
	}//End of removeVertex method

	/** Removes the vertex from the graph and severs any edges connected to it
	 *	@param vertex Vertex to be removed from the graph
	 *	@throws Exception Exception will be thrown if vertex doesnt exist in the graph
	 */
	public void removeVertex(Vertex vertex) throws Exception{
		if(!adjList.containsKey(vertex)) throw new Exception("Vertex doesn't exist in the graph!");
		//Clean adjacency list before removing vertex
		HashSet<Vertex> neighbors = this.adjList.get(vertex);

		//Find vertex on other vertices' neighbors list and remove it
		for(Vertex neighbor : neighbors){
			this.adjList.get(neighbor).remove(vertex);
		}//End of loop

		//the adjacency list is clean. Now, vertex can be removed
		this.adjList.remove(vertex);
		this.vertexList.remove(vertex.getLabel());
		if(stateCount.get(vertex.getState()) == 1) stateCount.remove(vertex.getState()); //Remove instead of decrementing
		else{
			//Decrement it	
			int count = stateCount.get(vertex.getState());
			count--;
			stateCount.put(vertex.getState(), count);
		}
	}//End of removeVertex method

	/** Forces a vertex in the graph
	 *	@param vertex Vertex to be forced
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public void forceVertex(Vertex vertex) throws Exception{
		if(!this.adjList.containsKey(vertex)) throw new Exception("Vertex doesn't exist in the graph!");
		short state = vertex.getState();
		state++;
		setVertexState(vertex, state);
	}//End of forceVertex method

	/** Reverse Force a vertex in the graph
	 *	@param vertex Vertex to be reverse forced
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public void unforceVertex(Vertex vertex) throws Exception{
		if(!this.adjList.containsKey(vertex)) throw new Exception("Vertex doesn't exist in the graph!");
		short state = vertex.getState();
		state--;
		setVertexState(vertex, state);
	}//End of forceVertex method

	/** Forces a vertex in the graph
	 *	@param vertexLabel Label of vertex to be reverse forced
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public void forceVertex(String vertexLabel) throws Exception{
		if(!vertexList.containsKey(vertexLabel)) throw new Exception("Vertex doesn't exist in the graph!");
		Vertex vertex = vertexList.get(vertexLabel);
		short state = vertex.getState();
		state++;
		setVertexState(vertex, state);
	}//End of forceVertex method

	/** Reverse Force a vertex in the graph
	 *	@param vertexLabel Label of vertex to be forced
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public void unforceVertex(String vertexLabel) throws Exception{
		if(!vertexList.containsKey(vertexLabel)) throw new Exception("Vertex doesn't exist in the graph!");
		Vertex vertex = vertexList.get(vertexLabel);
		short state = vertex.getState();
		state--;
		setVertexState(vertex, state);
	}//End of forceVertex method

	/** Sets a vertex's state in the graph to a specified number of state
	 *	@param vertexLabel Label of vertex to be forced
	 *	@param state Specified state
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public void setVertexState(String vertexLabel, short state) throws Exception{
		if(!vertexList.containsKey(vertexLabel)) throw new Exception("Vertex doesn't exist in the graph!");
		this.setVertexState(vertexList.get(vertexLabel), state);
	}//End of setVertexState
		
	/** Sets a vertex's state in the graph to a specified number of state
	 *	@param vertex Vertex to be forced
	 *	@param state Specified state
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public void setVertexState(Vertex vertex, short state) throws Exception{
		if(!this.adjList.containsKey(vertex)) throw new Exception("Vertex doesnt exist in the graph!");
		
		//Update stateCount record first
		if(stateCount.get(vertex.getState()) == 1) stateCount.remove(vertex.getState()); //Remove instead of decrementing
		else{
			//Decrement it
			int count = stateCount.get(vertex.getState());
			count--;
			stateCount.put(vertex.getState(), count);
		}
		if(stateCount.containsKey(state)){
			//Increment it
			int count = stateCount.get(state);
			count++;
			stateCount.put(state, count);
		}else{
			//Create entry and start at 1
			stateCount.put(state, 1);
		}

		//Now, force the vertex
		vertex.setState(state);
	}//End of setVertexState method

	/** Accessor for list of vertices in the graph
	 *	@return list of vertices
	 */
	public Collection<Vertex> getListOfVertices(){
		return vertexList.values();
	}//End of getListOfVertices method

	/** Accessor for list of a vertex's neighbors in the graph
	 *	@param vertex Vertex to be used to get neighbors
	 *	@return list of vertex's neighbors
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public Collection<Vertex> getListOfNeighbors(Vertex vertex) throws Exception{
		if(!adjList.containsKey(vertex)) throw new Exception("Vertex doesnt exist in the graph!");
		return adjList.get(vertex);
	}//End of getListOfVertices method

	/** Accessor for list of a vertex's neighbors in the graph
	 *	@param vertexLabel Label of vertex to be used to get neighbors
	 *	@return list of vertex's neighbors
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public Collection<Vertex> getListOfNeighbors(String vertexLabel) throws Exception{
		if(!vertexList.containsKey(vertexLabel)) throw new Exception("Vertex doesnt exist in the graph!");
		return adjList.get(vertexList.get(vertexLabel));
	}//End of getListOfVertices method

	/** Accessor for list of a vertex's neighbors in the graph where neighbors' state is less than a specified number of state
	 *	@param vertex Vertex to be used to get neighbors
	 *	@param state State to be compared to
	 *	@param operator Operator Enum to be used to compare with state
	 *	@return list of vertex's neighbors
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public Collection<Vertex> getListOfNeighborsStateCriteria(Vertex vertex, Operator operator, short state) throws Exception{
		if(!adjList.containsKey(vertex)) throw new Exception("Vertex doesnt exist in the graph!");
		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
		for(Vertex neighbor : this.adjList.get(vertex)){
			switch(operator){
				case LESS_THAN:				if(neighbor.getState() < state) neighbors.add(neighbor);
											break;	
				case LESS_THAN_OR_EQUAL:	if(neighbor.getState() <= state) neighbors.add(neighbor);
											break;
				case EQUAL:					if(neighbor.getState() == state) neighbors.add(neighbor);
											break;
				case MORE_THAN_OR_EQUAL:	if(neighbor.getState() >= state) neighbors.add(neighbor);
											break;
				case MORE_THAN:				if(neighbor.getState() > state) neighbors.add(neighbor);
											break;
				case NOT_EQUAL:				if(neighbor.getState() != state) neighbors.add(neighbor);
				default:					break;
			}
		}//End of loop
		return neighbors;
	}//End of getListOfNeighborsStateCriteria method

	/** Accessor for list of a vertex's neighbors in the graph where neighbors' state is less than a specified number of state
	 *	@param vertexLabel Label of vertex to be used to get neighbors
	 *	@param state State to be compared to
	 *	@param operator Operator Enum to be used to compare with state
	 *	@return list of vertex's neighbors
	 *	@throws Exception Exception will be thrown if vertex does not exist in the graph
	 */
	public Collection<Vertex> getListOfNeighborsStateCriteria(String vertexLabel, Operator operator, short state) throws Exception{
		if(!vertexList.containsKey(vertexList)) throw new Exception("Vertex doesnt exist in the graph!");
		return getListOfNeighborsStateCriteria(this.vertexList.get(vertexLabel), operator, state);
	}//End of getListOfNeighborsStateCriteria method
}//End of class
